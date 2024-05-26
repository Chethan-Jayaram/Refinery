package com.application.refinary.fragment.modules.callanengineer;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.fragment.general.TicketDetails;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.model.EmergencyServiceModel;
import com.application.refinary.model.HouseKeepingModel;
import com.application.refinary.model.ModuleSegmentModel;
import com.application.refinary.pojo.emergencyservice.Data;
import com.application.refinary.pojo.emergencyservice.EmergencyServicePojo;
import com.application.refinary.pojo.ticketcreation.Meta;
import com.application.refinary.pojo.ticketresponse.TicketResponse;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;


public class CallAnEngineerFragment extends Fragment implements ApiListener {

    private Spinner support_spinner;
    private Context mContext;
    private List<String> SupportList;
    private HashMap<String,String> SupportMapping;
    private String special_instruction;
    private Integer mSelectedItem;
    private EmergencyServiceModel houseKeepingModel;
    private ArrayList<Data> details=new ArrayList<>();
    private Meta meta = new Meta();
    private ArrayList<Data> data=new ArrayList<>();
    private ProgressBar loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_an_engineer, container, false);

        try {

            mContext = view.getContext();
            getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
            TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            EditText et_support = view.findViewById(R.id.et_support);
            TextView btn_support = view.findViewById(R.id.btn_support);
            support_spinner = view.findViewById(R.id.support_spinner);
            Bundle args=getArguments();
            if(args!=null){
                toolbar_title.setText(args.getString("tool_bar_header"));
            }
            loading = view.findViewById(R.id.loading);

            getSupportSpinnerList();

            support_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        mSelectedItem = position;
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btn_support.setOnClickListener(v -> {
                try {
                    if (mSelectedItem !=0) {
                        if (!et_support.getText().toString().trim().isEmpty()) {
                            loading.setVisibility(View.VISIBLE);
                            special_instruction = et_support.getText().toString().trim();
                            houseKeepingModel = new EmergencyServiceModel();
                            Data categoryItem = new Data();
                            details.clear();
                            categoryItem.setItemUUID(data.get(mSelectedItem).getItemUUID());
                            categoryItem.setPrice(0);
                            categoryItem.setItemQuantity(1);
                            details.add(categoryItem);
                            if(GlobalClass.hasActiveBooking) {
                                houseKeepingModel.setGuestUUID(GlobalClass.Guest_UUID);
                                houseKeepingModel.setBookingConfNo(GlobalClass.Booking_Number);
                                houseKeepingModel.setLocationUUID(GlobalClass.Location_ID);
                                houseKeepingModel.setRequestType("emergency");
                                houseKeepingModel.setRoomNumber(GlobalClass.Room_no);
                                meta.setSpecialInstructions(special_instruction);
                                houseKeepingModel.setServiceDetails(details);
                                houseKeepingModel.setMeta(meta);
                                postSupport(houseKeepingModel);
                            }else{
                                GlobalClass.ShowAlet(mContext, "Message", "Sorry you currently don't have active booking");
                            }
                        } else {
                            GlobalClass.ShowAlet(mContext, "Message", "Please enter vaild message..");
                        }
                    } else {
                        GlobalClass.ShowAlet(mContext, "Message", "Please Select at least one category..");
                    }
                }catch (Exception e){
                    Log.d("support Error",e.getMessage());
                    e.printStackTrace();
                }
            });
        }catch (Exception e){

        }



        return view;
    }

    private void postSupport(EmergencyServiceModel houseKeepingModel) {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<TicketResponse> createOrder = api.emergencyServiceAPI(headerMap, houseKeepingModel);
        APIResponse.getCallRetrofit(createOrder, "emergencyTicket", mContext, this);
    }

    private void getSupportSpinnerList() {
        loading.setVisibility(View.VISIBLE);
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        HashMap headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<EmergencyServicePojo> supportCall = api.emergencyServiceAPI(headerMap);
        APIResponse.callBackgroundRetrofit(supportCall, "GetSupport", mContext, this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        if (apiCallName.equalsIgnoreCase("GetSupport")) {
            EmergencyServicePojo emergencyServicePojo = (EmergencyServicePojo) response.body();
            if (emergencyServicePojo.getStatusCode().equals(200)){
                loading.setVisibility(View.GONE);
                SupportList = new ArrayList<>();
                SupportMapping = new HashMap();
                SupportList.add("Please select any one from the list");
                SupportMapping.put(SupportList.get(0), "");
                for (int i = 0; i < emergencyServicePojo.getData().size(); i++) {
                    SupportList.add(emergencyServicePojo.getData().get(i).getItemName());
                    SupportMapping.put(emergencyServicePojo.getData().get(i).getItemName(), emergencyServicePojo.getData().get(i).getItemUUID());
                    data.add(emergencyServicePojo.getData().get(i));
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item, SupportList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                support_spinner.setAdapter(dataAdapter);

            }

        }else if (apiCallName.equalsIgnoreCase("emergencyTicket")){
            TicketResponse ticketResponse = (TicketResponse) response.body();
            if (ticketResponse.getStatusCode().equals(201)){
                loading.setVisibility(View.GONE);
                GlobalClass.ShowAlet(mContext,"Alert",ticketResponse.getMessage());
                Bundle bundle = new Bundle();
                Fragment fragment = new TicketDetails();
                bundle.putString("id", String.valueOf(ticketResponse.getData().getOrderUUID()));
               // bundle.putString("lyt", ticketResponse.getResult().getLayout());
                bundle.putString("type", "Support");
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
            }else {
                GlobalClass.ShowAlet(mContext,"Alert",ticketResponse.getMessage());
            }
        }

    }

    @Override
    public void onErrorListner() {

    }
}