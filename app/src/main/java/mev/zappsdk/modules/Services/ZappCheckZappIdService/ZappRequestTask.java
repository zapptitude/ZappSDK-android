package mev.zappsdk.modules.Services.ZappCheckZappIdService;

import android.os.AsyncTask;

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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by andrew on 05.04.16.
 */
public class ZappRequestTask extends AsyncTask<Void, Void, String> {

    //region Constants

    public static final String SUCCESS_RESULT = "Success";

    //endregion

    //region Properties

    private URL url;
    private String jsonData;
    private Runnable successCaseHandler;

    //endregion

    //region Constructors

    public ZappRequestTask(URL url, Runnable successCaseHandler) {
        this.url = url;
        this.successCaseHandler = successCaseHandler;
    }

    public ZappRequestTask(URL url, String jsonData, Runnable successCaseHandler) {
        this.url = url;
        this.jsonData = jsonData;
        this.successCaseHandler = successCaseHandler;
    }

    //endregion

    //region Virtual methods

    @Override
    protected String doInBackground(Void... params) {
        return jsonData != null ? executeDataSending() : executeGetRequest();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (!result.equals(SUCCESS_RESULT))
            return;
        if (successCaseHandler == null)
            return;
        successCaseHandler.run();
    }

    //endregion

    private String executeGetRequest() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int status = urlConnection.getResponseCode();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return "InputStream is null! Nothing to do.";
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return "Stream was empty. No point in parsing.";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SUCCESS_RESULT;
    }

    private String executeDataSending() {
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out);


            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // TODO: Do something with response
            String response = readStream(in);


        } catch (MalformedURLException e) {
            return e.toString();
        } catch (IOException e) {
            return e.toString();
        } finally {
            urlConnection.disconnect();
        }

        return SUCCESS_RESULT;
    }

    private void writeStream(OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeUTF(jsonData);
    }

    private String readStream(InputStream in) throws IOException {
        DataInputStream dataOutputStream = new DataInputStream(in);
        return dataOutputStream.readUTF();
    }

}
