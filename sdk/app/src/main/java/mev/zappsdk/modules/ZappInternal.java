package mev.zappsdk.modules;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import mev.loggersdk.modules.Helper.LDataSourceHelper;
import mev.loggersdk.modules.Helper.LEncodeHelper;
import mev.loggersdk.modules.Helper.LFileHelper;
import mev.loggersdk.modules.Helper.LInfoHelper;
import mev.loggersdk.modules.Logger;
import mev.zappsdk.R;
import mev.zappsdk.modules.Helpers.BloomFilter.BloomFilter;
import mev.zappsdk.modules.Helpers.BloomFilter.Models.ZappBloom;
import mev.zappsdk.modules.Helpers.ZappResultHandler;
import mev.zappsdk.modules.Helpers.ZappResultHandler.*;
import mev.zappsdk.modules.Helpers.ZappSerializeHelper;
import mev.zappsdk.modules.Resources.URLType;
import mev.zappsdk.modules.Services.ZappCheckZappIdService.ZappCheckZappIdService;
import mev.zappsdk.modules.Views.ZappActivities.ZappIdActivity;

import mev.zappsdk.modules.Views.ZappActivities.ZappIdActivity;
/**
 * Created by andrew on 24.03.16.
 */
public class ZappInternal {

    //region Constants

    static final String ZAPP_FILE_NAME = "zapp.bin";
    static final String ZAPP_BLOOM_FILE_NAME = "bloom.bin";
    static final String ZAPP_ID_KEY = "id";
    static final String ZAPP_ID_SET_BY_USER_KEY = "set_by_user";
    static final String ZAPP_BLOOM_DATA = "bloom_data";

    static final String ZID_KEY = "zid";
    static final String TIME_KEY = "time";
    static final String ZID_SESSION = "session";
    static final String ZID_DURATION = "duration";
    static final String ZID_TIME = "time";

    static final String ERROR_SAVE_TO_FILE_FAILED_TEXT= "saveToFile: failed to save %s";
    static final String ERROR_LOAD_FROM_FILE_FAILED_TEXT= "loadFromFile: failed to load %s";
    static final String ERROR_LOAD_FROM_BLOOM_FILE_FAILED_TEXT= "loadBloomFilterDataFromFile: failed to load %s";
    static final String ERROR_NO_SUCH_KEY_TEXT = "No such key %s in %s";
    static final String ERROR_FAILED_TO_LOAD_DATA_TEXT = "loadFromFile: Failed to load data from file %s";
    static final String ERROR_FAILED_TO_LOAD_BLOOM_DATA_TEXT = "loadBloomFilterDataFromFile: Failed load bloom filter data: %s";
    static final String ERROR_ZAPPBLOOM_OBJECT_IS_EMPTY = "zappBloom object is empty";

    static final String SAVE_TO_FILE_SUCCESSFUL_TEXT = "saveToFile: successfully saved zid %s to %s";
    static final String SAVE_BLOOM_TO_FILE_SUCCESSFUL_TEXT = "saveBloomFilterToFile: successfully saved zid %s to %s";
    static final String LOAD_FROM_FILE_SUCCESSFUL_TEXT = "loadFromFile: successfully loaded zid %s from %s";
    static final String LOAD_BLOOM_FROM_FILE_SUCCESSFUL_TEXT = "loadBloomFilterDataFromFile: successfully loaded zid %s from %s";

    static final String UNKNOWN_APP_TEXT = "unknownApp";

    static final String Z_ID_FORMAT = "Z-%d";

    static final String REGULAR_EXPRESSION = "[^a-zA-Z0-9.-]";
    static final String REPLACEMENT = "_";

    //endregion

    //region Properties

    boolean isUserDefinedZapp;
    boolean allowNotVerifiedZid;

    ArrayList<String> seedFunctions;

    String sessionId;
    String zappId;
    String taskName;
    String contextName;

    // TODO: check this property NSTimeInterval
    long sessionStartTime;
    long taskStartTime;

