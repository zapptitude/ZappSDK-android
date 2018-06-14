package mev.zapptitudeapp.Activities.ZappActivity.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import mev.zapptitudeapp.R;

public class LoggedEventsListFragment extends Fragment {

    //region Properties

    View rootView;
    List<String> loggedItems;

    //endregion

    //region Constructors

    public LoggedEventsListFragment() {

    }

    //endregion

    //region Virtual methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedItems = bundle.getStringArrayList("loggedItems");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_logged_events_list, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, loggedItems);

        listView.setAdapter(adapter);

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
}
