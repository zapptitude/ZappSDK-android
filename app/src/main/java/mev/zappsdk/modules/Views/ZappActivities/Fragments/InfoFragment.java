package mev.zappsdk.modules.Views.ZappActivities.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mev.loggersdk.modules.Helper.LInfoHelper;
import mev.loggersdk.modules.LApplication;
import mev.zappsdk.R;

public class InfoFragment extends Fragment {

    //region Views

    View rootView;

    //endregion

    //region Constructors

    public InfoFragment() {}

    //endregion

    //region Virtual methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_info, container, false);

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
        TextView infoTextView = (TextView) rootView.findViewById(R.id.info_textView);
        infoTextView.setText(String.format(getContext().getString(R.string.how_text), LInfoHelper.getInstance().getApplicationName()));
    }

    private void setListeners() {
        rootView.findViewById(R.id.close_button).setOnTouchListener(closeButtonTouchListener);
    }

    //endregion

    //region Listeners

    private final View.OnTouchListener closeButtonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            FragmentTransaction fragment = getFragmentManager().beginTransaction();
            fragment.replace(R.id.contentFragment, new ZappIdFragment(), "ZappIdFragment");
            fragment.commit();
            return false;
        }
    };

    //endregion

}
