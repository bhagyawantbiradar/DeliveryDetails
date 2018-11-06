package delivery.bhagyawant.com.deliverydetails.pojos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Location : Serializable {

    @SerializedName("lat")
    @Expose
    var lat: Double? = null
    @SerializedName("lng")
    @Expose
    var lng: Double? = null
    @SerializedName("address")
    @Expose
    var address: String? = null

}
