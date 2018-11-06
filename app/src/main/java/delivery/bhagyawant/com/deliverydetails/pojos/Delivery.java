
package delivery.bhagyawant.com.deliverydetails.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Delivery implements Serializable {

    public static final String ID_FIELD = "id";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    @SerializedName("id")
    @Expose
    private Integer id;
    @DatabaseField
    @SerializedName("description")
    @Expose
    private String description;
    @DatabaseField
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    @SerializedName("location")
    @Expose
    private Location location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
