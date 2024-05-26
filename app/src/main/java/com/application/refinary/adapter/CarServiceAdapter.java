package com.application.refinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.pojo.carservice.Item;
import com.application.refinary.pojo.carservice.Price;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.stream.Collectors;

public class CarServiceAdapter extends RecyclerView.Adapter<CarServiceAdapter.ViewHolder> {

    private Context mContext;
    private List<Item> data;

    public CarServiceAdapter(Context mContext, List<Item> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_carservice, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try{
            holder.tv_cab_header.setText(data.get(position).getModelName());
            holder.car_desc.setText(data.get(position).getModelDescription());

            holder.lyt_detailed_view.setVisibility(View.VISIBLE);
            CarServiceDetailViewAdapter adapter = new CarServiceDetailViewAdapter(data.get(position).getPrices());
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            holder.recycler_detailed_view.setLayoutManager(mLayoutManager);
            holder.recycler_detailed_view.setNestedScrollingEnabled(false);
            holder.recycler_detailed_view.setItemAnimator(new DefaultItemAnimator());
            holder.recycler_detailed_view.setAdapter(adapter);

            Glide.with(mContext).load(data.get(position).getModelImage()).into(holder.iv_car);



            holder.tv_min_time.setText("Hourly");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Price> carPricing = data.get(position).getPrices().stream().filter(pricing_type -> !(pricing_type.getRateType().equalsIgnoreCase("fixed"))).collect(Collectors.toList());
                if(carPricing.size()>0) {
                    holder.tv_min_time.setText(carPricing.get(0).getDestinationName());
                    holder.tv_hourlyprice.setText("$"+carPricing.get(0).getPickupRate());
                }
            } else {
                for (int i = 0; i < data.get(position).getPrices().size(); i++) {
                    if (!(data.get(position).getPrices().get(i).getRateType().equalsIgnoreCase("fixed"))) {
                        holder.tv_min_time.setText(data.get(position).getPrices().get(i).getDestinationName());
                        holder.tv_hourlyprice.setText("$"+data.get(position).getPrices().get(i).getPickupRate());
                    }
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_cab_header,tv_hourlyprice,tv_min_time,car_desc;
        private LinearLayout lyt_detailed_view;
        private RecyclerView recycler_detailed_view;
        private ImageView iv_car;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_cab_header = itemView.findViewById(R.id.tv_cab_header);
            lyt_detailed_view= itemView.findViewById(R.id.lyt_detailed_view);
            tv_hourlyprice= itemView.findViewById(R.id.tv_hourlyprice);
            tv_min_time= itemView.findViewById(R.id.tv_min_time);
            recycler_detailed_view= itemView.findViewById(R.id.recycler_detailed_view);
            iv_car= itemView.findViewById(R.id.iv_car);
            car_desc = itemView.findViewById(R.id.car_desc);

        }
    }
}
