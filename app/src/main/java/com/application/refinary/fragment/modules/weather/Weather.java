package com.application.refinary.fragment.modules.weather;

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


public class Weather extends Fragment implements ApiListener {

    private Context mContext;
    private ViewPager view_pager;

    private com.application.refinary.pojo.weather.Weather mWeatherPojo;

    private ProgressBar loading;

    private ShimmerFrameLayout shimmerFrameLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        mContext=view.getContext();
        getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
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

        getWeather();

        return view;
    }

    private void getWeather() {
       // loading.setVisibility(View.VISIBLE);
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<com.application.refinary.pojo.weather.Weather> weatherCall = api.WeatherAPI(headerMap);
        APIResponse.getCallRetrofit(weatherCall, "weather",mContext , this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        try {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            view_pager.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            mWeatherPojo = (com.application.refinary.pojo.weather.Weather) response.body();
            if (apiCallName.equalsIgnoreCase("weather")) {
                if (mWeatherPojo.getStatusCode().equals(200)) {
                    setupViewPager(view_pager);
                } else {
                    GlobalClass.showErrorMsg(mContext, mWeatherPojo.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setupViewPager(ViewPager view_pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getChildFragmentManager());
        adapter.addFragment(new Today(), "Today");
        adapter.addFragment(new Tomorrow(), "Tomorrow");
      //  adapter.addFragment(new TenDays(), "3 Days");
        view_pager.setAdapter(adapter);
    }

    @Override
    public void onErrorListner() {

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

        void addFragment(Fragment fragment, String title) {
            try {
                Bundle bundle = new Bundle();
                bundle.putParcelable("weather_data", (Parcelable) mWeatherPojo);
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
}