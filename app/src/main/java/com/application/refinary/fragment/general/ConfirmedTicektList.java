package com.application.refinary.fragment.general;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.TicketListAdapter;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.helper.PaginationScrollListener;
import com.application.refinary.pojo.alltickets.AllTicketDetailsPojo;
import com.application.refinary.pojo.alltickets.Order;
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


public class ConfirmedTicektList extends Fragment implements ApiListener {

    private RecyclerView ticket_recyler_list;
    private Context context;
    private TicketListAdapter adapter;
    int page = 1, limit = 10;
    private ProgressBar loading;
    private List<Order> orderList = new ArrayList<>();
    private List<Order> newOrderList = new ArrayList<>();
    private Boolean isScrolling = false, hasNextPage = false, isLastPage = false,isLoading = false;;
    private Integer currentItem,scrolledOutItem,totalItem,totalPages = 0;
    LinearLayoutManager mLayoutManager;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmed_ticekt_list, container, false);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        ticket_recyler_list = view.findViewById(R.id.ticket_recyler_list);
        context = view.getContext();
        getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        loading = view.findViewById(R.id.loading);
        mLayoutManager = new LinearLayoutManager(context);
        Bundle bundle=getArguments();
        if(bundle!=null){
            toolbar_title.setText(bundle.getString("tool_bar_header"));
        }

        if (GlobalClass.hasActiveBooking) {
            getTicketList();
        }else {
            GlobalClass.ShowAlet(context,"Alert","You dont have active bokking");
            Fragment fragment = new HomeGridFragment();
            getActivity().getSupportFragmentManager().popBackStack();
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
        }

        adapter = new TicketListAdapter(context,position ->{
            Bundle bundle1 = new Bundle();
            Fragment fragment = new TicketDetails();
            bundle1.putString("id", String.valueOf(newOrderList.get(position).getOrderUUID()));
            // bundle.putString("lyt", ticketResponse.getResult().getLayout());
            bundle1.putString("type", "Support");
            fragment.setArguments(bundle1);
            //getActivity().getSupportFragmentManager().popBackStack();
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_container, fragment).addToBackStack(null).commit();

        });
        ticket_recyler_list.setLayoutManager(mLayoutManager);
        ticket_recyler_list.setNestedScrollingEnabled(false);
        ticket_recyler_list.setItemAnimator(new DefaultItemAnimator());
        ticket_recyler_list.setAdapter(adapter);

        /*ticket_recyler_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = mLayoutManager.getChildCount();
                scrolledOutItem = mLayoutManager.getItemCount();
                totalItem = mLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItem+scrolledOutItem == totalItem)){
                    isScrolling = false;
                    page += page;
                    getTicketList(page,limit);
                }

            }
        });*/

        ticket_recyler_list.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                page += 1;
                // mocking network delay for API call
                new Handler().postDelayed(() -> {
                    if(!orderList.isEmpty()){
                        getTicketList(page,limit);
                        loading.setVisibility(View.VISIBLE);
                    }
                }, 1000);
            }

            @Override
            public Integer getTotalPageCount() {
                return totalPages;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        return view;
    }

    private void getTicketList() {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<AllTicketDetailsPojo> ticketsList = api.getallticketDetailsAPI(headerMap,GlobalClass.Location_ID,GlobalClass.Booking_Number,page,limit,"DESC","delivered");
        APIResponse.getCallRetrofit(ticketsList, "ticketsList",context , this);
    }

    private void getTicketList(int page, int limit) {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<AllTicketDetailsPojo> ticketsList = api.getallticketDetailsAPI(headerMap,GlobalClass.Location_ID,GlobalClass.Booking_Number,page,limit,"DESC","delivered");
        APIResponse.getCallRetrofit(ticketsList, "nextPage",context , this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        AllTicketDetailsPojo allTicketDetailsPojo = (AllTicketDetailsPojo) response.body();
        if (apiCallName.equalsIgnoreCase("ticketsList")) {
            if (allTicketDetailsPojo.getStatusCode().equals(200)){
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

                orderList = allTicketDetailsPojo.getData().getOrders();
                newOrderList = allTicketDetailsPojo.getData().getOrders();
                totalPages = allTicketDetailsPojo.getData().getMeta().getPageCount();
                adapter.addAll(orderList);
                isLastPage = !allTicketDetailsPojo.getData().getMeta().getHasNextPage();
                //JsonArray jsonArray = new JsonArray(allTicketDetailsPojo.getData().getOrders());

               /* orderList = allTicketDetailsPojo.getData().getOrders();
                adapter = new TicketListAdapter(context,allTicketDetailsPojo.getData().getOrders());
                ticket_recyler_list.setLayoutManager(mLayoutManager);
                ticket_recyler_list.setNestedScrollingEnabled(false);
                ticket_recyler_list.setItemAnimator(new DefaultItemAnimator());
                ticket_recyler_list.setAdapter(adapter);*/
            }else{
                GlobalClass.ShowAlet(context, "Alert", allTicketDetailsPojo.getMessage());
            }
        }else if (apiCallName.equalsIgnoreCase("nextPage")){
            if (allTicketDetailsPojo.getStatusCode().equals(200)) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

                loading.setVisibility(View.GONE);
                isLoading = false;
                isLastPage = !allTicketDetailsPojo.getData().getMeta().getHasNextPage();
                orderList = allTicketDetailsPojo.getData().getOrders();
                newOrderList.addAll(allTicketDetailsPojo.getData().getOrders());
                adapter.addAll(orderList);
            }else{
                GlobalClass.ShowAlet(context, "Alert", allTicketDetailsPojo.getMessage());
            }
        }

    }

    @Override
    public void onErrorListner() {

    }
}