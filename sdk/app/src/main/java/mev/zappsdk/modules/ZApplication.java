package mev.zappsdk.modules;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mev.loggersdk.modules.LApplication;
import mev.zappsdk.modules.Helpers.ZappResultHandler;

/**
 * Created by andrew on 08.04.16.
 */
public class ZApplication extends LApplication {

    //region Virtual methods

    @Override
    public void onCreate() {
        super.onCreate();
    }

    //endregion

}
