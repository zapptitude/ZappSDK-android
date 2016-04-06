package mev.zappsdk.modules.Views.ZappActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mev.zappsdk.R;
import mev.zappsdk.modules.Views.ZappActivities.Fragments.ZappIdFragment;

public class ZappIdActivity extends AppCompatActivity {

    //region Virtual Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapp_id);

        if (savedInstanceState == null) {

            ZappIdFragment fragment = new ZappIdFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFragment, fragment)
                    .commit();

        }

    }

    //endregion

}
