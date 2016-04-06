package mev.zappsdk.modules.Views.ZappActivities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import mev.zappsdk.R;
import mev.zappsdk.modules.Views.ZappActivities.ZappIdActivityModel;

public class ZappIdFragment extends Fragment {

    //region Views

    private View rootView;
    private EditText zapIdEditText;

    //endregion

    //region Properties

    private ZappIdActivityModel activityModel = new ZappIdActivityModel(getContext());

    //endregion

    //region Constructors

    public ZappIdFragment() {}

    //endregion

    //region Virtual methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_zapp_id, container, false);

        initControls();
        setListeners();

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

    //region Internal methods

    private void initControls() {
        zapIdEditText = (EditText) rootView.findViewById(R.id.zapIdEditText);
    }

    private void setListeners() {
        rootView.findViewById(R.id.anonymous_button).setOnTouchListener(anonymousButtonTouchListener);
        rootView.findViewById(R.id.question_button).setOnTouchListener(questionButtonTouchListener);
        rootView.findViewById(R.id.ok_button).setOnTouchListener(okButtonTouchListener);
    }

    //endregion

    //region Listeners

    private final View.OnTouchListener anonymousButtonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            zapIdEditText.setText(getString(R.string.anonymous), TextView.BufferType.EDITABLE);
            return false;
        }
    };

    private final View.OnTouchListener questionButtonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            FragmentTransaction fragment = getFragmentManager().beginTransaction();
            fragment.replace(R.id.contentFragment, new InfoFragment(), "InfoFragment");
            fragment.commit();
            return false;
        }
    };

    private final View.OnTouchListener okButtonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            activityModel.handleZappId(zapIdEditText.getText().toString());
            return false;
        }
    };

    //endregion

}
