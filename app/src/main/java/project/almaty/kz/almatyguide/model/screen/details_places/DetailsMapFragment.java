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
public class DetailsMapFragment extends Fragment implements OnMapReadyCallback{
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        Log.d("dddd","onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_map, container, false);
        mMapView = (MapView) view.findViewById(R.id.mapView);

        bundle = getArguments();
        if (bundle != null){
            resultPlaces = bundle.getParcelable(MAP_KEY);
            Log.d("dddd","resPlace" + resultPlaces.getLat());
        }

        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<DistanceResponse> call = apiInterface.getWay(Constants.UPlat + ", " + Constants.UPlng,String.valueOf(resultPlaces.getLat()) + ", " + String.valueOf(resultPlaces.getLng()),true,"ru", Constants.apiKey);
        Log.d("dddd","er");
        call.enqueue(new Callback<DistanceResponse>() {
            @Override
            public void onResponse(Call<DistanceResponse> call, Response<DistanceResponse> response) {
                Log.d("dddd","err");
                DistanceResponse distanceResponse = response.body();
                String status = distanceResponse.getStatus();
                if (status.equals("OK")){
                    Log.d("dddd","status ok");
                    List<Route> routes = distanceResponse.getRoutes();
                    OverviewPolyline points = routes.get(0).getOverviewPolyline();
                    mPoints = PolyUtil.decode(String.valueOf(points.getPoints()));
                    mMapView.onCreate(savedInstanceState);
                    mMapView.onResume(); // needed to get the map to display immediately
                    Log.d("dddd","points" + mPoints.size());
                }else {
                    Log.d("dddd","error");
                }
            }

            @Override
            public void onFailure(Call<DistanceResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("dddd",t.getMessage());
            }
        });

        mMapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
            googleMap = gMap;
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
                line.add(mPoints.get(i));
                latLngBuilder.include(mPoints.get(i));
            }
            googleMap.addPolyline(line);
            int size = getResources().getDisplayMetrics().widthPixels;
            LatLngBounds latLngBounds = latLngBuilder.build();
            CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
            googleMap.moveCamera(track);
    }
}
