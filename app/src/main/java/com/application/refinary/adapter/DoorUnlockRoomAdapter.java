package com.application.refinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.mystay.Current;

import java.util.List;

public class DoorUnlockRoomAdapter extends RecyclerView.Adapter<DoorUnlockRoomAdapter.ViewHolder> {

    private Context mContext;
    private List<Current> current;

    public DoorUnlockRoomAdapter(Context mContext, List<Current> current) {
        this.current = current;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.door_unlock_recycler_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_room.setText(current.get(0).getRoomDetails().get(position).getRoomNumber().toString());
        holder.tv_checkin_date_time.setText(GlobalClass.outputdateformat.format(GlobalClass.mUserCheckInDate));
        holder.tv_checkout_date_time.setText(GlobalClass.outputdateformat.format(GlobalClass.mUserCheckOutDate));

    }

    @Override
    public int getItemCount() {
        return current.get(0).getRoomDetails().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_room, tv_checkin_date_time,tv_unlock_door,
                tv_checkout_date_time;
        private LinearLayout content_lyt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_checkin_date_time=itemView.findViewById(R.id.tv_checkin_date_time);
            tv_checkout_date_time=itemView.findViewById(R.id.tv_checkout_date_time);
            content_lyt=itemView.findViewById(R.id.content_lyt);
            tv_room=itemView.findViewById(R.id.tv_room);
            tv_unlock_door=itemView.findViewById(R.id.tv_unlock_door);
        }
    }
}
