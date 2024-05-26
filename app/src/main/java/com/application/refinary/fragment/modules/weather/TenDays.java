package com.application.refinary.fragment.modules.weather;

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
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.WeatherTenDayAdapter;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.weather.Weather;
import com.application.refinary.pojo.weather.WeathersDatum;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TenDays extends Fragment {


    private RecyclerView ten_days_recycler;
    private Context mCtx;

    private  List<WeathersDatum> futureWeather = new ArrayList<WeathersDatum>();
    private Weather mWeather;
    private WeatherTenDayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ten_days, container, false);

        try {

            mCtx=view.getContext();
            ten_days_recycler=view.findViewById(R.id.ten_days_recycler);


            Bundle data = getArguments();
            if (data != null) {
                mWeather = data.getParcelable("weather_data");
            }
            futureWeather.clear();

            for (int i = 0; i<mWeather.getData().getWeathersData().size(); i++){
                if (mWeather.getData().getWeathersData().get(i).getDateType().equalsIgnoreCase("future")) {
                    futureWeather.add(mWeather.getData().getWeathersData().get(i));
                }
            }

            adapter=new WeatherTenDayAdapter(futureWeather,mCtx);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
            ten_days_recycler.setLayoutManager(mLayoutManager);
            ten_days_recycler.setItemAnimator(new DefaultItemAnimator());
            ten_days_recycler.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}