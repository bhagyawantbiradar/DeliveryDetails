package delivery.bhagyawant.com.deliverydetails.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import delivery.bhagyawant.com.deliverydetails.R;
import delivery.bhagyawant.com.deliverydetails.pojos.Delivery;

import static delivery.bhagyawant.com.deliverydetails.utils.Constants.EXTRA_DELIVERIES;

public class AllLocationsMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    List<Delivery> deliveries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_locations_map);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        deliveries = (List<Delivery>) bundle.getSerializable(EXTRA_DELIVERIES);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        mMap.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (Delivery delivery : deliveries) {
            LatLng latlng = new LatLng(delivery.getLocation().getLat(), delivery.getLocation().getLng());
            mMap.addMarker(new MarkerOptions().position(latlng).title(delivery.getLocation().getAddress()));
            builder.include(latlng);
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        googleMap.moveCamera(cu);
        googleMap.animateCamera(cu);

    }

}
