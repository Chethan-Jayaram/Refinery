package com.application.refinary.fragment.modules.laundry;

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
import com.application.refinary.adapter.DeliveryTypeAdapter;
import com.application.refinary.fragment.general.moduleSegment;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.model.ModuleSegmentModel;
import com.application.refinary.pojo.laundry.LaundryPojo;
import com.application.refinary.pojo.laundrydeliverytype.Data;
import com.application.refinary.pojo.laundrydeliverytype.LaundryDeliveryTypePojo;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class DeliveryType extends Fragment implements ApiListener {

    private RecyclerView laundry_delivery_recycler;
    private DeliveryTypeAdapter adapter;
    private Context mContext;
    private Data mDeliveryType;
    private TextView tv_bottom;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ModuleSegmentModel laundryModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_delivery_type, container, false);

        mContext = view.getContext();
        tv_bottom = view.findViewById(R.id.tv_bottom);
        laundry_delivery_recycler = view.findViewById(R.id.laundry_delivery_recycler);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        Bundle args=getArguments();

        toolbar_title.setText("Laundry Services");

        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        laundry_delivery_recycler.setVisibility(View.GONE);

        if(args!=null){
          //  toolbar_title.setText(args.getString("tool_bar_header"));
        }
        getDeliveryTypeList();
        laundryModel = args.getParcelable("subcategory");

        tv_bottom.setOnClickListener(v->{
            if(mDeliveryType!=null) {
                /*     if (validateDay(mDeliveryType.getTitle())) {*/

                Bundle bundle = new Bundle();
                Fragment fragment = new moduleSegment();
                bundle.putString("url", args.getString("url"));
                bundle.putString("type", "");
                bundle.putString("toolbar_title",args.getString("toolbar_title"));
                bundle.putParcelable("subcategory", laundryModel);
                bundle.putParcelable("Delivery_Type", mDeliveryType);
                bundle.putString("tool_bar_header", args.getString("tool_bar_header"));
                bundle.putString("item_count",args.getString("item_count"));
                bundle.putString("total_price", args.getString("total_price"));
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();


            }else{
                GlobalClass.ShowAlet(mContext,"Alert","please select a Delivery type to continue");

            }
        });

        return view;
    }

    private void getDeliveryTypeList() {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<LaundryDeliveryTypePojo> weatherCall = api.deliveryTypeAPI(headerMap);
        APIResponse.getCallRetrofit(weatherCall, "deliverytype",mContext , this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        try {

            if (apiCallName.equalsIgnoreCase("deliverytype")) {

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                laundry_delivery_recycler.setVisibility(View.VISIBLE);
                LaundryDeliveryTypePojo laundryDeliveryTypePojo = (LaundryDeliveryTypePojo) response.body();
                if (laundryDeliveryTypePojo.getStatusCode().equals(200)){

                    adapter = new DeliveryTypeAdapter(mContext,laundryDeliveryTypePojo.getData(),mDeliveryTypeClicked ->{
                        mDeliveryType = mDeliveryTypeClicked;
                    });
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    laundry_delivery_recycler.setLayoutManager(mLayoutManager);
                    laundry_delivery_recycler.setItemAnimator(new DefaultItemAnimator());
                    laundry_delivery_recycler.setAdapter(adapter);

                }else {
                    GlobalClass.showErrorMsg(mContext, laundryDeliveryTypePojo.getMessage());
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