    public BloomFilter bloomFilter;

    static ZappIdActivity zappIdActivity;

    //endregion

    private static ZappInternal instance;

    public static ZappInternal getInstance() {
        return instance != null ? instance : (instance = new ZappInternal());
    }

    //region Constructors

    private ZappInternal() {
        sessionStartTime = new Date().getTime();
        sessionId = getRandomId();
        taskStartTime = 0;

        // TODO: move this, activity should not be global
        zappIdActivity = new ZappIdActivity();
        String appIdentifier = LInfoHelper.getInstance().getPackageName();
        String appVersion = LInfoHelper.getInstance().getVersion();
        String appId = (appIdentifier == null || appVersion == null) ? UNKNOWN_APP_TEXT : appIdentifier;

        String cleanedAppId = appId.replaceAll(REGULAR_EXPRESSION, REPLACEMENT);
        Logger.getInstance().initLogger(cleanedAppId, URLType.PROD_ENVIRONMENT.getValue());

       if (!loadFromFile(ZAPP_FILE_NAME)) {
            zappId = getRandomId();
            isUserDefinedZapp = false;
            saveToFile(ZAPP_FILE_NAME);
        }

        allowNotVerifiedZid = false;

        if (LInfoHelper.getInstance().getConnectionState() == NetworkInfo.DetailedState.CONNECTED) {
            loadDataForBloomFilter();
        }
    }

    //endregion

    //region ZappId methods

    public void setZappId(String zappId) {
        if (zappId == null || zappId.isEmpty()) {

            if (!isUserDefinedZapp) {
                return;
            }

            this.zappId = getRandomId();
            isUserDefinedZapp = false;

        } else if (zappId.equals(this.zappId)) {
            return;
        } else {
            this.zappId = zappId;
            isUserDefinedZapp = true;
        }

        saveToFile(LDataSourceHelper.getFile(ZAPP_FILE_NAME));
    }

    public void requestZappId() {
        zappIdActivity.requestZappId(isUserDefinedZapp ? zappId : null);
        if (LInfoHelper.getInstance().getConnectionState() == NetworkInfo.DetailedState.CONNECTED) {
            loadDataForBloomFilter();
        }
        updateOfflineCheckerRules();
    }

    public String userProviderZappId() {

        if (isUserDefinedZapp) {
            return zappId;
        }

        return null;
    }

    //endregion

    //region General methods

    public HashMap<String, String> sessionInfo() {
        HashMap<String, String> result = new HashMap();

        result.put(ZID_KEY, zappId);
        result.put(ZID_SESSION, sessionId);
        result.put(TIME_KEY, String.valueOf(new Date().getTime() - sessionStartTime));

        return result;
    }

    public double taskDuration(String task, String context) {
        if (taskStartTime <= 0) {
            return -1;
        }
        if ((taskName != null || task != null) && !taskName.equals(task)) {
            return -1;
        }
        if ((contextName != null || context != null) && !contextName.equals(context)) {
            return -1;
        }

        // TODO: timeIntervalSinceReferenceDate
        return new Date().getTime() - taskStartTime;
    }

    public void setTask(String task, String context) {
        taskStartTime = new Date().getTime();

        if (task == null || task.isEmpty()) {
            taskName = null;
        } else {
            taskName = new String(task);
        }
        if (context == null || context.isEmpty()) {
            contextName = null;
        } else {
            contextName = new String(context);
        }

    }

    public HashMap<String, String> sessionInfoForTask(String task, String context) {
        HashMap<String, String> result = new HashMap();
        result.put(ZID_KEY, zappId);
        result.put(ZID_SESSION, sessionId);
        result.put(ZID_DURATION, String.format("%.3f", taskDuration(task, context)));
        result.put(ZID_TIME, String.valueOf((new Date().getTime() - sessionStartTime) / 1000.0));
        return result;
    }

    public void resetTaskStartTime() {
        taskStartTime = 0;
    }

