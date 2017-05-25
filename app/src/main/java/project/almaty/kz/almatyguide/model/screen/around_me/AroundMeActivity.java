package project.almaty.kz.almatyguide.model.screen.around_me;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import io.realm.RealmResults;
import project.almaty.kz.almatyguide.R;
import project.almaty.kz.almatyguide.model.screen.atm.AtmFragment;
import project.almaty.kz.almatyguide.model.screen.bank.BankFragment;
import project.almaty.kz.almatyguide.model.screen.food.FoodFragment;
import project.almaty.kz.almatyguide.model.screen.main.MainActivity;
import project.almaty.kz.almatyguide.model.screen.restaurant.RestaurantFragment;
import project.almaty.kz.almatyguide.model.utils.Constants;

public class AroundMeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private Fragment fragment;
    private String type;
    private String title;
    private Bundle bundle;

    private class DrawerItemClickListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectItem(item);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_me);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();

        if (bundle != null){
            Log.d("ssss","sdasdasd");
            type = getIntent().getExtras().getString(Constants.TYPE_PLACE);
            Log.d("ssss",type);
            initTypeFragment();
        }

        initNavigationView(toolbar);
    }

    private void initTypeFragment() {
        if (type.equals("bank")){
            fragment = new BankFragment();
            title = "Банк";
        }
        if (type.equals("atm")){
            fragment = new AtmFragment();
            title = "Банкомат";
        }
        if (type.equals("restaurant")){
            fragment = new RestaurantFragment();
            title = "Ресторан";
        }
        if (type.equals("cafe")){
            fragment = new BankFragment();
            title = "Кафе";
        }
        if (type.equals("shopping_mall")){
            fragment = new RestaurantFragment();
            title = "Супермаркет";
        }
        if (type.equals("bar")){
            fragment = new BankFragment();
            title = "Бар";
        }
        if (type.equals("lodging")){
            fragment = new AtmFragment();
            title = "Хостел";
        }
        if (type.equals("pharmacy")){
            fragment = new RestaurantFragment();
            title = "Аптека";
        }
        setFragment(fragment);
        setActionBarTitle(title);
    }

    private void initNavigationView(Toolbar toolbar) {
        navigationView = (NavigationView)findViewById(R.id.navigationView);
//        navigationView.setItemIconTintList(null);
//        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
//        initNavHeader();
        navigationView.inflateMenu(R.menu.menu_navigation);
        navigationView.setNavigationItemSelectedListener(new DrawerItemClickListener());
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    private void selectItem(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_atm:
                fragment = new AtmFragment();
                title = "Банкомат";
                bundle.putString(Constants.TYPE_PLACE,"atm");
                break;
            case R.id.menu_bank:
                fragment = new BankFragment();
                title = "Банк";
                bundle.putString(Constants.TYPE_PLACE,"bank");
                break;
            case R.id.menu_restaurant:
                fragment = new RestaurantFragment();
                title = "Еда";
                bundle.putString(Constants.TYPE_PLACE,"restaurant");
                break;
            case R.id.menu_bar:
                fragment = new BankFragment();
                title = "Бар";
                bundle.putString(Constants.TYPE_PLACE,"bar");
                break;
            case R.id.menu_hotel:
                fragment = new AtmFragment();
                title = "Супермаркет";
                bundle.putString(Constants.TYPE_PLACE,"shopping_mall");
                break;
            case R.id.menu_hostel:
                fragment = new BankFragment();
                title = "Хостел";
                bundle.putString(Constants.TYPE_PLACE,"lodging");
                break;
            case R.id.menu_medical:
                fragment = new RestaurantFragment();
                title = "Аптека";
                bundle.putString(Constants.TYPE_PLACE,"pharmacy");
                break;
            case R.id.menu_food:
                fragment = new AtmFragment();
                title = "Кафе";
                bundle.putString(Constants.TYPE_PLACE,"cafe");
                break;
            case R.id.menu_home:
                fragment = null;
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            default:
                Log.d("dddd","favorite");
                break;
        }
        if (fragment != null){
            setFragment(fragment);
            setActionBarTitle(title);
        }
        drawerLayout.closeDrawer(navigationView);
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void setActionBarTitle(String item) {
        String title = item;
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}
