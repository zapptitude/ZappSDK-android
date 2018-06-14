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
import mev.zapptitudeapp.Activities.ZappActivity.Models.LogTaskInContextModel;
import mev.zapptitudeapp.R;


public class LogTaskInContextFragment extends Fragment implements ILoggable {

    //region Views

    View rootView;

    //endregion

    //region Constructors

    public LogTaskInContextFragment() {}

    //endregion

    //region Virtual methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_log_task_in_context, container, false);

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
        EditText taskEditText = (EditText) rootView.findViewById(R.id.task_editText);
        EditText contextEditText = (EditText) rootView.findViewById(R.id.context_editText);

        if (taskEditText.getText().toString().isEmpty() || contextEditText.getText().toString().isEmpty())
            return "";

        Zapptitude.logBeginTask(taskEditText.getText().toString(), contextEditText.getText().toString());
        return new LogTaskInContextModel(taskEditText.getText().toString(), contextEditText.getText().toString()).toString();
    }

    //endregion
}
