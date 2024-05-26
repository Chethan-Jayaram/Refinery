package com.application.refinary.fragment.general;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.OrderActivityAdpater;
import com.application.refinary.adapter.TicketDetailsAdapter;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.housekeeping.ChildItem;
import com.application.refinary.pojo.laundry.Item__1;
import com.application.refinary.pojo.ticketdetails.OrderActivity;
import com.application.refinary.pojo.ticketdetails.TicketsDetailsPojo;
import com.application.refinary.pojo.ticketdetailssocketpojo.TicketDetailsSocketPojo;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.java_websocket.client.WebSocketClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Response;


public class TicketDetails extends Fragment implements ApiListener {

    private Context context;
    private TextView tv_ticket_number, tv_ticket_status,tv_service_name,
            tv_guest_name, tv_guest_room_no, tv_quantity, tv_price;
    private RecyclerView tikcet_details_recylcer, ticket_status_adapter;
    private String lyt, type;
    private String id;
    private TextView toolbar_title, tv_special_instruction;
    private LinearLayout special_instruction_lyt,service_headers;
    //  private ProgressBar loading;
    private RelativeLayout lyt_tkt_dtls;
    private OrderActivityAdpater activityAdpater;
    private List<ChildItem> houseKeepingList = new ArrayList<>();
    private List<Item__1> laundryList = new ArrayList<>();
    private TicketDetailsAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    private ShimmerFrameLayout shimmerFrameLayout;
    private boolean showPrice = false,showQuantity = false;
    private List<OrderActivity> orderItems = new ArrayList<>();
    private Socket mSocket;
    JSONObject obj=new JSONObject();
    Emitter.Listener onNewMessage ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ticket_details, container, false);

        context = view.getContext();
        ImageView btn_back=getActivity().findViewById(R.id.btn_back);
        btn_back.setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.nav_menu).setVisibility(View.GONE);
        getActivity().findViewById(R.id.iv_sos).setVisibility(View.GONE);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        tv_service_name = view.findViewById(R.id.tv_service_name);
        service_headers = view.findViewById(R.id.service_headers);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        tv_ticket_number = view.findViewById(R.id.tv_ticket_number);
        tv_ticket_status = view.findViewById(R.id.tv_ticket_status);
        /* tv_module_category = view.findViewById(R.id.tv_module_category);*/
        tv_guest_name = view.findViewById(R.id.tv_guest_name);
        tv_guest_room_no = view.findViewById(R.id.tv_guest_room_no);
        tikcet_details_recylcer = view.findViewById(R.id.tikcet_details_recylcer);
        ticket_status_adapter = view.findViewById(R.id.ticket_status_adapter);
        tv_price = view.findViewById(R.id.tv_price);
        tv_quantity = view.findViewById(R.id.tv_quantity);
        special_instruction_lyt = view.findViewById(R.id.special_instruction_lyt);
        tv_special_instruction = view.findViewById(R.id.tv_special_instruction);
        lyt_tkt_dtls= view.findViewById(R.id.lyt_tkt_dtls);
        lyt_tkt_dtls.setVisibility(View.GONE);
        //loading=view.findViewById(R.id.loading);
       // lyt_tkt_dtls.setVisibility(View.INVISIBLE);
        tv_quantity.setVisibility(View.GONE);
        tv_price.setVisibility(View.GONE);

        toolbar_title.setText("Order Details");

        special_instruction_lyt.setVisibility(View.GONE);
        Bundle data = getArguments();
        id = data.getString("id");
        type = data.getString("type");
       // layoutDecider(lyt);


        try {
            mSocket = IO.socket("https://exoneapi.refineryhotelnewyork.com:4000/orders");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        getTicketDetails(id);

        mSocket.connect();

        try {
            obj.put("guestUUID",GlobalClass.Guest_UUID);
            Log.d("obj", String.valueOf(obj));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("guestsJoinOrderRoom",obj);

        onNewMessage = args -> getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                JSONObject data = (JSONObject) args[0];

                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(String.valueOf(data));
                Gson gson = new Gson();
                TicketDetailsSocketPojo socketPojo = gson.fromJson(mJson, TicketDetailsSocketPojo.class);


                if (id.equalsIgnoreCase(socketPojo.getOrderInfo().getOrderUUID())){
                    orderItems.clear();
                    orderItems = socketPojo.getOrderInfo().getOrderActivities();
                    activityAdpater = new OrderActivityAdpater(context,orderItems);
                    mLayoutManager = new LinearLayoutManager(context);
                    ticket_status_adapter.setLayoutManager(mLayoutManager);
                    ticket_status_adapter.setNestedScrollingEnabled(false);
                    ticket_status_adapter.setItemAnimator(new DefaultItemAnimator());
                    ticket_status_adapter.setAdapter(activityAdpater);
                }
            }
        });

        mSocket.on("orderNotification", onNewMessage);
        mSocket.connect();



        /*btn_back.setOnClickListener(v->{
            try {
                Intent intent = new Intent(context, HomeScreenActivity.class);
                intent.putExtra("changes", "");
                context.startActivity(intent);
                getActivity().finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        });*/
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }

    private void getTicketDetails(String id) {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<TicketsDetailsPojo> ticketDetailsPojoCall = api.getticketDetailsAPI(headerMap,GlobalClass.Location_ID,GlobalClass.Booking_Number,id);
        APIResponse.getCallRetrofit(ticketDetailsPojoCall, "ticketDetails", context, this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        if (apiCallName.equalsIgnoreCase("ticketDetails")) {
            TicketsDetailsPojo ticketsDetailsPojo = (TicketsDetailsPojo) response.body();
            if (ticketsDetailsPojo.getStatusCode().equals(200) || ticketsDetailsPojo.getStatusCode().equals(201)){

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                lyt_tkt_dtls.setVisibility(View.VISIBLE);

                if (ticketsDetailsPojo.getData().getRequestType().equalsIgnoreCase("emergency")){
                    tv_ticket_number.setText(ticketsDetailsPojo.getData().getOrderNumber());
                    showPrice = ticketsDetailsPojo.getData().getShowPrice();
                    tikcet_details_recylcer.setVisibility(View.GONE);
                    service_headers.setVisibility(View.GONE);
                    tv_service_name.setVisibility(View.VISIBLE);
                    tv_service_name.setText(ticketsDetailsPojo.getData().getOrderItems().get(0).getItemName());
                    if (!ticketsDetailsPojo.getData().getSpecialInstructions().equalsIgnoreCase("") &&
                            !ticketsDetailsPojo.getData().getSpecialInstructions().equalsIgnoreCase(null)){
                        special_instruction_lyt.setVisibility(View.VISIBLE);
                        tv_special_instruction.setText(ticketsDetailsPojo.getData().getSpecialInstructions());
                    }
                    tv_guest_name.setText(GlobalClass.sharedPreferences.getString("fName", ""));
                    tv_guest_room_no.setText(String.valueOf(ticketsDetailsPojo.getData().getRoomNumber()));
                    activityAdpater = new OrderActivityAdpater(context,ticketsDetailsPojo.getData().getOrderActivities());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                    ticket_status_adapter.setLayoutManager(mLayoutManager);
                    ticket_status_adapter.setNestedScrollingEnabled(false);
                    ticket_status_adapter.setItemAnimator(new DefaultItemAnimator());
                    ticket_status_adapter.setAdapter(activityAdpater);
                }else{
                    showPrice = ticketsDetailsPojo.getData().getShowPrice();
                    if (showPrice){
                        tv_quantity.setVisibility(View.VISIBLE);
                    }else{
                        tv_quantity.setVisibility(View.GONE);
                    }

                    showQuantity = ticketsDetailsPojo.getData().getShowQuanity();

                    if (showQuantity){
                        tv_price.setVisibility(View.VISIBLE);
                    }else{
                        tv_price.setVisibility(View.GONE);
                    }

                    tv_ticket_number.setText(ticketsDetailsPojo.getData().getOrderNumber());
                    tv_service_name.setText(ticketsDetailsPojo.getData().getRequestType());
                    tikcet_details_recylcer.setVisibility(View.VISIBLE);
                    if (!ticketsDetailsPojo.getData().getSpecialInstructions().equalsIgnoreCase("") &&
                            !ticketsDetailsPojo.getData().getSpecialInstructions().equalsIgnoreCase(null)){
                        special_instruction_lyt.setVisibility(View.VISIBLE);
                        tv_special_instruction.setText(ticketsDetailsPojo.getData().getSpecialInstructions());
                    }
                    adapter = new TicketDetailsAdapter(context,ticketsDetailsPojo.getData().getOrderItems(),showPrice,showQuantity);
                    mLayoutManager = new LinearLayoutManager(context);
                    tikcet_details_recylcer.setLayoutManager(mLayoutManager);
                    tikcet_details_recylcer.setNestedScrollingEnabled(false);
                    tikcet_details_recylcer.setItemAnimator(new DefaultItemAnimator());
                    tikcet_details_recylcer.setAdapter(adapter);
                    tv_guest_name.setText(GlobalClass.sharedPreferences.getString("fName", ""));
                    tv_guest_room_no.setText(String.valueOf(ticketsDetailsPojo.getData().getRoomNumber()));

                    orderItems = ticketsDetailsPojo.getData().getOrderActivities();


                    activityAdpater = new OrderActivityAdpater(context,orderItems);
                    mLayoutManager = new LinearLayoutManager(context);
                    ticket_status_adapter.setLayoutManager(mLayoutManager);
                    ticket_status_adapter.setNestedScrollingEnabled(false);
                    ticket_status_adapter.setItemAnimator(new DefaultItemAnimator());
                    ticket_status_adapter.setAdapter(activityAdpater);
                }
            }
        }
    }

    @Override
    public void onErrorListner() {
    }

}