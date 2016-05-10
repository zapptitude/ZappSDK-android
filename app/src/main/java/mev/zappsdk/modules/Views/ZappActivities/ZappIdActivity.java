package mev.zappsdk.modules.Views.ZappActivities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import mev.loggersdk.modules.Helper.LInfoHelper;
import mev.loggersdk.modules.Logger;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PLAYGROUND", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            Log.d("PLAYGROUND", "Permission is granted");
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PLAYGROUND", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        } else {
            Log.d("PLAYGROUND", "Permission is granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch(requestCode){
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PLAYGROUND", "Permission has been granted");
                } else {
                    Log.d("PLAYGROUND", "Permission has been denied or request cancelled");
                }
            break;
            default:
            break;
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
