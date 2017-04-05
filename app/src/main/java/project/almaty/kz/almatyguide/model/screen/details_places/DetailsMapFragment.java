package project.almaty.kz.almatyguide.model.screen.details_places;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.almaty.kz.almatyguide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsMapFragment extends Fragment {

    public static DetailsMapFragment getInstance() {
        Bundle args = new Bundle();
        DetailsMapFragment fragment = new DetailsMapFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public DetailsMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_map, container, false);
    }

}
