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
import mev.zapptitudeapp.Activities.ZappActivity.Models.LogSolveGradTaskModel;
import mev.zapptitudeapp.R;


public class SolveGradTaskFragment extends Fragment implements ILoggable {

    //region Views

    View rootView;

    //endregion

    //region Constructors

    public SolveGradTaskFragment() {}

    //endregion

    //region Virtual methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_solve_grad_task, container, false);

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
        String among = ((EditText) rootView.findViewById(R.id.among_editText)).getText().toString();

        if (task.isEmpty() || context.isEmpty()
                || topics.isEmpty() || expected.isEmpty()
                || actual.isEmpty() || among.isEmpty() || actual.length() > Integer.MAX_VALUE || expected.length() > Integer.MAX_VALUE || among.length() > Integer.MAX_VALUE)
            return "";

        try {
            Zapptitude.logSolveGradTask(task, context,
                    topics, Integer.parseInt(expected),
                    Integer.parseInt(actual), Integer.parseInt(among));
        } catch (Exception e) {
            return e.getMessage();
        }

        return new LogSolveGradTaskModel(task, context, topics, Integer.parseInt(expected), Integer.parseInt(actual), Integer.parseInt(among)).toString();
    }

    //endregion

}
