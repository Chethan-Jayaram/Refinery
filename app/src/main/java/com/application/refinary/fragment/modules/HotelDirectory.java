package com.application.refinary.fragment.modules;

import android.content.Context;
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
import com.application.refinary.adapter.HotelDirectoryAdapter;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.hoteldirectory.Data;
import com.application.refinary.pojo.hoteldirectory.HotelDirectoryPojo;
import com.application.refinary.pojo.hoteldirectory.Item;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class HotelDirectory extends Fragment implements ApiListener {


    private Context context;
    private HotelDirectoryAdapter adapter;
    private RecyclerView recycler_hotel_directory;
    private ProgressBar loading;
    private TextView tv_hotel_directory,tv_hotel_directory_desc;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel_directory, container, false);
        try {
            context = view.getContext();
            getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
            TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            recycler_hotel_directory = view.findViewById(R.id.recycler_hotel_directory);
            loading = view.findViewById(R.id.loading);
            tv_hotel_directory_desc = view.findViewById(R.id.tv_hotel_directory_desc);
            tv_hotel_directory = view.findViewById(R.id.tv_hotel_directory);

            shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
            shimmerFrameLayout.startShimmer();
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            recycler_hotel_directory.setVisibility(View.GONE);

            Bundle bundle=getArguments();
            if(bundle!=null){
                toolbar_title.setText(bundle.getString("tool_bar_header"));
            }

            getHotelDirectory();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    private void getHotelDirectory() {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<HotelDirectoryPojo> hotelDirectory = api.hotelDirectoryAPI(headerMap);
        APIResponse.getCallRetrofit(hotelDirectory, "hotelDirectory",context , this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        try {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recycler_hotel_directory.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            HotelDirectoryPojo hotelDirectory = (HotelDirectoryPojo) response.body();
            Data data = hotelDirectory.getData();
            tv_hotel_directory.setText(data.getMeta().getHeaderTitle());
            tv_hotel_directory_desc.setText(data.getMeta().getHeaderDescription());
            if (apiCallName.equalsIgnoreCase("hotelDirectory")) {
                if (hotelDirectory.getStatusCode().equals(200)) {
                    List<Item> items = data.getItems();
                    adapter = new HotelDirectoryAdapter(items,context);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                    recycler_hotel_directory.setLayoutManager(mLayoutManager);
                    recycler_hotel_directory.setItemAnimator(new DefaultItemAnimator());
                    recycler_hotel_directory.setAdapter(adapter);
                } else {
                    GlobalClass.showErrorMsg(context, hotelDirectory.getMessage());
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