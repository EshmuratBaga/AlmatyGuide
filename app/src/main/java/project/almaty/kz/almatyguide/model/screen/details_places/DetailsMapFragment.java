package project.almaty.kz.almatyguide.model.screen.details_places;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import project.almaty.kz.almatyguide.R;
import project.almaty.kz.almatyguide.model.models.distance.DistanceResponse;
import project.almaty.kz.almatyguide.model.models.distance.OverviewPolyline;
import project.almaty.kz.almatyguide.model.models.distance.Route;
import project.almaty.kz.almatyguide.model.models.places.Location;
import project.almaty.kz.almatyguide.model.rest.ApiClient;
import project.almaty.kz.almatyguide.model.rest.ApiInterface;
import project.almaty.kz.almatyguide.model.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsMapFragment extends Fragment{
    private static final String MAP_KEY = "map_key";
    private Location resultPlaces;
    private Bundle bundle;
    private MapView mMapView;
    private GoogleMap googleMap;
    private List<LatLng> mPoints = new ArrayList<>();


    public static DetailsMapFragment getInstance(Location resultPlaces) {
        Bundle args = new Bundle();
        DetailsMapFragment fragment = new DetailsMapFragment();
        args.putParcelable(MAP_KEY, resultPlaces);
        fragment.setArguments(args);

        return fragment;
    }

    public DetailsMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("dddd","onCreate");
        bundle = getArguments();
        if (bundle != null){
            resultPlaces = bundle.getParcelable(MAP_KEY);
        }
        Log.d("dddd","lag" + resultPlaces.getLat());
        Log.d("dddd","lag" + resultPlaces.getLng());

        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<DistanceResponse> call = apiInterface.getWay(Constants.UPlat + ", " + Constants.UPlng,String.valueOf(resultPlaces.getLat()) + ", " + String.valueOf(resultPlaces.getLng()),true,"ru", Constants.apiKey);
        call.enqueue(new Callback<DistanceResponse>() {
            @Override
            public void onResponse(Call<DistanceResponse> call, Response<DistanceResponse> response) {
                DistanceResponse distanceResponse = response.body();
                String status = distanceResponse.getStatus();
                if (status.equals("OK")){
                    List<Route> routes = distanceResponse.getRoutes();
                    OverviewPolyline points = routes.get(0).getOverviewPolyline();
                    mPoints = PolyUtil.decode(String.valueOf(points.getPoints()));

                }
            }

            @Override
            public void onFailure(Call<DistanceResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("dddd",t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("dddd","onViewCreate");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_map, container, false);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

//        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
//                googleMap.setMyLocationEnabled(true);
                Log.d("dddd","onMapReady");
                PolylineOptions line = new PolylineOptions();
                line.width(4f).color(R.color.secondary_text);
                Builder latLngBuilder = new Builder();
                for (int i = 0; i < mPoints.size(); i++) {
                    if (i == 0) {
                        MarkerOptions startMarkerOptions = new MarkerOptions()
                                .position(mPoints.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_blue_300_24dp));
                        googleMap.addMarker(startMarkerOptions);
                    } else if (i == mPoints.size() - 1) {
                        MarkerOptions endMarkerOptions = new MarkerOptions()
                                .position(mPoints.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_teal_a700_24dp));
                        googleMap.addMarker(endMarkerOptions);
                    }
                    Log.d("dddd","" + 1);
                    line.add(mPoints.get(i));
                    latLngBuilder.include(mPoints.get(i));
                }
                googleMap.addPolyline(line);
                int size = getResources().getDisplayMetrics().widthPixels;
                LatLngBounds latLngBounds = latLngBuilder.build();
                CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
                googleMap.moveCamera(track);
//                // For showing a move to my location button
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        Log.d("dddd","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        Log.d("dddd","onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        Log.d("dddd","onDestroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
