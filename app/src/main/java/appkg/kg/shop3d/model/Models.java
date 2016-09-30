package appkg.kg.shop3d.model;

import java.io.Serializable;

/**
 * Created by Suimonkul on 17.09.2016.
 */
public class Models implements Serializable {

    String title;
    String description;
    String cover;
    String image_first;
    String image_second;
    String image_third;
    int order;

    public Models(String title, String description, String cover, int order, String img_first, String img_second, String img_third) {
        this.title = title;
        this.description = description;
        this.cover = cover;
        this.image_first = img_first;
        this.image_second = img_second;
        this.image_third = img_third;
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String image) {
        this.cover = image;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getImage_first() {
        return image_first;
    }

    public void setImage_first(String image_first) {
        this.image_first = image_first;
    }

    public String getImage_second() {
        return image_second;
    }

    public void setImage_second(String image_second) {
        this.image_second = image_second;
    }

    public String getImage_third() {
        return image_third;
    }

    public void setImage_third(String image_third) {
        this.image_third = image_third;
    }
}
