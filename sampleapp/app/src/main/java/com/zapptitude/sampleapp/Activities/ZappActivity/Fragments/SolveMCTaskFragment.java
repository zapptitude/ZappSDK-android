package com.zapptitude.sampleapp.Activities.ZappActivity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zapptitude.sampleapp.Activities.ZappActivity.Interfaces.ILoggable;
import com.zapptitude.sampleapp.Activities.ZappActivity.Models.LogSolveMCTaskModel;

import mev.zappsdk.modules.Zapptitude;
import mev.zapptitudeapp.R;


public class SolveMCTaskFragment extends Fragment implements ILoggable {

    //region Views

    View rootView;

    //endregion

    //region Constructors

    public SolveMCTaskFragment() {}

    //endregion

    //region Virtual methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_solve_mc_task, container, false);

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
                || actual.isEmpty() || among.isEmpty() || among.length() > Integer.MAX_VALUE)
            return "";

        try {
            Zapptitude.logSolveMCTask(task, context,
                    topics, expected.charAt(0),
                    actual.charAt(0), Integer.parseInt(among));
        } catch (Exception e) {
            return e.getMessage();
        }

        return new LogSolveMCTaskModel(task, context, topics, expected.charAt(0),
                actual.charAt(0), Integer.parseInt(among)).toString();
    }

    //endregion

}
