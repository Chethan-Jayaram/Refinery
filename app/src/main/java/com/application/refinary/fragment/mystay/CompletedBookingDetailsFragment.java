package com.application.refinary.fragment.mystay;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.bumptech.glide.Glide;


public class CompletedBookingDetailsFragment extends Fragment {

    private TextView tv_review,tv_booking_id,tv_hotel_name,tv_location,tv_checkin,check_out,tv_email,tv_name,tv_num,tv_room_no;
    private ImageView iv_hotel;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_booking_details, container, false);
        try {

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
            tv_room_no = view.findViewById(R.id.tv_room_no);


            Bundle bundle = getArguments();
            tv_room_no.setText(bundle.getString("room_no"));
            tv_booking_id.setText("Booking ID: " + bundle.getString("booking_id"));
            tv_hotel_name.setText(bundle.getString("propertyName"));
            tv_location.setText(bundle.getString("propertyLocation"));
            tv_checkin.setText(bundle.getString("checkin"));
            check_out.setText(bundle.getString("checkout"));
            tv_email.setText(GlobalClass.sharedPreferences.getString("eMail", ""));
            tv_name.setText(GlobalClass.sharedPreferences.getString("fName", ""));
            tv_num.setText(GlobalClass.sharedPreferences.getString("cNumber", ""));

            Glide.with(mContext).load(bundle.getString("propertyImage")).into(iv_hotel);


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