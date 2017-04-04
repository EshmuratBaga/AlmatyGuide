package project.almaty.kz.almatyguide.model.screen.atm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.almaty.kz.almatyguide.R;
import project.almaty.kz.almatyguide.model.utils.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AtmFragment extends BaseFragment {
    private RecyclerView recyclerView;


    public AtmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_atm, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_atm);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        initApiNear(recyclerView);
    }

    @Override
    public void initApiNear(RecyclerView recyclerView) {
        super.initApiNear(recyclerView);
    }
}
