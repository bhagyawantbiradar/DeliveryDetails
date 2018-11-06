package delivery.bhagyawant.com.deliverydetails.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import delivery.bhagyawant.com.deliverydetails.R;
import delivery.bhagyawant.com.deliverydetails.pojos.Delivery;

import static delivery.bhagyawant.com.deliverydetails.utils.Constants.EXTRA_DELIVERY;

public class DeliveryDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Delivery delivery;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.ivPerson)
    ImageView ivPerson;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        delivery = (Delivery) intent.getSerializableExtra(EXTRA_DELIVERY);
        tvAddress.setText(delivery.getLocation().getAddress());
        tvDescription.setText(delivery.getDescription());
        Picasso.with(this)
                .load(delivery.getImageUrl())
                .placeholder(R.drawable.ic_loading)
                .fit()
                .into(ivPerson);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        mMap.clear();
        LatLng location = new LatLng(delivery.getLocation().getLat(), delivery.getLocation().getLng());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f));
        mMap.addMarker(new MarkerOptions().position(location).title(delivery.getLocation().getAddress()));

    }
}
