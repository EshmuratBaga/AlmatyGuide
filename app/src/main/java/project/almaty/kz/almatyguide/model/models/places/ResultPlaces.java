package project.almaty.kz.almatyguide.model.models.places;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 3/20/2017.
 */

public class ResultPlaces implements Parcelable {
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("vicinity")
    @Expose
    private String vicinity;
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("opening_hours")
    @Expose
    private OpeningHours openingHours;
    @SerializedName("photoPlaces")
    @Expose
    private List<PhotoPlaces> photoPlaces = new ArrayList<>();

    public ResultPlaces(Geometry geometry, String icon, String id, String name,
                        String placeId, String reference, String scope,
                        List<String> types, String vicinity, float rating,
                        OpeningHours openingHours, List<PhotoPlaces> photoPlaces) {
        this.geometry = geometry;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.placeId = placeId;
        this.reference = reference;
        this.scope = scope;
        this.types = types;
        this.vicinity = vicinity;
        this.rating = rating;
        this.openingHours = openingHours;
        this.photoPlaces = photoPlaces;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public List<PhotoPlaces> getPhotoPlaces() {
        return photoPlaces;
    }

    public void setPhotoPlaces(List<PhotoPlaces> photoPlaces) {
        this.photoPlaces = photoPlaces;
    }

    protected ResultPlaces(Parcel in) {
        icon = in.readString();
        id = in.readString();
        name = in.readString();
        placeId = in.readString();
        reference = in.readString();
        scope = in.readString();
        types = in.createStringArrayList();
        vicinity = in.readString();
        rating = in.readFloat();
    }

    public static final Creator<ResultPlaces> CREATOR = new Creator<ResultPlaces>() {
        @Override
        public ResultPlaces createFromParcel(Parcel in) {
            return new ResultPlaces(in);
        }

        @Override
        public ResultPlaces[] newArray(int size) {
            return new ResultPlaces[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(placeId);
        dest.writeString(reference);
        dest.writeString(scope);
        dest.writeStringList(types);
        dest.writeString(vicinity);
        dest.writeFloat(rating);
    }
}
