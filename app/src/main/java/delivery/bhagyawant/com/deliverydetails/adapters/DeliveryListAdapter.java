package delivery.bhagyawant.com.deliverydetails.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import delivery.bhagyawant.com.deliverydetails.R;
import delivery.bhagyawant.com.deliverydetails.activities.DeliveryDetailActivity;
import delivery.bhagyawant.com.deliverydetails.pojos.Delivery;

import static delivery.bhagyawant.com.deliverydetails.utils.Constants.EXTRA_DELIVERY;

public class DeliveryListAdapter extends RecyclerView.Adapter<DeliveryListAdapter.ViewHolder> {

    List<Delivery> deliveries;
    Activity activity;

    public DeliveryListAdapter(List<Delivery> deliveries, Activity activity) {
        this.deliveries = deliveries;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Delivery delivery = deliveries.get(position);

        holder.tvDescription.setText(delivery.getDescription());
        holder.tvAddress.setText(delivery.getLocation().getAddress());

        Picasso.with(activity)
                .load(delivery.getImageUrl())
                .placeholder(R.drawable.ic_loading)
                .fit()
                .into(holder.ivPerson);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DeliveryDetailActivity.class);
                intent.putExtra(EXTRA_DELIVERY, (Serializable) delivery);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.ivPerson)
        ImageView ivPerson;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
