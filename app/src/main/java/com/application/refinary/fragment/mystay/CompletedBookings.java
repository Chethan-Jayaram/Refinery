package com.application.refinary.fragment.mystay;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.CurrentBookingAdapter;
import com.application.refinary.adapter.PastBookingAdapter;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.mystay.Current;
import com.application.refinary.pojo.mystay.Past;

import java.util.List;


public class CompletedBookings extends Fragment {

    private List<Past> bookingHistory;
    private RecyclerView recycler_active_booking;
    private Context mContext;
    private LinearLayout empty_booking_lyt;
    private TextView tv_msg,tv_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_bookings, container, false);
        try {
            mContext = view.getContext();
            recycler_active_booking = view.findViewById(R.id.recycler_active_booking);
            empty_booking_lyt = view.findViewById(R.id.empty_booking_lyt);
            tv_msg= view.findViewById(R.id.tv_msg);
            tv_title= view.findViewById(R.id.tv_title);
            empty_booking_lyt.setVisibility(View.GONE);
            recycler_active_booking.setVisibility(View.GONE);
            Bundle data = getArguments();
            if (data != null) {
                bookingHistory = data.getParcelableArrayList("list");
            }
            if(bookingHistory.size()>0) {
                setadapter();
            }else{
                empty_booking_lyt.setVisibility(View.VISIBLE);
                tv_msg.setText("Booking history will be displayed here");
                tv_title.setText("Looks empty, No booking history");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void setadapter() {
        recycler_active_booking.setVisibility(View.VISIBLE);

        PastBookingAdapter adapter = new PastBookingAdapter(mContext,(List<Past>) bookingHistory, flag->{


            Fragment fragment = new CompletedBookingDetailsFragment();
            Bundle bundle = new Bundle();

            try {
                bundle.putString("checkin", GlobalClass.getFormattedDate(GlobalClass.inputdateformat.parse(bookingHistory.get(flag).getCheckInDate())));
                bundle.putString("checkout", GlobalClass.getFormattedDate(GlobalClass.inputdateformat.parse(bookingHistory.get(flag).getCheckOutDate())));
            }catch (Exception e){
                e.printStackTrace();
            }
            bundle.putString("booking_id", bookingHistory.get(flag).getBookingConfNo());
            bundle.putString("room_no", String.valueOf(bookingHistory.get(flag).getRoomDetails().get(0).getRoomNumber()));
            bundle.putString("propertyName", bookingHistory.get(flag).getPropertyInfo().getPropertyName());
            bundle.putString("propertyImage", bookingHistory.get(flag).getPropertyInfo().getPropertyImage());
            bundle.putString("propertyLocation", bookingHistory.get(flag).getPropertyInfo().getPropertyLocation());
            fragment.setArguments(bundle);
            //navigate(fragment);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();

            /*if(flag){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new CompletedBookingDetailsFragment()).addToBackStack(null).commit();
            }else{
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new ReviewFragment()).addToBackStack(null).commit();
            }*/
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recycler_active_booking.setLayoutManager(mLayoutManager);
        recycler_active_booking.setNestedScrollingEnabled(false);
        recycler_active_booking.setItemAnimator(new DefaultItemAnimator());
        recycler_active_booking.setAdapter(adapter);
    }
}