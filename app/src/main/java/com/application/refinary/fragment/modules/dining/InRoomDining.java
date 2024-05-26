package com.application.refinary.fragment.modules.dining;

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
import com.application.refinary.adapter.DiningAdapter;
import com.application.refinary.fragment.modules.news.NewsDetails;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.sightseeing.SightseeingPojo;
import com.application.refinary.pojo.wineanddine.Item;
import com.application.refinary.pojo.wineanddine.WineAndDinePojo;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;


public class InRoomDining extends Fragment implements ApiListener {

    private RecyclerView recyclerView;
    private DiningAdapter adapter;
    private Context mContext;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_room_dining, container, false);

        recyclerView = view.findViewById(R.id.dining_recyclerview);
        mContext=view.getContext();
        getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);

        Bundle args=getArguments();
        if(args!=null){
            toolbar_title.setText(args.getString("tool_bar_header"));
        }

        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        getDininglist();


        return view;
    }

    private void getDininglist() {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<WineAndDinePojo> wineAndDineList = api.wineAndDineAPI(headerMap);
        APIResponse.getCallRetrofit(wineAndDineList, "wineAndDineList", mContext, this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {

        try {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            if (apiCallName.equalsIgnoreCase("wineAndDineList")) {
                WineAndDinePojo wineAndDinePojo = (WineAndDinePojo) response.body();
                if (wineAndDinePojo.getStatusCode().equals(200)){
                    List<Item> items = wineAndDinePojo.getData().getItems();
                    adapter = new DiningAdapter(items,mContext,position -> {
                        Fragment fragment=new DiningWebView();
                        Bundle bundle = new Bundle();
                        bundle.putString("url",(items.get(position).getExternalLink()));
                        bundle.putString("title",items.get(position).getItemName());
                        fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
                    });
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
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