    public boolean saveBloomFilterToFile(String bitSet, ArrayList<String> hashesArray)
    {
        ZappBloom zappBloom = new ZappBloom(bitSet, hashesArray);

        File file = LDataSourceHelper.getFile(ZAPP_BLOOM_FILE_NAME);

        HashMap<String, byte[]> bloomFilterData = new HashMap();

        bloomFilterData.put(ZAPP_BLOOM_DATA, LEncodeHelper.encode(ZappSerializeHelper.serialize(zappBloom)));

        LFileHelper.getInstance().writeToFile(file, bloomFilterData);

        if (!file.exists() || file.length() == 0) {
            Log.d(ZappInternal.class.getSimpleName(), String.format(ERROR_SAVE_TO_FILE_FAILED_TEXT, file.getName()));
            return false;
        }

        Log.d(ZappInternal.class.getSimpleName(), String.format(SAVE_BLOOM_TO_FILE_SUCCESSFUL_TEXT, zappId, file.getName()));

        allowNotVerifiedZid = false;

        configureBloomFilter(zappBloom);

        return true;
    }

    public void configureBloomFilter(ZappBloom zappBloom)
    {
        seedFunctions = zappBloom.getSeedsArray();
        bloomFilter = new BloomFilter(zappBloom.getBitSetString().length(), zappBloom.getBitSet(), seedFunctions);
    }

    //endregion

    //region Internal Methods

    private void updateOfflineCheckerRules() {
        if (loadBloomFilterDataFromFile() && LInfoHelper.getInstance().getConnectionState() != NetworkInfo.DetailedState.CONNECTED) {
            AlertDialog alertDialog = new AlertDialog.Builder(ZApplication.getAppContext().getApplicationContext()).setTitle("Zid check error").setMessage("Application is offline and we cannot verify zid. Are you sure you want to continue?").setNeutralButton("Cancel", null).show();
            alertDialog.show();
        }
    }

    private boolean loadBloomFilterDataFromFile() {

        File file = LDataSourceHelper.getFile(ZAPP_BLOOM_FILE_NAME);

        if (file == null || !file.exists() || file.length() == 0) {
            Log.d(ZappInternal.class.getSimpleName(), String.format(ERROR_LOAD_FROM_BLOOM_FILE_FAILED_TEXT, file.getName()));
            return false;
        }

        HashMap<String, byte[]> bloomFilterData = (HashMap) LFileHelper.getInstance().readHashMapFromFile(file);


        if (bloomFilterData == null) {
            Log.d(ZappInternal.class.getSimpleName(), String.format(ERROR_FAILED_TO_LOAD_BLOOM_DATA_TEXT, file.getName()));
            return false;
        }

        if (!bloomFilterData.containsKey(ZAPP_BLOOM_DATA)) {
            Log.d(ZappInternal.class.getSimpleName(), String.format(ERROR_NO_SUCH_KEY_TEXT, ZAPP_BLOOM_DATA));
            return false;
        }

        ZappBloom zappBloom = (ZappBloom) ZappSerializeHelper.deserialize(LEncodeHelper.decode(bloomFilterData.get(ZAPP_BLOOM_DATA)));

        if (zappBloom.getBitSet() == null || zappBloom.getSeedsArray() == null) {
            Log.d(ZappInternal.class.getSimpleName(), String.format(ERROR_ZAPPBLOOM_OBJECT_IS_EMPTY, ZAPP_BLOOM_DATA));
            return false;
        }

        configureBloomFilter(zappBloom);

        Log.d(ZappInternal.class.getSimpleName(), String.format(LOAD_BLOOM_FROM_FILE_SUCCESSFUL_TEXT, zappId, ZAPP_BLOOM_FILE_NAME));

        return true;
    }

    private String getRandomId() {
        SecureRandom secureRandom = new SecureRandom();
        return String.format(Z_ID_FORMAT, Math.abs(secureRandom.nextLong()));
    }

