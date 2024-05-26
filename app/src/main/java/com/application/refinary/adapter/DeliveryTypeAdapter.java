package com.application.refinary.adapter;

import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.pojo.laundrydeliverytype.Data;

import java.util.List;

public class DeliveryTypeAdapter extends RecyclerView.Adapter<DeliveryTypeAdapter.ViewHolder> {

    private Context mContext;
    private List<Data> data;
    private int mpreviousSelected=0;

    private ChechkBoxListner listner;


    public interface ChechkBoxListner{
        void onitemclicked(Data deliveryType);
    }

    public DeliveryTypeAdapter(Context mContext, List<Data> data,ChechkBoxListner listner) {

        this.data = data;
        this.mContext = mContext;
        this.listner = listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_type_recycler_content,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_delivery_title.setText(data.get(position).getDeliveryTypeName());

        Spanned spanned = HtmlCompat.fromHtml(data.get(position).getDeliveryTypeDescription(), HtmlCompat.FROM_HTML_MODE_COMPACT);


        holder.tv_delivery_Description.setText(spanned);


        if(data.get(position).getSelected()){
            holder.iv_selected.setVisibility(View.VISIBLE);
        }else{
            holder.iv_selected.setVisibility(View.GONE);
        }

        if(data.get(position).getSelected()){
            holder.content_lyt.setBackgroundColor(ContextCompat.getColor(mContext, R.color.emergency_bg_color));
            holder.iv_selected.setVisibility(View.VISIBLE);
        }else{
            holder.content_lyt.setBackgroundColor(ContextCompat.getColor(mContext, R.color.home_top_bg));
            holder.iv_selected.setVisibility(View.GONE);
        }


        holder.content_lyt.setOnClickListener(v -> {
            try {
                data.get(mpreviousSelected).setSelected(false);
                data.get(position).setSelected(true);
                mpreviousSelected = holder.getAdapterPosition();
                notifyDataSetChanged();
                listner.onitemclicked(data.get(position));
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_delivery_title,tv_delivery_Description;
        private ImageView iv_selected;
        private LinearLayout content_lyt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_selected=itemView.findViewById(R.id.iv_selected);
            content_lyt=itemView.findViewById(R.id.content_lyt);

            tv_delivery_Description=itemView.findViewById(R.id.tv_delivery_Description);
            tv_delivery_title=itemView.findViewById(R.id.tv_delivery_title);
        }
    }
}
