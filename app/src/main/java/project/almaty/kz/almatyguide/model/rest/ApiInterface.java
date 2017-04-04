package project.almaty.kz.almatyguide.model.rest;

import project.almaty.kz.almatyguide.model.models.CityPlacesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Andrey on 3/20/2017.
 */

public interface ApiInterface {
    @GET("nearbysearch/json?rankby=distance&sensor=false")
    Call<CityPlacesResponse> getNearPlaces(@Query("location") String ltlng,@Query("types") String type,@Query("key") String key);
}
