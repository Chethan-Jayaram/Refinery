package com.application.refinary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.alltickets.Order;

import java.util.ArrayList;
import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.ViewHolder> {

    private Context context;
    private List<Order> orders;
    private GlobalClass.AdapterClickListner adapterClickListner;

    /*public TicketListAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }*/



    public TicketListAdapter(Context context,GlobalClass.AdapterClickListner adapterClickListner){
        this.context = context;
        orders = new ArrayList<>();
        this.adapterClickListner = adapterClickListner;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_recycler_content, parent, false);

        return new ViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tv_ticket_list_number.setText("#" + orders.get(position).getOrderNumber());
           // holder.tv_ticket_list_status.setText(orders.get(position).getRequestType());
            holder.tv_Ticket_list_module_category.setText(orders.get(position).getRequestType());
            //GradientDrawable drawable = (GradientDrawable) holder.tv_ticket_list_status.getBackground();
            //drawable.setColor(Color.parseColor(result.get(position).getCurrentStatus().getEventStyle().getTicketStatusPills().getBackground()));
            holder.tv_started_date_time.setText( GlobalClass.dateTimeConverter(orders.get(position).getCreatedDate()));
            holder.tv_activated_date_time.setText(GlobalClass.dateTimeConverter(orders.get(position).getCreatedDate()));
            holder.content_lyt.setOnClickListener(v -> adapterClickListner.onItemClickListener(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    public void addAll(List<Order> orderList) {
        for (Order result : orderList) {
            add(result);
        }
    }

    private void add(Order result) {
        orders.add(result);
        notifyItemInserted(orders.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_ticket_list_number, tv_ticket_list_status,
                tv_Ticket_list_module_category, tv_activated_date_time, tv_started_date_time;

        LinearLayout content_lyt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content_lyt = itemView.findViewById(R.id.linear_content_lyt);
            tv_ticket_list_number = itemView.findViewById(R.id.tv_ticket_list_number);
            tv_ticket_list_status = itemView.findViewById(R.id.tv_ticket_list_status);
            tv_Ticket_list_module_category = itemView.findViewById(R.id.tv_Ticket_list_module_category);
            tv_activated_date_time = itemView.findViewById(R.id.tv_activated_date_time);
            tv_started_date_time = itemView.findViewById(R.id.tv_started_date_time);
        }
    }
}
