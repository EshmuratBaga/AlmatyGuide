package project.almaty.kz.almatyguide.model.utils;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import project.almaty.kz.almatyguide.model.adapter.AllCategoryAdapter;
import project.almaty.kz.almatyguide.model.models.distance.DistanceResponse;
import project.almaty.kz.almatyguide.model.models.places.CityPlacesResponse;
import project.almaty.kz.almatyguide.model.models.places.PhotoPlaces;
import project.almaty.kz.almatyguide.model.models.places.ResultPlaces;
import project.almaty.kz.almatyguide.model.rest.ApiClient;
import project.almaty.kz.almatyguide.model.rest.ApiClientDistance;
import project.almaty.kz.almatyguide.model.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    private String type;
    private String ltlng;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle !=null){
            type = bundle.getString(Constants.TYPE_PLACE);
            Log.d("dddd","type" + type);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_base, container, false);
//    }

    public void initApiNear(final RecyclerView recyclerView) {
        ltlng = Constants.UPlat + "," + Constants.UPlng;
        Log.d("ssss","ltlng"+ltlng);
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<CityPlacesResponse> call = apiInterface.getNearPlaces(ltlng,type,Constants.apiKey);
        call.enqueue(new Callback<CityPlacesResponse>() {
            @Override
            public void onResponse(Call<CityPlacesResponse> call, Response<CityPlacesResponse> response) {
                CityPlacesResponse cityPlacesResponse = response.body();
                List<ResultPlaces> resultPlaces = cityPlacesResponse.getResults();
                recyclerView.setAdapter(new AllCategoryAdapter(getActivity(),resultPlaces));
            }

            @Override
            public void onFailure(Call<CityPlacesResponse> call, Throwable t) {
                Log.d("ssss","error" + t.getMessage());
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
