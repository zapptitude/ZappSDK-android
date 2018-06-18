package mev.zappsdk.modules.Services.ZappCheckZappIdService;

import mev.zappsdk.R;
import mev.zappsdk.modules.Helpers.ZappResultHandler;
import mev.zappsdk.modules.ZApplication;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by andrew on 24.03.16.
 */
public class ZappCheckZappIdService {

    //region General methods

    public static void checkZappId(String zappId, ZappResultHandler resultHandler) {

        Uri builtUri = Uri.parse(ZApplication.getAppContext().getString(R.string.apiURL)).buildUpon()
                .appendPath(ZApplication.getAppContext().getString(R.string.check))
                .appendPath(zappId)
                .build();

        doRequestTask(builtUri, resultHandler);
    }

    public static void loadOfflineCheckInfo(ZappResultHandler resultHandler) {

        Uri builtUri = Uri.parse(ZApplication.getAppContext().getString(R.string.apiURL)).buildUpon()
                .appendPath(ZApplication.getAppContext().getString(R.string.export))
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
