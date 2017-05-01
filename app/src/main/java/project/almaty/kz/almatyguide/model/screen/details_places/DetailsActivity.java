package project.almaty.kz.almatyguide.model.screen.details_places;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import project.almaty.kz.almatyguide.R;
import project.almaty.kz.almatyguide.model.adapter.TabsPagerMyAdvertAdapter;
import project.almaty.kz.almatyguide.model.models.distance.DistanceResponse;
import project.almaty.kz.almatyguide.model.models.places.Location;
import project.almaty.kz.almatyguide.model.models.places.ResultPlaces;
import project.almaty.kz.almatyguide.model.rest.ApiClient;
import project.almaty.kz.almatyguide.model.rest.ApiInterface;
import project.almaty.kz.almatyguide.model.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ResultPlaces resultPlaces;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (getIntent() != null){
            resultPlaces = getIntent().getParcelableExtra("result");
            location = getIntent().getParcelableExtra("location");
        }

        initActionBar();

        setTitle(resultPlaces.getName());

        initViewPager();
    }

    private void initViewPager() {
        viewPager = (ViewPager)findViewById(R.id.view_pager_my_advert);
        TabsPagerMyAdvertAdapter adapter = new TabsPagerMyAdvertAdapter(this,getSupportFragmentManager(),resultPlaces,location);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout)findViewById(R.id.tab_layout_details);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return false;
    }
}
