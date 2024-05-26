package com.application.refinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.pojo.housekeeping.ChildItem;
import com.application.refinary.pojo.ticketdetails.OrderItem;

import org.java_websocket.client.WebSocketClient;

import java.util.List;

public class TicketDetailsAdapter extends RecyclerView.Adapter<TicketDetailsAdapter.ViewHolder> {

    private Context context;
    private List<OrderItem> houseKeepingList;
    private boolean showPrice;
    private boolean showQuantity;
    private WebSocketClient mWebSocketClient;

    public TicketDetailsAdapter(Context context, List<OrderItem> houseKeepingList, boolean showPrice, boolean showQuantity) {
        this.context = context;
        this.houseKeepingList = houseKeepingList;
        this.showPrice = showPrice;
        this.showQuantity = showQuantity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_details_recycler_content, parent, false);
        return new TicketDetailsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_service.setText(houseKeepingList.get(position).getItemName());
        if (showPrice){
            holder.tv_price.setVisibility(View.VISIBLE);
            holder.tv_price.setText("  $"+String.valueOf(houseKeepingList.get(position).getUnitPrice()));
        }else{
            holder.tv_price.setVisibility(View.GONE);
        }

        if (showQuantity){
            holder.tv_quantity.setVisibility(View.VISIBLE);
            holder.tv_quantity.setText(" " +String.valueOf(houseKeepingList.get(position).getItemQuantity()));
        }else{
            holder.tv_quantity.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return houseKeepingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_service,tv_price,tv_quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_service = itemView.findViewById(R.id.tv_service);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
}
