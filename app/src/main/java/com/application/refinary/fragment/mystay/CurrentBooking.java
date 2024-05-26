package com.application.refinary.fragment.mystay;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.CurrentBookingAdapter;
import com.application.refinary.adapter.UpComingBookingAdapter;
import com.application.refinary.fragment.login.VerifyOtpFragment;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.mystay.Current;
import com.application.refinary.pojo.mystay.Past;
import com.bumptech.glide.Glide;

import java.util.List;

public class CurrentBooking extends Fragment {

    private List<Current> bookingHistory;
    private TextView tv_review,tv_booking_id,tv_hotel_name,tv_location,tv_checkin,check_out,tv_email,tv_name,tv_num,tv_room_no;
    private ImageView iv_hotel;
    private Context mContext;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_booking, container, false);

        try {
            mContext = view.getContext();
            Bundle data = getArguments();
            if (data != null) {
                bookingHistory = data.getParcelableArrayList("list");
            }

            mContext = getContext();
            tv_booking_id = view.findViewById(R.id.tv_booking_id);
            tv_hotel_name = view.findViewById(R.id.tv_hotel_name);
            tv_location = view.findViewById(R.id.tv_location);
            tv_checkin = view.findViewById(R.id.tv_checkin);
            check_out = view.findViewById(R.id.check_out);
            tv_email = view.findViewById(R.id.tv_email);
            tv_name = view.findViewById(R.id.tv_name);
            tv_num = view.findViewById(R.id.tv_num);
            iv_hotel = view.findViewById(R.id.iv_hotel);
            tv_room_no = view.findViewById(R.id.room_no);

           // Log.d("num",bookingHistory.get(0).getRoomDetails().getRoomNumber());

            tv_room_no.setText(String.valueOf(bookingHistory.get(0).getRoomDetails().get(0).getRoomNumber()));
            tv_booking_id.setText("Booking ID: " + bookingHistory.get(0).getBookingConfNo());
            tv_hotel_name.setText(bookingHistory.get(0).getPropertyInfo().getPropertyName());
            tv_location.setText(bookingHistory.get(0).getPropertyInfo().getPropertyLocation());
            tv_checkin.setText(GlobalClass.getFormattedDate(GlobalClass.inputdateformat.parse(bookingHistory.get(0).getCheckInDate())));
            check_out.setText(GlobalClass.getFormattedDate(GlobalClass.inputdateformat.parse(bookingHistory.get(0).getCheckOutDate())));
            tv_email.setText(GlobalClass.sharedPreferences.getString("eMail", ""));
            tv_name.setText(GlobalClass.sharedPreferences.getString("fName", ""));
            tv_num.setText(GlobalClass.sharedPreferences.getString("cNumber", ""));

            Glide.with(mContext).load(bookingHistory.get(0).getPropertyInfo().getPropertyImage()).into(iv_hotel);


            tv_review=view.findViewById(R.id.tv_review);
            tv_review.setOnClickListener(v->{
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new ReviewFragment()).addToBackStack(null).commit();

            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

}