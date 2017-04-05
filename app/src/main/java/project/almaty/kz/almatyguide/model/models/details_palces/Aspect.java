
package project.almaty.kz.almatyguide.model.models.details_palces;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aspect {

    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("type")
    @Expose
    private String type;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
