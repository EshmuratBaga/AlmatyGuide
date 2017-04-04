package project.almaty.kz.almatyguide.model.rest;

import project.almaty.kz.almatyguide.model.models.distance.DistanceResponse;
import project.almaty.kz.almatyguide.model.models.places.CityPlacesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Andrey on 3/20/2017.
 */

public interface ApiInterface {
    @GET("nearbysearch/json?rankby=distance&sensor=false")
    Call<CityPlacesResponse> getNearPlaces(@Query("location") String ltlng,@Query("types") String type,@Query("key") String key);

    @GET("directions/json?&units=metric")
    Call<DistanceResponse> getDistance(@Query("origin") String ltlng, @Query("destination") String pltplng, @Query("key") String key);
}
