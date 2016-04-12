package mev.zappsdk.modules.Views.ZappActivities.Fragments;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import mev.loggersdk.modules.Helper.LInfoHelper;
import mev.zappsdk.R;
import mev.zappsdk.modules.Helpers.ZappAlertDialog;
import mev.zappsdk.modules.Helpers.ZappResultHandler;
import mev.zappsdk.modules.Views.ZappActivities.ZappIdActivity;
import mev.zappsdk.modules.ZappInternal;

public class ZappIdFragment extends Fragment {

    //region Views

    private View rootView;
    private EditText zapIdEditText;
    private ProgressBar progressBar;

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
        zapIdEditText.setText(((ZappIdActivity) getActivity()).zappId);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
    }

    private void setListeners() {
        rootView.findViewById(R.id.anonymous_button).setOnTouchListener(anonymousButtonTouchListener);
        rootView.findViewById(R.id.question_button).setOnTouchListener(questionButtonTouchListener);
        rootView.findViewById(R.id.ok_button).setOnTouchListener(okButtonTouchListener);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    // TODO: fix progress bar
    private void handleZappId(final String zappId) {
        showProgressBar();
        if (zappId.equals(getContext().getString(R.string.anonymous))) {
            ZappInternal.getInstance().setZappId("");
            getActivity().onBackPressed();
            hideProgressBar();
        } else if (!zappId.isEmpty()) {
            if (LInfoHelper.getInstance().getConnectionInfo() == NetworkInfo.DetailedState.CONNECTED) {

                ZappResultHandler.SuccessHandler successHandler = new ZappResultHandler.SuccessHandler() {

                    @Override
                    public void onSuccess(String result) {
                        ZappInternal.getInstance().setZappId(zappId);
                        getActivity().onBackPressed();
                        hideProgressBar();
                    }

                };

                ZappResultHandler.FailHandler failHandler = new ZappResultHandler.FailHandler() {

                    @Override
                    public void onFail(String errorMessage) {
                        ZappAlertDialog zappAlertDialog = new ZappAlertDialog();
                        zappAlertDialog.showDialog(getActivity(), "Login failed", String.format("Sorry, no such user named '%s'", zappId));
                        // TODO: change color
                        hideProgressBar();
                    }

                };

                ZappResultHandler resultHandler = new ZappResultHandler(successHandler, failHandler);
                ZappInternal.getInstance().checkZappId(zappId, resultHandler);

            } else {
                if (ZappInternal.getInstance().checkOfflineZID(zappId)) {
                    ZappInternal.getInstance().setZappId(zappId);
                    getActivity().onBackPressed();
                    hideProgressBar();
                } else {
                    ZappAlertDialog zappAlertDialog = new ZappAlertDialog();
                    zappAlertDialog.showDialog(getActivity(), "Login failed", String.format("Sorry, no such user named '%s'", zappId));
                    // TODO: change color
                    hideProgressBar();
                }
            }
        }
    }

    //endregion

    //region Listeners

    private final View.OnTouchListener anonymousButtonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != MotionEvent.ACTION_DOWN)
                return false;
            zapIdEditText.setText(getString(R.string.anonymous), TextView.BufferType.EDITABLE);
            return true;
        }
    };

    private final View.OnTouchListener questionButtonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != MotionEvent.ACTION_DOWN)
                return false;
            FragmentTransaction fragment = getFragmentManager().beginTransaction();
            fragment.replace(R.id.contentFragment, new InfoFragment(), "InfoFragment");
            fragment.commit();
            return true;
        }
    };

    private final View.OnTouchListener okButtonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != MotionEvent.ACTION_DOWN)
                return false;

            handleZappId(zapIdEditText.getText().toString());
            return true;
        }
    };

    //endregion

}
