package project.almaty.kz.almatyguide.model.screen.details_places;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import project.almaty.kz.almatyguide.R;
import project.almaty.kz.almatyguide.model.models.details_palces.PlaceDetailsResponse;
import project.almaty.kz.almatyguide.model.models.details_palces.Result;
import project.almaty.kz.almatyguide.model.models.places.ResultPlaces;
import project.almaty.kz.almatyguide.model.rest.ApiClient;
import project.almaty.kz.almatyguide.model.rest.ApiInterface;
import project.almaty.kz.almatyguide.model.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsInformationFragment extends Fragment {
    private ResultPlaces resultPlaces;
    private Result resultDetails;
    private ImageView imageView;
    private ImageView imgMap;
    private TextView txtTitle;
    private TextView txtAddress;
    private TextView txtDistance;
    private TextView txtTell;
    private Bundle bundle;

    public static DetailsInformationFragment getInstance(ResultPlaces resultPlaces) {
        Bundle args = new Bundle();
        DetailsInformationFragment fragment = new DetailsInformationFragment();
        args.putParcelable("place", resultPlaces);
        fragment.setArguments(args);

        return fragment;
    }

    public DetailsInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null){
            resultPlaces = bundle.getParcelable("place");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_information, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = (ImageView) view.findViewById(R.id.img_header);
        imgMap = (ImageView) view.findViewById(R.id.img_map);
        txtTitle = (TextView) view.findViewById(R.id.txt_details_title);
        txtAddress = (TextView) view.findViewById(R.id.txt_details_map_address);
        txtDistance = (TextView) view.findViewById(R.id.txt_details_distance);
        txtTell = (TextView) view.findViewById(R.id.txt_details_number_phone);


        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<PlaceDetailsResponse> call = apiInterface.getDetailsPlace(resultPlaces.getPlaceId(), Constants.apiKey);
        call.enqueue(new Callback<PlaceDetailsResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {
                PlaceDetailsResponse placeDetailsResponse = response.body();
                String status = placeDetailsResponse.getStatus();
                if(status.equals("OK")){
                    resultDetails = placeDetailsResponse.getResult();
                    txtTitle.setText(resultDetails.getName());
                    txtTell.setText(resultDetails.getFormattedPhoneNumber());
                    txtAddress.setText(resultDetails.getFormattedAddress());
                    String url = "http://maps.google.com/maps/api/staticmap?center=" + resultDetails.getGeometry().getLocation().getLat() + "," + resultDetails.getGeometry().getLocation().getLng() +
                            "&zoom=13&size=600x300&sensor=false&markers=size:mid%7Ccolor:0x5ac5f8%7Clabel:%7C" + resultDetails.getGeometry().getLocation().getLat() + "," + resultDetails.getGeometry().getLocation().getLng() + "";
                    Picasso.with(getContext()).load(url).centerCrop().fit().into(imgMap);

                    bundle.putDouble("lat", resultDetails.getGeometry().getLocation().getLat());
                    bundle.putDouble("lng", resultDetails.getGeometry().getLocation().getLng());
                }
            }

            @Override
            public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
