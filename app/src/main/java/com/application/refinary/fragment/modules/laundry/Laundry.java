package com.application.refinary.fragment.modules.laundry;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.LaundryAdapter;
import com.application.refinary.fragment.general.moduleSegment;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.model.ModuleSegmentModel;
import com.application.refinary.pojo.housekeeping.HouseKeepingPojo;
import com.application.refinary.pojo.housekeeping.WithCategory;
import com.application.refinary.pojo.laundry.Item;
import com.application.refinary.pojo.laundry.LaundryPojo;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;


public class Laundry extends Fragment implements ApiListener {

    private TextView tv_header_view,tv_view_order,item_count,tv_item_price;
    private ExpandableListView expandableListView;
    private LaundryAdapter adapter;
    private Context context;
    private Boolean show_price = false, place_order = false;
    private RelativeLayout bottom_view;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ArrayList<Item> details = new ArrayList<>();
    private Integer items_count= 0;
    private double total_price = 0;
    private ModuleSegmentModel laundryModel = new ModuleSegmentModel();;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_laundry, container, false);

        context = view.getContext();
        item_count = view.findViewById(R.id.item_count);
        tv_item_price = view.findViewById(R.id.tv_item_price);
        tv_header_view = view.findViewById(R.id.tv_header_view);
        expandableListView = view.findViewById(R.id.laundry_recycler);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        bottom_view = view.findViewById(R.id.bottom_view);
        tv_view_order = view.findViewById(R.id.tv_view_order);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        expandableListView.setVisibility(View.GONE);

        Bundle args=getArguments();
        if(args!=null){
            toolbar_title.setText(args.getString("tool_bar_header"));
        }
        getLaundryList();

        tv_view_order.setOnClickListener(v -> {

            try {
                laundryModel.setTitle("Laundry");
                laundryModel.setBooking(GlobalClass.Booking_id);

                List<Item> categoryItems = GlobalClass.removeDuplicates(details);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    categoryItems.forEach(i-> i.getItems().removeIf(j-> j.getCount() == 0));
                }else{
                    for (int i = 0; i < categoryItems.size(); i++) {
                        for (int j = 0; j<  categoryItems.get(i).getItems().size(); j++){
                            if (categoryItems.get(i).getItems().get(j).getCount() == 0) {
                                categoryItems.get(i).getItems().remove(j);
                                j=j-1;//categoryItems.get(i).getChildItems().remove(categoryItems.get(i).getChildItems().get(j));
                            }
                        }
                    }
                }
                if(categoryItems.size()>0){
                    //changes for relase
                    laundryModel.setDetails1(categoryItems);
                    Bundle bundle = new Bundle();
                    Fragment fragment = new DeliveryType();
                    bundle.putString("url", "laundry-book-ticket/");
                    bundle.putString("type", "");
                    bundle.putString("toolbar_title",args.getString("tool_bar_header"));
                    bundle.putParcelable("subcategory", laundryModel);
                    bundle.putString("item_count", String.valueOf(items_count));
                    bundle.putString("total_price", String.valueOf(total_price));
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
                }else{
                    GlobalClass.ShowAlet(context,"Alert","please select at least one item");
                }

                /*Fragment fragment = new DeliveryType();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();*/

            }catch (Exception e){
                e.printStackTrace();
            }


        });

        return view;
    }

    private void getLaundryList() {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<LaundryPojo> weatherCall = api.laundryAPI(headerMap);
        APIResponse.getCallRetrofit(weatherCall, "laundry",context , this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {

        try {

            if (apiCallName.equalsIgnoreCase("laundry")) {

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                expandableListView.setVisibility(View.VISIBLE);

                LaundryPojo laundryPojo = (LaundryPojo) response.body();
                if (laundryPojo.getStatusCode().equals(200)){
                    tv_header_view.setText(laundryPojo.getData().getMeta().getHeaderTitle());
                    List<Item> item =  laundryPojo.getData().getItems();
                    if (laundryPojo.getData().getMeta().getShowPrice()){
                        show_price = true;
                        bottom_view.setVisibility(View.VISIBLE);
                    }else{
                        show_price = false;
                        bottom_view.setVisibility(View.GONE);
                    }
                    if (laundryPojo.getData().getMeta().getCanPlaceOrder()){
                        bottom_view.setVisibility(View.VISIBLE);
                        place_order = true;
                    }else{
                        bottom_view.setVisibility(View.GONE);
                        place_order = false;
                    }
                    details.clear();
                    adapter = new LaundryAdapter(context,laundryPojo.getData().getItems(),show_price,place_order,data -> {
                        details.add(data);
                        items_count= 0;
                        total_price = 0;

                        for (int i = 0; i < laundryPojo.getData().getItems().size();i++){
                            for (int j = 0; j < laundryPojo.getData().getItems().get(i).getItems().size(); j++){
                                items_count += laundryPojo.getData().getItems().get(i).getItems().get(j).getCount();
                                if (laundryPojo.getData().getItems().get(i).getItems().get(j).getCount() >= 0 ) {
                                    total_price += laundryPojo.getData().getItems().get(i).getItems().get(j).getCount() * Double.parseDouble(laundryPojo.getData().getItems().get(i).getItems().get(j).getItemPrice());
                                }
                            }
                        }

                        item_count.setText(items_count+" items");
                        tv_item_price.setText("$"+String.valueOf(total_price));

                        Log.d("items_count",String.valueOf(items_count));
                        Log.d("total_price",String.valueOf(total_price));

                    });
                    expandableListView.setAdapter(adapter);
                }else{
                    GlobalClass.showErrorMsg(context, laundryPojo.getMessage());
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