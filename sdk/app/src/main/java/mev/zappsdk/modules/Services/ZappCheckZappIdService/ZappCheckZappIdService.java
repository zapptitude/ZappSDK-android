package mev.zappsdk.modules.Services.ZappCheckZappIdService;

import mev.loggersdk.modules.LPropertyStorage;
import mev.zappsdk.modules.Helpers.ZappResultHandler;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by andrew on 24.03.16.
 */
public class ZappCheckZappIdService {

    public final static String CHECK_URL_PART = "check";
    public final static String EXPORT_URL_PART = "export";

    //region General methods

    public static void checkZappId(String zappId, ZappResultHandler resultHandler) {

        Uri builtUri = Uri.parse(LPropertyStorage.getInstance().getApiUrl()).buildUpon()
                .appendEncodedPath(CHECK_URL_PART)
                .appendEncodedPath(zappId)
                .build();

        doRequestTask(builtUri, resultHandler);
    }

    public static void loadOfflineCheckInfo(ZappResultHandler resultHandler) {

        Uri builtUri = Uri.parse(LPropertyStorage.getInstance().getApiUrl()).buildUpon()
                .appendEncodedPath(EXPORT_URL_PART)
                .build();
        doRequestTask(builtUri, resultHandler);
    }

    //endregion

    //region Internal methods

    private static void doRequestTask(Uri uri, ZappResultHandler resultHandler) {

        try {

            URL url = new URL(uri.toString());

            new ZappRequestTask(url, resultHandler).execute();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    //endregion

}
