package mev.zappsdk.modules.Helpers;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by andrew on 11.04.16.
 */
public class ZappAlertDialog {

    public ZappAlertDialog() {

    }

    public void showDialog(Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setTitle(title);
        builder.create().show();
    }

}
