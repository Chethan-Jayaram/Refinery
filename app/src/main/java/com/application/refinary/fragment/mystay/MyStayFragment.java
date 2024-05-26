package com.application.refinary.fragment.mystay;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.mystay.Current;
import com.application.refinary.pojo.mystay.MyStayPojo;
import com.application.refinary.pojo.mystay.Past;
import com.application.refinary.pojo.mystay.Upcoming;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class MyStayFragment extends Fragment implements ApiListener {
    private Context mContext;
    private ViewPager view_pager;
    private ArrayList<Current> activeBookings;
    private ArrayList<Past> bookingHistories;
    private ArrayList<Upcoming> upcomingBookings;
    private ProgressBar loading;
    private  APIMethods api;
    private ShimmerFrameLayout shimmerFrameLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_stay, container, false);

        mContext=view.getContext();
        getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        view_pager = view.findViewById(R.id.view_pager);
        TabLayout tab_lyt = view.findViewById(R.id.tab_lyt);
        tab_lyt.setupWithViewPager(view_pager);
        loading=view.findViewById(R.id.loading);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        view_pager.setVisibility(View.GONE);
        Bundle args=getArguments();
        if(args!=null){
            toolbar_title.setText(args.getString("tool_bar_header"));
        }
        getBooking();

        return view;
    }


    private void getBooking() {
     //   loading.setVisibility(View.VISIBLE);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<MyStayPojo> hotelDirectory = api.myStayAPI(headerMap);
        APIResponse.getCallRetrofit(hotelDirectory, "bookings",mContext , this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        try {
            if (apiCallName.equalsIgnoreCase("bookings")) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                view_pager.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                MyStayPojo myStayPojo = (MyStayPojo) response.body();
                if (myStayPojo.getStatusCode().equals(200)) {
                    activeBookings = (ArrayList<Current>) myStayPojo.getData().getCurrent();
                    bookingHistories = (ArrayList<Past>) myStayPojo.getData().getPast();
                    upcomingBookings = (ArrayList<Upcoming>) myStayPojo.getData().getUpcoming();
                    setupViewPager(view_pager);
                } else {
                    GlobalClass.showErrorMsg(mContext, myStayPojo.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getChildFragmentManager());
        adapter.addFragment(new UpComingBookings(), "UPCOMING", upcomingBookings);
        adapter.addFragment(new CurrentBooking(), "CURRENT STAY",  activeBookings);
        adapter.addFragment(new CompletedBookings(), "COMPLETED ",  bookingHistories);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title,ArrayList<?> bookings) {
            try {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) bookings);
                fragment.setArguments(bundle);
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onErrorListner() {

    }
}