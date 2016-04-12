package mev.zappsdk.modules.Views.ZappActivities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import mev.zappsdk.R;
import mev.zappsdk.modules.Views.ZappActivities.Fragments.ZappIdFragment;
import mev.zappsdk.modules.ZApplication;

public class ZappIdActivity extends AppCompatActivity {

    //region Properties

    public String zappId;

    //endregion

    //region Virtual Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapp_id);

        if (savedInstanceState == null) {

            ZappIdFragment fragment = new ZappIdFragment();

            Intent intent = getIntent();
            if (intent != null) {
                zappId = intent.getStringExtra("zappId");
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFragment, fragment)
                    .commit();

        }

        // TODO: move this
        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getBaseContext())
                        .setTitle("Location permission")
                        .setMessage("Please allow acc")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
            }
        }



    }

    //endregion

    //region General methods

    public static void requestZappId(String zappId) {
        Intent intent = new Intent(ZApplication.getAppContext(), ZappIdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("zappId", zappId);
        ZApplication.getAppContext().startActivity(intent);
    }

    //endregion

}
