package com.application.refinary.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.pojo.hoteldirectory.Data;
import com.application.refinary.pojo.hoteldirectory.Item;

import java.util.List;

public class HotelDirectoryAdapter extends RecyclerView.Adapter<HotelDirectoryAdapter.MyViewHolder> {

    private List<Item> items;
    private Context context;

    public HotelDirectoryAdapter(List<Item> items, Context context) {

        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public HotelDirectoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_hotel_directory_content, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelDirectoryAdapter.MyViewHolder holder, int position) {

        try {
            holder.tv_title.setText(items.get(position).getItemName());
           // holder.tv_desc.setText(items.get(position).getItemDescription());

         //   Spanned spanned = HtmlCompat.fromHtml(items.get(position).getItemDescription(), HtmlCompat.FROM_HTML_MODE_COMPACT);

         //   Log.d("desc", String.valueOf(spanned));

          //  holder.tv_desc.setText(HtmlCompat.fromHtml(items.get(position).getItemDescription(), 0));

          //  holder.tv_desc.setText(spanned);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.tv_desc.setText(Html.fromHtml(items.get(position).getItemDescription(),Html.FROM_HTML_MODE_LEGACY));
            } else {
                holder.tv_desc.setText(Html.fromHtml(items.get(position).getItemDescription()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title, tv_desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
