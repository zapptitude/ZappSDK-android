package mev.zappsdk.modules.Services.ZappCheckZappIdService;

import mev.loggersdk.modules.LAppContextStorage;
import mev.zappsdk.R;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by andrew on 24.03.16.
 */
public class ZappCheckZappIdService {

    public static void checkZappId(String zappId) {
        // TODO: success
    //    success:(void(^)(NSDictionary *response))succes
    //    failure:(void(^)(NSString *errorMessage))failure

//        HttpURLConnection urlConnection = null;
//        BufferedReader reader = null;
//
//        Uri builtUri = Uri.parse(LAppContextStorage.getAppContext().getString(R.string.apiURL)).buildUpon()
//                .appendPath("check")
//                .appendPath(zappId)
//                .build();
//
//        URL url = null;
//
//        try {
//            url = new URL(builtUri.toString());
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
//
//            int status = urlConnection.getResponseCode();
//
//            InputStream inputStream = urlConnection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//            if (inputStream == null) {
//                // Nothing to do.
//                return;
//            }
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                // But it does make debugging a *lot* easier if you print out the completed
//                // buffer for debugging.
//                buffer.append(line + "\n");
//            }
//
//            if (buffer.length() == 0) {
//                // Stream was empty.  No point in parsing.
//                return;
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    public static void loadOfflineCheckInfoSuccess() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        Uri builtUri = Uri.parse(LAppContextStorage.getAppContext().getString(R.string.apiURL)).buildUpon()
                .appendPath("export")
                .build();

        URL url = null;


//        NSString *urlString = [NSString stringWithFormat:@"%@export", kApiURL];
//
//        NSMutableURLRequest *request = [NSMutableURLRequest new];
//        [request setHTTPMethod:@"GET"];
//        [request setURL:[NSURL URLWithString:urlString]];
//
//        AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
//        operation.responseSerializer = [AFJSONResponseSerializer serializer];
//
//        [operation setCompletionBlockWithSuccess:^(ZappSDK_AFHTTPRequestOperation *operation, id responseObject) {
//        NSDictionary *response = responseObject;
//        succes(response);
//        NSLog(@"Succes request");
//    } failure:^(ZappSDK_AFHTTPRequestOperation *operation, NSError *error) {
//        failure(error);
//        NSLog(@"Failure request");
//    }];
//
//        [operation start];
    }


}
