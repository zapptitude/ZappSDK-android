package mev.zappsdk.modules.Views.ZappActivities;

import android.content.Context;
import android.net.NetworkInfo;

import mev.loggersdk.modules.Helper.LInfoHelper;
import mev.zappsdk.R;
import mev.zappsdk.modules.ZappInternal;

/**
 * Created by andrew on 05.04.16.
 */
public class ZappIdActivityModel {

    public Context context;

    public ZappIdActivityModel(Context context) {
        this.context = context;
    }

    public void handleZappId(String zappId) {
        if (zappId.equals(context.getString(R.string.anonymous))) {
            ZappInternal.getInstance().setZappId("");
            // TODO: close activity
        } else if (!zappId.isEmpty()) {
            if (LInfoHelper.getInstance().getConnectionInfo() == NetworkInfo.DetailedState.CONNECTED) {
                // TODO: Success???
                ZappInternal.getInstance().checkZappId(zappId);

//                [internalZapp checkZID:zid succes:^(NSDictionary *response) {
//                    [internalZapp setZappId:zid];
//                    zidBoxImageView.layer.borderColor = [[UIColor colorWithRed:68/255. green:10/255. blue:156/255. alpha:1.0] CGColor];
//                    [weakSelf closeView:weakSelf withAnimationDuralation:0.];
//                    [weakSelf closeView:maskView withAnimationDuralation:0.];
//                    NSLog(@"%@", response);
//                } failure:^(NSString *error) {
//                    [self errorAlertWithZid:zid];
//                    [weakSelf closeView:maskView withAnimationDuralation:0.];
//                    NSLog(@"%@", error);
//                }];
            } else {
                if (ZappInternal.getInstance().checkOfflineZID(zappId)) {
                    ZappInternal.getInstance().setZappId(zappId);
                    // TODO: close activity
                } else {
                    // TODO: Error Alert zappId
                }
            }
        }
    }

}
