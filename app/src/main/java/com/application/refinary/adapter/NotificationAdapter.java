package com.application.refinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.notification.Data;
import com.bumptech.glide.Glide;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context mContext;
    private List<Data> data;

    public NotificationAdapter(Context mContext, List<Data> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_recycler_content, parent, false);
        return new NotificationAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {

            holder.title.setText(data.get(position).getNotificationTitle());
            holder.desc.setText(data.get(position).getDisplayContent());
            Glide.with(mContext).load(data.get(position).getNotificationImage()).into(holder.img_view);
            holder.date.setText(GlobalClass.getFormattedDate(GlobalClass.inputdateformat.parse(data.get(position).getCreatedDate())));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,desc,date;
        ImageView img_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notification_title);
            desc = itemView.findViewById(R.id.notification_desc);
            date = itemView.findViewById(R.id.notification_date);
            img_view = itemView.findViewById(R.id.img_notification);
        }
    }
}
