package delivery.bhagyawant.com.deliverydetails.interfaces;


import java.util.List;

import delivery.bhagyawant.com.deliverydetails.pojos.Delivery;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/deliveries")
    Call<List<Delivery>> getDeliveries();
}
