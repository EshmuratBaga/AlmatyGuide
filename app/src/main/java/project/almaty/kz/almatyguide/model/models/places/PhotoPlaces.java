package project.almaty.kz.almatyguide.model.models.places;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import project.almaty.kz.almatyguide.model.utils.Constants;

/**
 * Created by Andrey on 3/20/2017.
 */

public class PhotoPlaces implements Parcelable{

    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("html_attributions")
    @Expose
    private List<String> htmlAttributions = null;
    @SerializedName("photo_reference")
    @Expose
    private String photoReference;
    @SerializedName("width")
    @Expose
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getPhotoReference() {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference + Constants.apiKey;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    protected PhotoPlaces(Parcel in) {
        height = in.readInt();
        htmlAttributions = in.createStringArrayList();
        photoReference = in.readString();
        width = in.readInt();
    }

    public static final Creator<PhotoPlaces> CREATOR = new Creator<PhotoPlaces>() {
        @Override
        public PhotoPlaces createFromParcel(Parcel in) {
            return new PhotoPlaces(in);
        }

        @Override
        public PhotoPlaces[] newArray(int size) {
            return new PhotoPlaces[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(height);
        dest.writeStringList(htmlAttributions);
        dest.writeString(photoReference);
        dest.writeInt(width);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
