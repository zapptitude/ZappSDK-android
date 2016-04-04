package mev.zappsdk.modules;

import android.app.AlertDialog;
import android.net.NetworkInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import mev.loggersdk.modules.Helper.LDataSourceHelper;
import mev.loggersdk.modules.Helper.LEncodeHelper;
import mev.loggersdk.modules.Helper.LFileHelper;
import mev.loggersdk.modules.Helper.LInfoHelper;
import mev.loggersdk.modules.LAppContextStorage;
import mev.loggersdk.modules.Logger;
import mev.zappsdk.modules.Helpers.BloomFilter.BloomFilter;
import mev.zappsdk.modules.Helpers.BloomFilter.Models.ZappBloom;
import mev.zappsdk.modules.Helpers.SerializeHelper;

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
    static final String SESSION_ID_KEY = "session";
    static final String TIME_KEY = "time";
    static final String ZID_SESSION = "session";
    static final String ZID_DURATION = "duration";
    static final String ZID_TIME = "time";

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

    static ZappIdView zappIdView;

    //endregion

    //region Constructors

    public ZappInternal() {
        sessionStartTime = new Date().getTime();
        sessionId = getRandomId();
        taskStartTime = 0;
        zappIdView = new ZappIdView();

        // TODO: for a while
        String appIdentifier = LInfoHelper.getInstance().getPackageName();
        String appVersion = LInfoHelper.getInstance().getVersion();
        String appId = (appIdentifier == null || appVersion == null) ? "unknownApp" : appIdentifier;

        // TODO: What the fuck is this?
//        NSError *error = NULL;
//        NSRegularExpression *regex = [NSRegularExpression regularExpressionWithPattern:@"[^A-Za-z0-9._\\-]"
//        options:NSRegularExpressionCaseInsensitive
//        error:&error];
        // TODO: for a while
        String cleanedAppId = "cleanedAppId";
//        NSString *cleanedAppId = [regex stringByReplacingMatchesInString:appId
//        options:0
//        range:NSMakeRange(0, [appId length])
//        withTemplate:@"_"];

        Logger.getInstance().loggerWithAppID(cleanedAppId);
//        [[Logger sharedInstance] loggerWithAppID:cleanedAppId];


        String zappDirPath = LDataSourceHelper.getInternalStoragePath();

        if (zappDirPath == null || zappDirPath.isEmpty()) {
            // TODO: remove simple print
            System.out.print(String.format("could not create directory %s", zappDirPath));
            zappId = new String(sessionId);
            isUserDefinedZapp = false;
            return;
        }
//
//        [[NSNotificationCenter defaultCenter] addObserver:self
//        selector:@selector(_appWillEnterForeground)
//        name:UIApplicationWillEnterForegroundNotification
//        object:nil];
//

//        // Check if the zapp state file exists and if it does read zapp state from it.
//        // If either the zapp state file does not exit or the reading failed,
//        // initialize the zapp id at random and attempt to save the state.

//        String zappFilePath = zappDirPath + ZAPP_FILE_NAME;
        File zappFile = new File (ZAPP_FILE_NAME);

        // TODO: maybe I should check only last
        if (!zappFile.exists() || !loadFromFile(ZAPP_FILE_NAME)) {
            zappId = getRandomId();
            isUserDefinedZapp = false;
            saveToFile(ZAPP_FILE_NAME);
        }

        allowNotVerifiedZid = false;

        if (LInfoHelper.getInstance().getConnectionInfo() != NetworkInfo.DetailedState.CONNECTED) {
            loadDataForBloomFilter();
        }
    }

    //endregion

    //region ZappId methods

    public void setZappId(String zappId) {
        if (this.zappId == null || this.zappId.isEmpty()) {

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

//        // TODO: check this
//        String zappDirPath = LDataSourceHelper.getInternalStoragePath();
//        if (zappDirPath == null || zappDirPath.isEmpty()) {
//            return;
//        }
//        String zappFilePath = zappDirPath + ZAPP_FILE_NAME;
        saveToFile(LDataSourceHelper.getFile(ZAPP_FILE_NAME));
    }

    public void requestZappId() {
        zappIdView.requestZappId(isUserDefinedZapp ? zappId : null);
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

    // TODO: create model
    public HashMap<String, String> sessionInfo() {
        HashMap<String, String> result = new HashMap();

        result.put(ZID_KEY, zappId);
        result.put(SESSION_ID_KEY, sessionId);
        // TODO: check this
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
        result.put(ZID_TIME, String.valueOf(new Date().getTime() - sessionStartTime));
        return result;
    }

    public void resetTaskStartTime() {
        taskStartTime = 0;
    }

    public boolean saveBloomFilterToFileWithBitSet(String bitSet, ArrayList<String> hashesArray)
    {
        ZappBloom zappBloom = new ZappBloom(bitSet, hashesArray);

        File file = LDataSourceHelper.getFile(ZAPP_BLOOM_FILE_NAME);

        HashMap<String, byte[]> zappData = new HashMap();

        zappData.put(ZAPP_BLOOM_DATA, LEncodeHelper.encode(SerializeHelper.serialize(zappData)));

        LFileHelper.getInstance().writeToFile(file, zappData);

        if (!file.exists() || file.length() == 0) {
            System.out.print(String.format("saveToFile: failed to save %s", file.getName()));
            return false;
        }

        System.out.print(String.format("saveToFile: successfully saved zid %s to %s", zappId, file.getName()));

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
        File zappBloomFilterFile = new File(ZAPP_BLOOM_FILE_NAME);

        // TODO: maybe I should check only last
        if (!zappBloomFilterFile.exists() || checkBloomFilterFile(ZAPP_BLOOM_FILE_NAME)) {
            if (LInfoHelper.getInstance().getConnectionInfo() != NetworkInfo.DetailedState.CONNECTED) {
                AlertDialog alertDialog = new AlertDialog.Builder(LAppContextStorage.getAppContext().getApplicationContext()).setTitle("Zid check error").setMessage("Application is offline and we cannot verify zid. Are you sure you want to continue?").setNeutralButton("Cancel", null).show();
                alertDialog.show();
            }
        }
    }

    // TODO: complete this
    private boolean checkBloomFilterFile(String path) {
//        NSData *zappData = [NSData dataWithContentsOfURL:url];
//        if (!zappData) {
//            ZappLog(@"loadFromURL: failed to load %@", url);
//            return NO;
//        }
//        NSKeyedUnarchiver *decoder = [[NSKeyedUnarchiver alloc] initForReadingWithData:zappData];
//        if (![decoder containsValueForKey:kZappBloomData]) {
//        ZappLog(@"loadFromURL: no key %@ in %@", kZappBloomData, url);
//        return NO;
//        }
//            NSDictionary *dict = [decoder decodeObjectForKey:kZappBloomData];
//            [decoder finishDecoding];
//            ZappLog(@"loadFromURL: successfully loaded bloom data %@ from %@", kZappBloomData, url);
//
//            if ([dict objectForKey:@"set"] && [dict objectForKey:@"hashes"]) {
//            [self configurateBloomFilter:dict];
//            return YES;
//        } else {
//            return NO;
//        }
        return true;
    }

    // TODO: generate random Id
    private String getRandomId() {
//        u_int64_t res = arc4random();
//        return [NSString stringWithFormat:@"Z-%lld", (res << 32) + arc4random()];
        return "Z-someId";
    }

    // TODO: complete this
    private boolean loadFromFile(String path) {
        File file = new File(path);

        if (file == null || !file.exists() || file.length() == 0) {
            System.out.print(String.format("loadFromFile: failed to load %s", file.getName()));
            return false;
        }

        HashMap<String, String> zappData = (HashMap) LFileHelper.getInstance().readHashMapFromFile(file);

        if (zappData == null) {
            System.out.print("zappData is null");
            return false;
        }

        if (!zappData.containsKey(ZAPP_ID_KEY)) {
            System.out.print(String.format("No such key %s in %s", ZAPP_ID_KEY, path));
            return false;
        }
        zappId = LEncodeHelper.getDecodedString(zappData.get(ZAPP_ID_KEY));
        if (!zappData.containsKey(ZAPP_ID_SET_BY_USER_KEY)) {
            System.out.print(String.format("No such key %s in %s", ZAPP_ID_SET_BY_USER_KEY, path));
            return false;
        }
        isUserDefinedZapp = Boolean.valueOf(LEncodeHelper.getDecodedString(zappData.get(ZAPP_ID_SET_BY_USER_KEY)));

        System.out.print(String.format("loadFromFile: successfully loaded zid %s from %s", zappId, path));

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
            System.out.print(String.format("saveToFile: failed to save %s", file.getName()));
            return false;
        }

        System.out.print(String.format("saveToFile: successfully saved zid %s to %s", zappId, file.getName()));

        return true;
    }

    private void loadDataForBloomFilter() {
//        ZappCheckZIDService.loadOfflineCheckInfoSucces
//        [ZappCheckZIDService loadOfflineCheckInfoSucces:^(NSDictionary *response) {
//        if ([response objectForKey:@"result"] && ((NSArray *)[response objectForKey:@"result"]).count) {
//            [self saveBloomFilterToFileWithBitSet:[[response objectForKey:@"result"] objectForKey:@"set"]
//            hashesArray:[[response objectForKey:@"result"] objectForKey:@"hashes"]];
//        }
//    } failure:^(NSError *error) {
//        ZappLog(@"Failed load bloom filter data: %@", error);
//    }];
    }

    //endregion

}
