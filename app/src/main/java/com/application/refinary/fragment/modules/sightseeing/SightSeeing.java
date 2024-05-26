package com.application.refinary.fragment.modules.sightseeing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.SightSeeingAdapter;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.sightseeing.SightseeingPojo;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class SightSeeing extends Fragment implements ApiListener {

    private Context mContext;
    private RecyclerView tour_guide_recycler;
    private ProgressBar loading;
    int page = 1, limit = 10;
    String order = "ASC";
    private ShimmerFrameLayout shimmerFrameLayout;
    private Double longitude,latitude;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sight_seeing, container, false);

        mContext=view.getContext();
        getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        tour_guide_recycler = view.findViewById(R.id.tour_guide_recycler);
        loading=view.findViewById(R.id.loading);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        tour_guide_recycler.setVisibility(View.GONE);
        Bundle args=getArguments();
        if(args!=null){
            toolbar_title.setText(args.getString("tool_bar_header"));
        }
        getLocalTourGuideList();



        return view;
    }

    private void getLocalTourGuideList() {
    //    loading.setVisibility(View.VISIBLE);
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<SightseeingPojo> tourGuideList = api.sightSeeingApi(headerMap,order,page,limit);
        APIResponse.getCallRetrofit(tourGuideList, "tourGuideList", mContext, this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        try {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            tour_guide_recycler.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            if (apiCallName.equalsIgnoreCase("tourGuideList")) {
                SightseeingPojo sightseeingPojo = (SightseeingPojo) response.body();
                if (sightseeingPojo.getStatusCode().equals(200)) {
                    /*SightSeeingAdapter adapter= new SightSeeingAdapter(mContext,sightseeingPojo.getData(), position->{
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("google.navigation:q="+sightseeingPojo.getData().getPlaces().get(position).getPlaceName()+","+sightseeingPojo.getData().getPlaces().get(position).getPlaceLocation());
                        startActivity(intent);
                        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new Travel(),"localtour").addToBackStack(null).commit();
                    });*/
                    SightSeeingAdapter adapter= new SightSeeingAdapter(mContext,sightseeingPojo.getData().getPlaces(),position -> {
                        longitude = Double.parseDouble(sightseeingPojo.getData().getPlaces().get(position).getGeoCodes().getLongitude());
                        latitude = Double.parseDouble(sightseeingPojo.getData().getPlaces().get(position).getGeoCodes().getLatitude());
                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        mContext.startActivity(intent);
                    });
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    tour_guide_recycler.setLayoutManager(mLayoutManager);
                    tour_guide_recycler.setNestedScrollingEnabled(false);
                    tour_guide_recycler.setItemAnimator(new DefaultItemAnimator());
                    tour_guide_recycler.setAdapter(adapter);
                } else {
                    GlobalClass.showErrorMsg(mContext, sightseeingPojo.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorListner() {

    }
}