package com.application.refinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.wineanddine.Item;
import com.bumptech.glide.Glide;

import java.util.List;

public class DiningAdapter extends RecyclerView.Adapter<DiningAdapter.ViewHolder> {

    private List<Item> items;
    private Context mContext;
    private GlobalClass.AdapterClickListner onclicklistner;

    public DiningAdapter(List<Item> items, Context mContext, GlobalClass.AdapterClickListner onclicklistner) {
        this.items = items;
        this.mContext = mContext;
        this.onclicklistner = onclicklistner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dining_recyclerview_content, parent, false);
        return new DiningAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {

            if (items.get(position).getExternalLink() == null ||
                    items.get(position).getExternalLink().equalsIgnoreCase("")){
                holder.bt_3d_view.setVisibility(View.GONE);
            }else{
                holder.bt_3d_view.setVisibility(View.VISIBLE);
            }

            holder.tv_wine_dine_title.setText(items.get(position).getItemName());
            holder.dining_desc.setText(items.get(position).getItemDescription());
            holder.tv_help_line.setText(items.get(position).getHelpLineText());

            Glide.with(mContext).load(items.get(position).getItemImage()).into(holder.img_wine_dine);




            holder.bt_3d_view.setOnClickListener(v -> {
                onclicklistner.onItemClickListener(position);
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dining_desc,tv_wine_dine_title,tv_help_line;
        ImageView img_wine_dine;
        Button bt_3d_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dining_desc = itemView.findViewById(R.id.dining_desc);
            img_wine_dine = itemView.findViewById(R.id.img_wine_dine);
            bt_3d_view = itemView.findViewById(R.id.bt_3d_view);
            tv_wine_dine_title = itemView.findViewById(R.id.tv_wine_dine_title);
            tv_help_line = itemView.findViewById(R.id.tv_help_line);
        }
    }
}
