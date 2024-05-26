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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.NotificationAdapter;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.notification.NotificationPojo;
import com.application.refinary.pojo.sightseeing.SightseeingPojo;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class Notification extends Fragment implements ApiListener {

    private RecyclerView notification_recycler;
    private NotificationAdapter adapter;
    private Context mContext;
    private RelativeLayout lyt_empty_notification;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        mContext = view.getContext();
        notification_recycler = view.findViewById(R.id.notification_recycler);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        notification_recycler.setVisibility(View.GONE);
        lyt_empty_notification = view.findViewById(R.id.lyt_empty_notification);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Notifications");
        Bundle args=getArguments();
        /*if(args!=null){
            toolbar_title.setText(args.getString("tool_bar_header"));
        }*/
        getNotificationList();

        return view;
    }

    private void getNotificationList() {

        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<NotificationPojo> tourGuideList = api.notificationAPI(headerMap);
        APIResponse.getCallRetrofit(tourGuideList, "notificationList", mContext, this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {

        try {

            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            notification_recycler.setVisibility(View.VISIBLE);

            if (apiCallName.equalsIgnoreCase("notificationList")) {
                NotificationPojo notificationPojo = (NotificationPojo) response.body();
                if (notificationPojo.getStatusCode().equals(200)){


                    if (notificationPojo.getData().size()>0) {
                        lyt_empty_notification.setVisibility(View.GONE);
                        adapter = new NotificationAdapter(mContext, notificationPojo.getData());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        notification_recycler.setLayoutManager(mLayoutManager);
                        notification_recycler.setNestedScrollingEnabled(false);
                        notification_recycler.setItemAnimator(new DefaultItemAnimator());
                        notification_recycler.setAdapter(adapter);
                    }else{
                        lyt_empty_notification.setVisibility(View.VISIBLE);
                    }
                }else{
                    GlobalClass.showErrorMsg(mContext, notificationPojo.getMessage());
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