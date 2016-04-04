package mev.zappsdk.modules.Services;

/**
 * Created by andrew on 24.03.16.
 */
public class ZappCheckZIDService {

    public void checkZID() {
    //    success:(void(^)(NSDictionary *response))succes
    //    failure:(void(^)(NSString *errorMessage))failure

//        NSString *urlString = [NSString stringWithFormat:@"%@check/%@", kApiURL, zid];
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
//        NSDictionary *responce = operation.responseObject;
//        NSString *errorMessage;
//        if (responce) {
//            errorMessage = [[responce objectForKey:@"errors"] objectForKey:@"message"];
//        } else {
//            errorMessage = [[error userInfo] objectForKey:@"NSLocalizedDescription"];
//        }
//        failure(errorMessage);
//        NSLog(@"Failure request");
//    }];
//
//        [operation start];
    }

    public void loadOfflineCheckInfoSuccess() {
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
