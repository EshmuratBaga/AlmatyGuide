package project.almaty.kz.almatyguide.model.models.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrey on 3/20/2017.
 */

public class Geometry {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("viewport")
    @Expose
    private Viewport viewport;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}