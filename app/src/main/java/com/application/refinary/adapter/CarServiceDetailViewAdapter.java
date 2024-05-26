package com.application.refinary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.pojo.carservice.Price;

import java.util.List;

public class CarServiceDetailViewAdapter extends RecyclerView.Adapter<CarServiceDetailViewAdapter.ViewHolder> {

    private List<Price> prices;

    public CarServiceDetailViewAdapter(List<Price> prices) {
        this.prices = prices;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_carservice1, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if(prices.get(position).getRateType().equalsIgnoreCase("fixed")) {
                holder.tv_destination.setText(prices.get(position).getDestinationName());
                holder.tv_fromairport.setText("$"+prices.get(position).getPickupRate());
                holder.tv_toairport.setText("$"+ prices.get(position).getDropRate());
            }else {
                holder.lyt_detailed.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return prices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lyt_detailed;
        private TextView tv_destination,tv_toairport,tv_fromairport;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_destination = itemView.findViewById(R.id.tv_destination);
            tv_toairport = itemView.findViewById(R.id.tv_toairport);
            tv_fromairport= itemView.findViewById(R.id.tv_fromairport);
            lyt_detailed= itemView.findViewById(R.id.lyt_detailed);
        }
    }
}
