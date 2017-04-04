package project.almaty.kz.almatyguide.model.models.places;

/**
 * Created by Andrey on 3/20/2017.
 */

public class CategoryModel {
    private String iconName = "";
    private int img = -1; // menu icon resource id
    private String typePlaces;

    public CategoryModel(String iconName, int img, String typePlaces) {
        this.iconName = iconName;
        this.img = img;
        this.typePlaces = typePlaces;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTypePlaces() {
        return typePlaces;
    }

    public void setTypePlaces(String typePlaces) {
        this.typePlaces = typePlaces;
    }
}
