package mev.zapptitudeapp.Activities.ZappActivity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import mev.zappsdk.modules.Zapptitude;
import mev.zapptitudeapp.Activities.ZappActivity.Interfaces.ILoggable;
import mev.zapptitudeapp.Activities.ZappActivity.Models.LogSolveFloatTaskModel;
import mev.zapptitudeapp.R;


public class SolveFloatTaskFragment extends Fragment implements ILoggable {

    //region Views

    View rootView;

    //endregion

    //region Constructors

    public SolveFloatTaskFragment() {}

    //endregion

    //region Virtual methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_solve_float_task, container, false);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //endregion

    //region ILoggable

    @Override
    public String log() {
        String task = ((EditText) rootView.findViewById(R.id.task_editText)).getText().toString();
        String context = ((EditText) rootView.findViewById(R.id.context_editText)).getText().toString();
        String topics = ((EditText) rootView.findViewById(R.id.topics_editText)).getText().toString();
        String expected = ((EditText) rootView.findViewById(R.id.expected_editText)).getText().toString();
        String actual = ((EditText) rootView.findViewById(R.id.actual_editText)).getText().toString();

        if (task.isEmpty() || context.isEmpty()
                || topics.isEmpty() || expected.isEmpty()
                || actual.isEmpty())
            return "";

        Zapptitude.logSolveFloatTask(task, context,
                topics, Float.parseFloat(expected), Float.parseFloat(actual));
        return new LogSolveFloatTaskModel(task, context, topics, Float.parseFloat(expected), Float.parseFloat(actual)).toString();
    }

    //endregion

}
