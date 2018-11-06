
package delivery.bhagyawant.com.deliverydetails.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Location implements Serializable {

    @DatabaseField
    @SerializedName("lat")
    @Expose
    private Double lat;
    @DatabaseField
    @SerializedName("lng")
    @Expose
    private Double lng;
    @DatabaseField
    @SerializedName("address")
    @Expose
    private String address;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
