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
import com.application.refinary.pojo.mystay.Past;

import java.text.ParseException;
import java.util.List;

public class PastBookingAdapter extends RecyclerView.Adapter<PastBookingAdapter.ViewHolder>{

    private Context mContext;
    private List<Past> bookingHistory;
    private AdapterClickListner clickListner;

    public PastBookingAdapter(Context mContext, List<Past> bookingHistory, AdapterClickListner clickListner) {
        this.bookingHistory = bookingHistory;
        this.clickListner = clickListner;
        this.mContext = mContext;
    }

    public interface AdapterClickListner { // create an interface
        void onItemClickListener(int state); // create callback function
    }

    @NonNull
    @Override
    public PastBookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activebooking_recycler_content, parent, false);

        return new PastBookingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastBookingAdapter.ViewHolder holder, int position) {
       // holder.btn_check_in.setVisibility(View.GONE);
        holder.txt_review.setVisibility(View.GONE);
        if (bookingHistory != null) {
                holder.btn_check_in.setVisibility(View.GONE);
            //    holder.tv_username.setText(upComingBooking.get(position).getGuest().getFirstName());
            holder.tv_username.setText(GlobalClass.sharedPreferences.getString("fName", ""));
            try {
                holder.tv_checkin_date_time.setText(GlobalClass.getFormattedDate(GlobalClass.inputdateformat.parse(bookingHistory.get(position).getCheckInDate())));
                holder.tv_checkout_date_time.setText(GlobalClass.getFormattedDate(GlobalClass.inputdateformat.parse(bookingHistory.get(position).getCheckOutDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.btn_check_in.setOnClickListener(v->{
                clickListner.onItemClickListener(position);
            });
        }

    }

    @Override
    public int getItemCount() {
        return bookingHistory.size();
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
