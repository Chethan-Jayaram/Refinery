package com.application.refinary.fragment.modules;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.HouseKeepingAdapter;
import com.application.refinary.fragment.general.moduleSegment;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.model.ModuleSegmentModel;
import com.application.refinary.pojo.housekeeping.ChildItem;
import com.application.refinary.pojo.housekeeping.Data;
import com.application.refinary.pojo.housekeeping.HouseKeepingPojo;
import com.application.refinary.pojo.housekeeping.WithCategory;
import com.application.refinary.pojo.weather.Weather;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;

public class HouseKeeping extends Fragment implements ApiListener {

    private ExpandableListView recyclerView;
    private Context context;
    private ProgressBar loading;
    private HouseKeepingAdapter houseKeepingAdapter;
    private TextView tv_header_view,tv_bottom;
    private Boolean show_price = false, place_order = false;
    private ShimmerFrameLayout shimmerFrameLayout;
    private Integer items_count= 0;
    private double total_price = 0;
    private ModuleSegmentModel houseKeepingModel;
    private List<WithCategory> houseKeepingList = new ArrayList<>();
    // private List<WithCategory> categoryItems = new ArrayList<>();
    private List<WithCategory> data;
    private ArrayList<WithCategory> details = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_house_keeping, container, false);

        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        context = view.getContext();
        getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
     //   TextView tv_bottom = view.findViewById(R.id.tv_bottom);
        recyclerView = view.findViewById(R.id.house_keeping_recycler);
        loading = view.findViewById(R.id.loading);
        tv_header_view = view.findViewById(R.id.tv_header_view);
        tv_bottom = view.findViewById(R.id.tv_bottom);
        tv_bottom.setVisibility(View.GONE);


        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Bundle args=getArguments();
        if(args!=null){
            toolbar_title.setText(args.getString("tool_bar_header"));
        }


        getHouseKeepingList();

        houseKeepingModel = new ModuleSegmentModel();
        tv_bottom.setOnClickListener(v -> {
            try {
                //categoryItems.clear();
                houseKeepingList.clear();
                houseKeepingModel.setTitle("Housekeeping");
                houseKeepingModel.setBooking(GlobalClass.Booking_id);

                List<WithCategory> categoryItems = GlobalClass.removeDuplicates(details);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    categoryItems.forEach(i-> i.getChildItems().removeIf(j-> j.getCount() == 0));
                }else{
                    for (int i = 0; i < categoryItems.size(); i++) {
                        for (int j = 0; j<  categoryItems.get(i).getChildItems().size(); j++){
                            if (categoryItems.get(i).getChildItems().get(j).getCount() == 0) {
                                categoryItems.get(i).getChildItems().remove(j);
                                j=j-1;//categoryItems.get(i).getChildItems().remove(categoryItems.get(i).getChildItems().get(j));
                            }
                        }
                    }
                }

                if(categoryItems.size()>0){
                    //changes for relase
                    houseKeepingModel.setDetails(categoryItems);
                    Bundle bundle = new Bundle();
                    Fragment fragment = new moduleSegment();
                    bundle.putString("url", "housekeeping-book-ticket/");
                    bundle.putString("type", "");
                    bundle.putString("toolbar_title",args.getString("tool_bar_header"));
                    bundle.putParcelable("subcategory", houseKeepingModel);
                    bundle.putParcelableArrayList("List", (ArrayList<? extends Parcelable>) houseKeepingList);
                    //bundle.putParcelableArrayList("category", results);
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
                }else{
                    GlobalClass.ShowAlet(context,"Alert","please select at least one item");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        return view;
    }

    private void getHouseKeepingList() {
      //  loading.setVisibility(View.VISIBLE);
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<HouseKeepingPojo> weatherCall = api.housKeepingAPI(headerMap);
        APIResponse.getCallRetrofit(weatherCall, "houseKeeping",context , this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {

        try {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            HouseKeepingPojo houseKeepingPojo = (HouseKeepingPojo) response.body();
            if (houseKeepingPojo.getStatusCode().equals(200)){
                tv_header_view.setText(houseKeepingPojo.getData().getMetadata().getHeader());
                if (houseKeepingPojo.getData().getMetadata().getShowPrice()){
                    show_price = true;
                    tv_bottom.setVisibility(View.VISIBLE);
                }else{
                    show_price = false;
                    tv_bottom.setVisibility(View.GONE);
                }
                if (houseKeepingPojo.getData().getMetadata().getCanPlaceOrder()){
                    tv_bottom.setVisibility(View.VISIBLE);
                    place_order = true;
                }else{
                    tv_bottom.setVisibility(View.GONE);
                    place_order = false;
                }

                data = (List<WithCategory>) houseKeepingPojo.getData().getWithCategory();
                details.clear();
                houseKeepingAdapter = new HouseKeepingAdapter(context,data,show_price,place_order,data1 -> {

                    details.add(data1);
                    items_count= 0;
                    total_price = 0;

                    for (int i = 0; i < data.size();i++){
                        for (int j = 0; j < data.get(i).getChildItems().size(); j++){
                            items_count += data.get(i).getChildItems().get(j).getCount();
                            if (data.get(i).getChildItems().get(j).getCount() >= 0 ) {
                                total_price += data.get(i).getChildItems().get(j).getCount() * Double.parseDouble(data.get(i).getChildItems().get(j).getPrice());
                            }
                        }
                    }

                    Log.d("items_count",String.valueOf(items_count));
                    Log.d("total_price",String.valueOf(total_price));


                });
                recyclerView.setAdapter(houseKeepingAdapter);
            }else{
                GlobalClass.showErrorMsg(context, houseKeepingPojo.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onErrorListner() {

    }
}