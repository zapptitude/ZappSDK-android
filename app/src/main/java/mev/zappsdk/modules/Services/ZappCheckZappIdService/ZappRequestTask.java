package mev.zappsdk.modules.Services.ZappCheckZappIdService;

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

import mev.zappsdk.modules.Helpers.ZappResultHandler;

/**
 * Created by andrew on 05.04.16.
 */
public class ZappRequestTask extends AsyncTask<Void, Void, ZappRequestTask.ZappResult> {

    //region Constants

    public static final String REQUEST_METHOD_GET_TYPE = "GET";

    public static final String RESULT_HANDLER_ERROR_TEXT = "ResultHandler is null.";
    public static final String INPUT_STREAM_ERROR_TEXT = "InputStream is null. Nothing to do.";
    public static final String BUFFER_ERROR_TEXT = "Stream was empty. No point in parsing.";

    //endregion

    //region Properties

    private URL url;
    private String jsonData;
    private ZappResultHandler resultHandler;

    //endregion

    //region Constructors

    public ZappRequestTask(URL url, ZappResultHandler resultHandler) {
        this.url = url;
        this.resultHandler = resultHandler;
    }

    public ZappRequestTask(URL url, String jsonData, ZappResultHandler resultHandler) {
        this.url = url;
        this.jsonData = jsonData;
        this.resultHandler = resultHandler;
    }

    //endregion

    //region Virtual methods

    @Override
    protected ZappResult doInBackground(Void... params) {
        return jsonData != null ? executeDataSending() : executeGetRequest();
    }

    @Override
    protected void onPostExecute(ZappResult result) {
        super.onPostExecute(result);

        if (resultHandler == null) {
            Log.d(ZappRequestTask.class.getSimpleName(), RESULT_HANDLER_ERROR_TEXT);
            return;
        }

        if (result.isSuccess())
            resultHandler.onSuccess(result.getResultString());
        else
            resultHandler.onFail(result.getResultString());
    }

    //endregion

    //region Internal functions

    private ZappResult executeGetRequest() {
        ZappResult result = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(REQUEST_METHOD_GET_TYPE);
            urlConnection.connect();

            // TODO: handle response code
            int status = urlConnection.getResponseCode();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return new ZappResult(false, INPUT_STREAM_ERROR_TEXT);
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return new ZappResult(false, BUFFER_ERROR_TEXT);
            }

            result = new ZappResult(true, buffer.toString());

        } catch (Exception e) {

            return new ZappResult(false, e.getLocalizedMessage());

        } finally {

            urlConnection.disconnect();

        }

        return result;
    }

    private ZappResult executeDataSending() {
        ZappResult result = null;
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out);

            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

            if (inputStream == null) {
                return new ZappResult(false, INPUT_STREAM_ERROR_TEXT);
            }

            result = new ZappResult(true, readStream(inputStream));

        } catch (Exception e) {

            return new ZappResult(false, e.getLocalizedMessage());

        } finally {

            urlConnection.disconnect();

        }
        return result;
    }

    private void writeStream(OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeUTF(jsonData);
    }

    private String readStream(InputStream in) throws IOException {
        DataInputStream dataOutputStream = new DataInputStream(in);
        return dataOutputStream.readUTF();
    }

    //endregion

    //region Inner classes

    public class ZappResult {

        //region Properties

        private boolean success;
        private String resultString;

        //endregion

        //region Getters

        public String getResultString() {
            return resultString;
        }

        public boolean isSuccess() {
            return success;
        }

        //endregion

        //region Constructors

        public ZappResult(boolean success, String resultString) {
            this.success = success;
            this.resultString = resultString;
        }

        //endregion

    }

    //endregion

}
