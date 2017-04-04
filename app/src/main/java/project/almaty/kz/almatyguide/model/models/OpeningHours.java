package project.almaty.kz.almatyguide.model.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Andrey on 3/20/2017.
 */

public class OpeningHours {
    @SerializedName("exceptional_date")
    @Expose
    private List<Object> exceptionalDate = null;
    @SerializedName("open_now")
    @Expose
    private boolean openNow;
    @SerializedName("weekday_text")
    @Expose
    private List<Object> weekdayText = null;

    public List<Object> getExceptionalDate() {
        return exceptionalDate;
    }

    public void setExceptionalDate(List<Object> exceptionalDate) {
        this.exceptionalDate = exceptionalDate;
    }

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public List<Object> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<Object> weekdayText) {
        this.weekdayText = weekdayText;
    }
}