    private boolean loadFromFile(String path) {

        File file = LDataSourceHelper.getFile(path);

        if (file == null || !file.exists() || file.length() == 0) {
            Log.d(ZappInternal.class.getSimpleName(), String.format(ERROR_LOAD_FROM_FILE_FAILED_TEXT, file.getName()));
            return false;
        }

        HashMap<String, String> zappData = (HashMap) LFileHelper.getInstance().readHashMapFromFile(file);

        if (zappData == null) {
            Log.d(ZappInternal.class.getSimpleName(), String.format(ERROR_FAILED_TO_LOAD_DATA_TEXT, file.getName()));
            return false;
        }

        if (!zappData.containsKey(ZAPP_ID_KEY)) {
            Log.d(ZappInternal.class.getSimpleName(), String.format(ERROR_NO_SUCH_KEY_TEXT, ZAPP_ID_KEY, path));
            return false;
        }
        zappId = LEncodeHelper.getDecodedString(zappData.get(ZAPP_ID_KEY));
        if (!zappData.containsKey(ZAPP_ID_SET_BY_USER_KEY)) {
            Log.d(ZappInternal.class.getSimpleName(), String.format(ERROR_NO_SUCH_KEY_TEXT, ZAPP_ID_SET_BY_USER_KEY, path));
            return false;
        }
        isUserDefinedZapp = Boolean.valueOf(LEncodeHelper.getDecodedString(zappData.get(ZAPP_ID_SET_BY_USER_KEY)));

        Log.d(ZappInternal.class.getSimpleName(), String.format(LOAD_FROM_FILE_SUCCESSFUL_TEXT, zappId, path));

        return true;
    }

    private boolean saveToFile(String path) {
        File file = LDataSourceHelper.getFile(path);
        return saveToFile(file);
    }

    private boolean saveToFile(File file) {
        HashMap<String, String> zappData = new HashMap();

        zappData.put(ZAPP_ID_KEY, LEncodeHelper.getEncodedString(zappId));
        zappData.put(ZAPP_ID_SET_BY_USER_KEY, LEncodeHelper.getEncodedString(String.valueOf(isUserDefinedZapp)));

        LFileHelper.getInstance().writeToFile(file, zappData);

        if (!file.exists() || file.length() == 0) {
            Log.d(ZappInternal.class.getSimpleName(), String.format(ERROR_SAVE_TO_FILE_FAILED_TEXT, file.getName()));
            return false;
        }

        Log.d(ZappInternal.class.getSimpleName(), String.format(SAVE_TO_FILE_SUCCESSFUL_TEXT, zappId, file.getName()));

        return true;
    }

    private void loadDataForBloomFilter() {

        SuccessHandler successHandler = new SuccessHandler() {

            @Override
            public void onSuccess(String result) {

                String bitSet = new String();
                ArrayList<String> hashes = new ArrayList();
                try {
                    JSONObject root = new JSONObject(result);
                    JSONObject resultArray = root.getJSONObject("result");

                    bitSet = resultArray.getString("set");
                    JSONArray hashesArray  = resultArray.getJSONArray("hashes");
                    for(int i = 0; i < hashesArray.length(); i++){
                        hashes.add(hashesArray.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                saveBloomFilterToFile(bitSet, hashes);
            }

        };

        FailHandler failHandler = new FailHandler() {

            @Override
            public void onFail(String errorMessage) {
                Log.d(ZappInternal.class.getSimpleName(), String.format(ERROR_FAILED_TO_LOAD_BLOOM_DATA_TEXT, errorMessage));
            }

        };

        ZappResultHandler resultHandler = new ZappResultHandler(successHandler, failHandler);

        ZappCheckZappIdService.loadOfflineCheckInfo(resultHandler);

    }

    //endregion

    public boolean checkOfflineZID(String zappId) {
        if (bloomFilter == null)
            return false;
        return bloomFilter.contains(zappId.toLowerCase()) || allowNotVerifiedZid;
    }

    public void checkZappId(String zappId, ZappResultHandler resultHandler) {
        ZappCheckZappIdService.checkZappId(zappId, resultHandler);
    }


}
