package com.zapptitude.sampleapp.Activities.ZappActivity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.zapptitude.sampleapp.Activities.ZappActivity.Interfaces.ILoggable;
import com.zapptitude.sampleapp.Activities.ZappActivity.Models.LogSolveBinaryTaskModel;

import mev.zappsdk.modules.Zapptitude;
import mev.zapptitudeapp.R;


public class SolveBinaryTaskFragment extends Fragment implements ILoggable {

    //region Views

    View rootView;

    //endregion

    //region Constructors

    public SolveBinaryTaskFragment() {}

    //endregion

    //region Virtual methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_solve_binary_task, container, false);

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

        if (task.isEmpty() || context.isEmpty()
                || topics.isEmpty())
            return "";

        boolean expected = ((Switch) rootView.findViewById(R.id.expected_switch)).getSplitTrack();
        boolean actual = ((Switch) rootView.findViewById(R.id.actual_switch)).getSplitTrack();

        Zapptitude.logSolveBinaryTask(task, context,
                    topics, expected, actual);

        return new LogSolveBinaryTaskModel(task, context, topics, expected, actual).toString();
    }

    //endregion

}
