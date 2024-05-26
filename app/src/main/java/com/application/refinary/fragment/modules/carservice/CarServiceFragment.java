package com.application.refinary.fragment.modules.carservice;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.CarServiceAdapter;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.carservice.CarServicePojo;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;


public class CarServiceFragment extends Fragment implements ApiListener {

    private CarServiceAdapter carServiceAdapter;
    private RecyclerView recyclerView;
    private Context mContext;
    private TextView tv_header_view,tv_header_desc;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_car_service, container, false);

        recyclerView = view.findViewById(R.id.car_Service_recyclerview);
        tv_header_desc = view.findViewById(R.id.tv_header_desc);
        tv_header_view = view.findViewById(R.id.tv_header_view);
        mContext=view.getContext();
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        Bundle args=getArguments();
        if(args!=null){
            toolbar_title.setText(args.getString("tool_bar_header"));
        }
        getCarServices();
        return view;
    }

    private void getCarServices() {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<CarServicePojo> tourGuideList = api.carServiceAPI(headerMap);
        APIResponse.getCallRetrofit(tourGuideList, "carServiceList", mContext, this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {

        try {

            if (apiCallName.equalsIgnoreCase("carServiceList")) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                    CarServicePojo carServicePojo = (CarServicePojo) response.body();
                    if (carServicePojo.getStatusCode().equals(200)){
                        tv_header_desc.setText(carServicePojo.getData().getMeta().getHeaderDescription());
                        tv_header_view.setText(carServicePojo.getData().getMeta().getHeaderTitle());
                        carServiceAdapter = new CarServiceAdapter(mContext,carServicePojo.getData().getItems());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(carServiceAdapter);
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