package mev.zapptitudeapp.Activities.ZappActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mev.zappsdk.modules.Zapptitude;
import mev.zapptitudeapp.Activities.ZappActivity.Fragments.LoggedEventsListFragment;
import mev.zapptitudeapp.Activities.ZappActivity.Interfaces.ILoggable;
import mev.zapptitudeapp.Activities.ZappActivity.Fragments.LogEventFragment;
import mev.zapptitudeapp.Activities.ZappActivity.Fragments.LogTaskInContextFragment;
import mev.zapptitudeapp.Activities.ZappActivity.Fragments.SolveBinaryTaskFragment;
import mev.zapptitudeapp.Activities.ZappActivity.Fragments.SolveFloatTaskFragment;
import mev.zapptitudeapp.Activities.ZappActivity.Fragments.SolveGradTaskFragment;
import mev.zapptitudeapp.Activities.ZappActivity.Fragments.SolveIntTaskFragment;
import mev.zapptitudeapp.Activities.ZappActivity.Fragments.SolveMCTaskFragment;
import mev.zapptitudeapp.R;

public class ZappActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //region Constants

    private final int LOG_EVENT_FRAGMENT_INDEX = 0;
    private final int LOG_TASK_IN_CONTEXT_FRAGMENT_INDEX = 1;
    private final int SOLVE_BINARY_TASK_FRAGMENT_INDEX = 2;
    private final int SOLVE_INT_TASK_FRAGMENT_INDEX = 3;
    private final int SOLVE_FLOAT_TASK_FRAGMENT_INDEX = 4;
    private final int SOLVE_GRAD_TASK_FRAGMENT_INDEX = 5;
    private final int SOLVE_MCT_TASK_FRAGMENT_INDEX = 6;

    //endregion

    //region Properties

    Runnable onLog;
    ArrayList<String> loggedItems = new ArrayList();

    //endregion

    //region Virtual methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();
    }

    //endregion

    //region Internal methods

    private void initControls() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tasks_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button initIdButton = (Button) findViewById(R.id.init_id_button);
        initIdButton.setOnTouchListener(initIdButtonTouchListener);

        Button logButton = (Button) findViewById(R.id.log_button);
        logButton.setOnTouchListener(logButtonTouchListener);

        Button viewLoggedButton = (Button) findViewById(R.id.view_logged_button);
        viewLoggedButton.setOnTouchListener(viewLoggedButtonTouchListener);

    }

    private void showLogEventFragment() {
        LogEventFragment fragment = new LogEventFragment();
        showFragment(fragment);
    }

    private void showLogTaskInContextFragment() {
        LogTaskInContextFragment fragment = new LogTaskInContextFragment();
        showFragment(fragment);
    }

    private void showSolveBinaryTaskFragment() {
        SolveBinaryTaskFragment fragment = new SolveBinaryTaskFragment();
        showFragment(fragment);
    }

    private void showSolveIntTaskFragment() {
        SolveIntTaskFragment fragment = new SolveIntTaskFragment();
        showFragment(fragment);
    }

    private void showSolveFloatTaskFragment() {
        SolveFloatTaskFragment fragment = new SolveFloatTaskFragment();
        showFragment(fragment);
    }

    private void showSolveGradTaskFragment() {
        SolveGradTaskFragment fragment = new SolveGradTaskFragment();
        showFragment(fragment);
    }

    private void showSolveMCTaskFragment() {
        SolveMCTaskFragment fragment = new SolveMCTaskFragment();
        showFragment(fragment);
    }

    private void showLoggedEventsListFragment() {
        LoggedEventsListFragment fragment = new LoggedEventsListFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("loggedItems", loggedItems);
        fragment.setArguments(bundle);
        showFragment(fragment);
    }

    private void showFragment(final Fragment fragment) {

        onLog = new Runnable() {
            @Override
            public void run() {
                loggedItems.add(((ILoggable) fragment).log());
            }
        };

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction
                    .replace(R.id.contentFragment, fragment)
                    .commit();
    }

    private void showZappIdActivity() {
        loggedItems = new ArrayList();
        Zapptitude.requestZappId();
    }

    //endregion

    //region Listeners

    private final View.OnTouchListener initIdButtonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != MotionEvent.ACTION_DOWN)
                return false;
            showZappIdActivity();
            return true;
        }
    };


    private final View.OnTouchListener logButtonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentFragment);
            if (motionEvent.getAction() != MotionEvent.ACTION_DOWN || fragment instanceof LoggedEventsListFragment)
                return false;
            onLog.run();
            Toast.makeText(getBaseContext(), "Logged successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    };



    private final View.OnTouchListener viewLoggedButtonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != MotionEvent.ACTION_DOWN)
                return false;
            showLoggedEventsListFragment();
            return true;
        }
    };

    //endregion

    //region Listener events

    public void onItemSelected(AdapterView<?> parent, View view,
                               int index, long id) {
        switch(index) {
            case LOG_EVENT_FRAGMENT_INDEX: showLogEventFragment();
                break;
            case LOG_TASK_IN_CONTEXT_FRAGMENT_INDEX: showLogTaskInContextFragment();
                break;
            case SOLVE_BINARY_TASK_FRAGMENT_INDEX: showSolveBinaryTaskFragment();
                break;
            case SOLVE_INT_TASK_FRAGMENT_INDEX: showSolveIntTaskFragment();
                break;
            case SOLVE_FLOAT_TASK_FRAGMENT_INDEX: showSolveFloatTaskFragment();
                break;
            case SOLVE_GRAD_TASK_FRAGMENT_INDEX: showSolveGradTaskFragment();
                break;
            case SOLVE_MCT_TASK_FRAGMENT_INDEX: showSolveMCTaskFragment();
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(ZappActivity.class.getSimpleName(), "Something gone wrong");
    }

    //endregion

}
