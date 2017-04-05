package project.almaty.kz.almatyguide.model.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import project.almaty.kz.almatyguide.model.models.places.ResultPlaces;
import project.almaty.kz.almatyguide.model.screen.details_places.DetailsInformationFragment;
import project.almaty.kz.almatyguide.model.screen.details_places.DetailsMapFragment;


/**
 * Created by Andrey on 4/5/2017.
 */

public class TabsPagerMyAdvertAdapter extends FragmentPagerAdapter {

    private String[] tabs;
    private Context context;
    private ResultPlaces resultPlaces;

    public TabsPagerMyAdvertAdapter(Context context,FragmentManager fm,ResultPlaces resultPlaces) {
        super(fm);
        this.context = context;
        this.resultPlaces = resultPlaces;
        tabs = new String[]{
                "Информация",
                "Карта",
        };
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return DetailsInformationFragment.getInstance(resultPlaces);
            case 1:
                return DetailsMapFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
