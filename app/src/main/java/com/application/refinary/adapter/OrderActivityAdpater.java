package com.application.refinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.ticketdetails.OrderActivity;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class OrderActivityAdpater extends RecyclerView.Adapter<OrderActivityAdpater.ViewHolder> {

    private Context context;
    private List<OrderActivity> orderActivities;

    public OrderActivityAdpater(Context context, List<OrderActivity> orderActivities) {
        this.orderActivities =orderActivities;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_order_activity, parent, false);
        return new OrderActivityAdpater.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.description.setText(orderActivities.get(position).getStatusDescription());
        holder.time.setText(GlobalClass.dateTimeConverter(orderActivities.get(position).getCreatedDate()));
       if (orderActivities.size() == 1){
           holder.line_view.setVisibility(View.GONE);
       }

       if (position==orderActivities.size()-1){
           holder.line_view.setVisibility(View.GONE);
       }
    }

    @Override
    public int getItemCount() {
        return orderActivities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView time,description;
        TimelineView timeline;
        View line_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.text_timeline_date);
            description = itemView.findViewById(R.id.text_timeline_title);
           // timeline = itemView.findViewById(R.id.timeline);
            line_view = itemView.findViewById(R.id.line_view);


        }
    }
}
