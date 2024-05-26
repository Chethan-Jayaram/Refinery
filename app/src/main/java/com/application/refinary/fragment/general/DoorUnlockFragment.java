package com.application.refinary.fragment.general;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.refinary.R;
import com.application.refinary.adapter.DoorUnlockRoomAdapter;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.mystay.Current;
import com.application.refinary.pojo.mystay.MyStayPojo;
import com.application.refinary.pojo.mystay.Past;
import com.application.refinary.pojo.mystay.Upcoming;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class DoorUnlockFragment extends Fragment implements ApiListener   {

    private RecyclerView mRecycler_Active_Booking;
    private Context mContext;
    private APIMethods api;
    private DoorUnlockRoomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_door_unlock, container, false);

        mRecycler_Active_Booking = view.findViewById(R.id.recycler_active_booking);
        mContext = view.getContext();
        api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);

        getBooking();

        return view;
    }

    private void getBooking() {
        //   loading.setVisibility(View.VISIBLE);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<MyStayPojo> hotelDirectory = api.myStayAPI(headerMap);
        APIResponse.getCallRetrofit(hotelDirectory, "bookings",mContext , this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        try {
            if (apiCallName.equalsIgnoreCase("bookings")) {
                MyStayPojo myStayPojo = (MyStayPojo) response.body();
                if (myStayPojo.getStatusCode().equals(200)) {
                    if (myStayPojo.getData().getCurrent() != null){

                        adapter = new DoorUnlockRoomAdapter(mContext,myStayPojo.getData().getCurrent());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        mRecycler_Active_Booking.setLayoutManager(mLayoutManager);
                        mRecycler_Active_Booking.setNestedScrollingEnabled(false);
                        mRecycler_Active_Booking.setItemAnimator(new DefaultItemAnimator());
                        mRecycler_Active_Booking.setAdapter(adapter);

                    }
                } else {
                    GlobalClass.showErrorMsg(mContext, myStayPojo.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorListner() {

    }
}