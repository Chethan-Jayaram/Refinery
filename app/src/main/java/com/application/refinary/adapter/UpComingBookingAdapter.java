package com.application.refinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.mystay.Upcoming;

import java.util.List;

public class UpComingBookingAdapter extends RecyclerView.Adapter<UpComingBookingAdapter.ViewHolder> {

    private Context context;
    private List<Upcoming> upComingBooking;
    private AdapterClickListner clickListner;

    public UpComingBookingAdapter(Context context,List<Upcoming> upComingBooking, AdapterClickListner clickListner) {
        this.context = context;
        this.upComingBooking =  upComingBooking;
        this.clickListner = clickListner;
    }


    public interface AdapterClickListner { // create an interface
        void onItemClickListener(int state); // create callback function
    }

    @NonNull
    @Override
    public UpComingBookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activebooking_recycler_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingBookingAdapter.ViewHolder holder, int position) {

        try {

            holder.btn_check_in.setVisibility(View.GONE);
            holder.txt_review.setVisibility(View.GONE);
            if (upComingBooking != null) {
                holder.btn_check_in.setVisibility(View.VISIBLE);
            //    holder.tv_username.setText(upComingBooking.get(position).getGuest().getFirstName());
                holder.tv_username.setText(GlobalClass.sharedPreferences.getString("fName", ""));
                holder.tv_checkin_date_time.setText(GlobalClass.getFormattedDate(GlobalClass.inputdateformat.parse(upComingBooking.get(position).getCheckInDate())));
                holder.tv_checkout_date_time.setText(GlobalClass.getFormattedDate(GlobalClass.inputdateformat.parse(upComingBooking.get(position).getCheckOutDate())));
                holder.btn_check_in.setOnClickListener(v->{
                    clickListner.onItemClickListener(position);
                });
            }/* else if (upComingBooking != null) {
                holder.txt_review.setVisibility(View.VISIBLE);
                holder.tv_username.setText(bookingHistories.get(position).getGuest().getFirstName());
                holder.tv_checkin_date_time.setText(GlobalClass.getFormattedDate(GlobalClass.inputdateformat.parse(bookingHistories.get(position).getCheckinDateTime())));
                holder.tv_checkout_date_time.setText(GlobalClass.getFormattedDate(GlobalClass.inputdateformat.parse(bookingHistories.get(position).getCheckoutDateTime())));
                holder.content_lyt.setOnClickListener(v -> {
                    clickListner.onItemClickListener(true);
                });


                holder.txt_review.setOnClickListener(v->{
                    clickListner.onItemClickListener(false);
                });

            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return upComingBooking.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_username,tv_checkin_date_time,
                tv_checkout_date_time,txt_review;
        Button btn_check_in;
        private LinearLayout content_lyt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_username);

            tv_checkin_date_time = itemView.findViewById(R.id.tv_checkin_date_time);
            tv_checkout_date_time = itemView.findViewById(R.id.tv_checkout_date_time);
            btn_check_in = itemView.findViewById(R.id.btn_check_in);
            txt_review = itemView.findViewById(R.id.txt_review);
            content_lyt = itemView.findViewById(R.id.content_lyt);
        }


    }
}
