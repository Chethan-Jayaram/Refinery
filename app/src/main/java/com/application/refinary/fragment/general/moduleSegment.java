package com.application.refinary.fragment.general;

import android.content.Context;
import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.LaundryServiceModuleAdapter;
import com.application.refinary.adapter.ServiceModuleSegmentAdapter;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.model.HouseKeepingModel;
import com.application.refinary.model.LaundryModel;
import com.application.refinary.model.ModuleSegmentModel;
import com.application.refinary.pojo.housekeeping.ChildItem;
import com.application.refinary.pojo.housekeeping.WithCategory;
import com.application.refinary.pojo.laundry.Item;
import com.application.refinary.pojo.laundry.Item__1;
import com.application.refinary.pojo.laundrydeliverytype.Data;
import com.application.refinary.pojo.laundryticket.LaundryOrderPojo;
import com.application.refinary.pojo.ticketcreation.HouseKeepingTicket;
import com.application.refinary.pojo.ticketcreation.Meta;
import com.application.refinary.pojo.ticketresponse.TicketResponse;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.beigirad.zigzagview.ZigzagView;
import retrofit2.Call;
import retrofit2.Response;

public class moduleSegment extends Fragment implements ApiListener {

    private Context context;
    private ModuleSegmentModel moduleSegmentModel;
    private String url,special_instruction="";
    private APIMethods api;
    private TextView tv_delivery_title,tv_delivery_Description,item_count,tv_item_price,total_amount,tv_surcharge;
    private LinearLayout content_lyt;
    private EditText et_special_instruction;
    private HouseKeepingModel houseKeepingModel = new HouseKeepingModel();
    private List<ChildItem> list = new ArrayList<>();
    private Meta meta = new Meta();
    private Data mDeliveryType;
    private List<Item> laundry_details;
    private ServiceModuleSegmentAdapter adapter;
    private LaundryServiceModuleAdapter laundryServiceModuleAdapter;
    private ZigzagView lyt_bill_details;
    private LaundryModel laundryModel = new LaundryModel();
    private com.application.refinary.pojo.laundryticket.Meta laundryMeta = new com.application.refinary.pojo.laundryticket.Meta();
    private List<Item__1> laundryList = new ArrayList<>();
    private ProgressBar loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module_segment, container, false);


        context = view.getContext();
        loading = view.findViewById(R.id.loading);

        getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
        TextView tv_confirm_button = view.findViewById(R.id.tv_confirm_button);
        ExpandableListView house_keeping_segment_recycler = view.findViewById(R.id.house_keeping_segment_recycler);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        et_special_instruction = view.findViewById(R.id.et_special_instruction);
        lyt_bill_details= view.findViewById(R.id.lyt_bill_details);
        lyt_bill_details.setVisibility(View.GONE);
        item_count = view.findViewById(R.id.item_count);
        tv_item_price = view.findViewById(R.id.tv_item_price);
        total_amount = view.findViewById(R.id.total_amount);
        tv_surcharge = view.findViewById(R.id.tv_surcharge);

        content_lyt = view.findViewById(R.id.content_lyt1);

        tv_delivery_Description = view.findViewById(R.id.tv_delivery_Description);
        tv_delivery_title = view.findViewById(R.id.tv_delivery_title);


        Bundle data = getArguments();
        url = data.getString("url");
        moduleSegmentModel = data.getParcelable("subcategory");
        mDeliveryType = data.getParcelable("Delivery_Type");
        //List<WithCategory> details = data.getParcelableArrayList("List");
        List<WithCategory> details = moduleSegmentModel.getDetails();
        if (url.contains("laundry")) {
           laundry_details = moduleSegmentModel.getDetails1();
            content_lyt.setVisibility(View.VISIBLE);
            Spanned spanned = HtmlCompat.fromHtml(mDeliveryType.getDeliveryTypeDescription(), HtmlCompat.FROM_HTML_MODE_COMPACT);
            tv_delivery_Description.setText(spanned);
            tv_delivery_title.setText(mDeliveryType.getDeliveryTypeName());
         //   houseKeepingModel.setDeliveryType(mDeliveryType.getTitle());
        } else {
            content_lyt.setVisibility(View.GONE);
        }


        toolbar_title.setText(data.getString("toolbar_title"));

        if (url.contains("housekeeping")) {
            tv_confirm_button.setVisibility(moduleSegmentModel.getDetails().size() > 0 ? View.VISIBLE : View.GONE);
            adapter = new ServiceModuleSegmentAdapter(details,context);
            house_keeping_segment_recycler.setAdapter(adapter);
        }else if (url.contains("laundry")){
            lyt_bill_details.setVisibility(View.VISIBLE);
            item_count.setText(data.getString("item_count"));

            Double total = Double.parseDouble(data.getString("total_price")) + mDeliveryType.getSurchargePercentage();

            total_amount.setText("$"+String.valueOf(total));
            tv_item_price.setText("$"+data.getString("total_price"));
            tv_surcharge.setText("$"+String.valueOf(mDeliveryType.getSurchargePercentage()));
            tv_confirm_button.setVisibility(moduleSegmentModel.getDetails1().size() > 0 ? View.VISIBLE : View.GONE);
            laundryServiceModuleAdapter = new LaundryServiceModuleAdapter(laundry_details,context);
            house_keeping_segment_recycler.setAdapter(laundryServiceModuleAdapter);
        }





        /*ServiceModuleSegmentAdapter adapter = new ServiceModuleSegmentAdapter(details,context);
        house_keeping_segment_recycler.setAdapter(adapter);*/

        tv_confirm_button.setOnClickListener(v -> {
            special_instruction = et_special_instruction.getText().toString();

            if (url.contains("laundry")){
                laundryModel.setGuestUUID(GlobalClass.Guest_UUID);
                laundryModel.setBookingConfNo(GlobalClass.Booking_Number);
                laundryModel.setLocationUUID(GlobalClass.Location_ID);
                laundryModel.setRequestType("laundry");
                laundryModel.setRoomNumber(GlobalClass.Room_no);
                laundryMeta.setSpecialInstructions(special_instruction);
                laundryMeta.setDeliveryTypeUUID(mDeliveryType.getDeliveryTypeUUID());
                laundryMeta.setSurchargePercentage(mDeliveryType.getSurchargePercentage());
                laundryModel.setMeta(laundryMeta);
                for (int i = 0; i < laundry_details.size(); i++) {
                    for (int j = 0; j < laundry_details.get(i).getItems().size(); j++) {
                        laundryList.add(laundry_details.get(i).getItems().get(j));
                    }
                }

                laundryModel.setItems(laundryList);

                loading.setVisibility(View.VISIBLE);
                APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "bearer " + GlobalClass.token);
                Call<TicketResponse> createOrder = api.laundryTicketCreationAPI(headerMap, laundryModel);
                APIResponse.getCallRetrofit(createOrder, "LaundryCreateOrder", context, this);


            }else {


                houseKeepingModel.setGuestUUID(GlobalClass.Guest_UUID);
                houseKeepingModel.setBookingConfNo(GlobalClass.Booking_Number);
                houseKeepingModel.setLocationUUID(GlobalClass.Location_ID);
                houseKeepingModel.setRequestType("housekeeping");
                houseKeepingModel.setRoomNumber(GlobalClass.Room_no);
                meta.setSpecialInstructions(special_instruction);
                houseKeepingModel.setMeta(meta);

                for (int i = 0; i < details.size(); i++) {
                    for (int j = 0; j < details.get(i).getChildItems().size(); j++) {
                        list.add(details.get(i).getChildItems().get(j));
                    }
                }

                houseKeepingModel.setItems(list);

                loading.setVisibility(View.VISIBLE);
                APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "bearer " + GlobalClass.token);
                Call<TicketResponse> createOrder = api.ticketCreationAPI(headerMap, houseKeepingModel);
                APIResponse.getCallRetrofit(createOrder, "HouseKeepingCreateOrder", context, this);
            }

        });



        return view;
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {

        if (apiCallName.equalsIgnoreCase("HouseKeepingCreateOrder")) {
            loading.setVisibility(View.GONE);
            TicketResponse ticketResponse = (TicketResponse) response.body();
            if (ticketResponse.getStatusCode().equals(201) || ticketResponse.getStatusCode().equals(200)){
                GlobalClass.ShowAlet(context,"Alert",ticketResponse.getMessage());
                Bundle bundle1 = new Bundle();
                Fragment fragment = new TicketDetails();
                bundle1.putString("id", String.valueOf(ticketResponse.getData().getOrderUUID()));
                // bundle.putString("lyt", ticketResponse.getResult().getLayout());
                bundle1.putString("type", "houseKeeping");
                bundle1.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
                fragment.setArguments(bundle1);
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
            }else {
                GlobalClass.ShowAlet(context,"Alert",ticketResponse.getMessage());
            }

        }else if (apiCallName.equalsIgnoreCase("LaundryCreateOrder")) {
            loading.setVisibility(View.GONE);
            TicketResponse ticketResponse = (TicketResponse) response.body();
            if (ticketResponse.getStatusCode().equals(201) || ticketResponse.getStatusCode().equals(200)){
                GlobalClass.ShowAlet(context,"Alert",ticketResponse.getMessage());
                Bundle bundle1 = new Bundle();
                Fragment fragment = new TicketDetails();
                bundle1.putString("id", String.valueOf(ticketResponse.getData().getOrderUUID()));
                // bundle.putString("lyt", ticketResponse.getResult().getLayout());
                bundle1.putString("type", "laundry");
                bundle1.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) laundryList);
                fragment.setArguments(bundle1);
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
            }else {
                GlobalClass.ShowAlet(context,"Alert",ticketResponse.getMessage());
            }
        }
    }

    @Override
    public void onErrorListner() {
        try{
            loading.setVisibility(View.GONE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}