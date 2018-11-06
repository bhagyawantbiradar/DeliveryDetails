package delivery.bhagyawant.com.deliverydetails.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bhagyawant.quickprefs.QuickPref;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import delivery.bhagyawant.com.deliverydetails.R;
import delivery.bhagyawant.com.deliverydetails.adapters.DeliveryListAdapter;
import delivery.bhagyawant.com.deliverydetails.interfaces.ApiInterface;
import delivery.bhagyawant.com.deliverydetails.pojos.Delivery;
import delivery.bhagyawant.com.deliverydetails.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static delivery.bhagyawant.com.deliverydetails.utils.Constants.EXTRA_DELIVERIES;
import static delivery.bhagyawant.com.deliverydetails.utils.Constants.PREF_NAME;
import static delivery.bhagyawant.com.deliverydetails.utils.Constants.SAVED_DELIVERIES;

public class MainActivity extends AppCompatActivity {

    public ApiInterface sApiService;
    private Call<List<Delivery>> deliveryService;
    private List<Delivery> deliveries;
    @BindView(R.id.rvDeliveryList)
    RecyclerView rvDeliveryList;
    @BindView(R.id.btnShowOnMap)
    Button btnShowOnMap;
    private LinearLayoutManager llm;
    private DividerItemDecoration dividerItemDecoration;
    private Gson gson = new Gson();
    private Type deliveriesType = new TypeToken<List<Delivery>>() {
    }.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        if (deliveries.isEmpty()) //Checked with local data. If local storage is empty then call the API to get the delivery details
            callDeliveryDetailsService();
        else loadDataToRecyclerView(deliveries);
    }

    private void loadDataToRecyclerView(List<Delivery> deliveries) {
        rvDeliveryList.setAdapter(new DeliveryListAdapter(deliveries, MainActivity.this));
        btnShowOnMap.setVisibility(View.VISIBLE);
    }

    private void init() {
        llm = new LinearLayoutManager(this);
        rvDeliveryList.setLayoutManager(llm);
        dividerItemDecoration = new DividerItemDecoration(rvDeliveryList.getContext(),
                llm.getOrientation());
        rvDeliveryList.addItemDecoration(dividerItemDecoration);
        String savedJson = QuickPref.getString(this, PREF_NAME, SAVED_DELIVERIES, "[]");
        deliveries = gson.fromJson(savedJson, deliveriesType); // Loaded data from shared preferences

    }

    private void callDeliveryDetailsService() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_message));
        progressDialog.show();
        sApiService = ApiClient.getClient().create(ApiInterface.class);
        deliveryService = sApiService.getDeliveries();

        deliveryService.enqueue(new Callback<List<Delivery>>() {
            @Override
            public void onResponse(Call<List<Delivery>> call, Response<List<Delivery>> response) {
                Log.d("onRespnse", "" + response.body().toString());
                deliveries = response.body();
                loadDataToRecyclerView(deliveries);
                QuickPref.putString(MainActivity.this, PREF_NAME, SAVED_DELIVERIES, gson.toJson(deliveries));
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Delivery>> call, Throwable t) {
                Log.d("nError", "" + t.getMessage());
                Toast.makeText(MainActivity.this, getString(R.string.loading_message), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnShowOnMap:
                Intent intent = new Intent(this, AllLocationsMapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_DELIVERIES, (Serializable) deliveries);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
