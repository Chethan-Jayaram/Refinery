package com.application.refinary.fragment.modules.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.weather.Weather;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Date;


public class Tomorrow extends Fragment {

    private Weather mWeather;
    private TextView tv_name, tv_region_country, tv_localtime, tv_condition, tv_max_f, tv_min_f, tv_temp_f,
            tv_humidity, tv_precipitation, tv_pressure, tv_visiblity,tv_pres_win;
    private ImageView condition_icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tomorrow, container, false);


        try {
            tv_name = view.findViewById(R.id.tv_name);
            tv_region_country = view.findViewById(R.id.tv_region_country);
            tv_localtime = view.findViewById(R.id.tv_localtime);
            tv_condition = view.findViewById(R.id.tv_condition);
            tv_max_f = view.findViewById(R.id.tv_max_f);
            tv_min_f = view.findViewById(R.id.tv_min_f);
            tv_temp_f = view.findViewById(R.id.tv_temp_f);
            tv_humidity = view.findViewById(R.id.tv_humidity);
            tv_precipitation = view.findViewById(R.id.tv_precipitation);
//            tv_pressure = view.findViewById(R.id.tv_pressure);
            tv_visiblity = view.findViewById(R.id.tv_visiblity);
            condition_icon= view.findViewById(R.id.condition_icon);
     //       tv_pres_win= view.findViewById(R.id.tv_pres_win);

            Bundle data = getArguments();
            if (data != null) {
                mWeather = data.getParcelable("weather_data");
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);
            Date date = calendar.getTime();


            String tomorrowDate = GlobalClass.weainputdateformat.format(date);
            Log.d("date", tomorrowDate);


        //    tv_name.setText((mWeather.getData().getMeta().getLocation()));
            tv_name.setText("New York City");
            tv_region_country.setText((mWeather.getData().getMeta().getLocation()));


            for (int i = 0; i<= mWeather.getData().getWeathersData().size(); i++){
                if (mWeather.getData().getWeathersData().get(i).getDateType().equalsIgnoreCase("tomorrow")){
                    tv_max_f.setText(mWeather.getData().getWeathersData().get(i).getDayData().getMaxtempF()+"\u00B0");
                    tv_min_f.setText(mWeather.getData().getWeathersData().get(i).getDayData().getMintempF()+"\u00B0");
                    tv_condition.setText(mWeather.getData().getWeathersData().get(i).getDayData().getCondition().getText());
                    tv_humidity.setText(mWeather.getData().getWeathersData().get(i).getDayData().getAvghumidity()+"%");
                    tv_precipitation.setText(mWeather.getData().getWeathersData().get(i).getDayData().getTotalprecipMm()+" mm");
                    tv_visiblity.setText(mWeather.getData().getWeathersData().get(i).getDayData().getAvgvisMiles()+" m");
                    tv_localtime.setText(mWeather.getData().getWeathersData().get(i).getWeatherDate());
                    Glide.with(this)
                            .load("https:"+mWeather.getData().getWeathersData().get(i).getDayData().getCondition().getIcon())
                            .into(condition_icon);
                    tv_temp_f.setText(mWeather.getData().getWeathersData().get(i).getDayData().getAvgtempF()+"\u00B0");
                 //   tv_temp_f.setText("22.3"+ "\u00B0");
                }
            }


            /*for(int i=0;i<mWeather.getResult().getForecast().getForecastday().size();i++){
                if(tomorrowDate.equalsIgnoreCase(mWeather.getResult().getForecast().getForecastday().get(i).getDate())){
                    tv_max_f.setText(mWeather.getResult().getForecast().getForecastday().get(i).getDay().getMaxtempF()+"\u00B0");
                    tv_min_f.setText(mWeather.getResult().getForecast().getForecastday().get(i).getDay().getMintempF()+"\u00B0");
                    tv_condition.setText(mWeather.getResult().getForecast().getForecastday().get(i).getDay().getCondition().getText());
                    tv_pres_win.setText("Wind");
                    tv_humidity.setText(mWeather.getResult().getForecast().getForecastday().get(i).getDay().getAvghumidity()+"%");
                    tv_precipitation.setText(mWeather.getResult().getForecast().getForecastday().get(i).getDay().getTotalprecipMm()+" mm");
                    tv_pressure.setText(mWeather.getResult().getForecast().getForecastday().get(i).getDay().getMaxwindKph()+" Kph");
                    tv_visiblity.setText(mWeather.getResult().getForecast().getForecastday().get(i).getDay().getAvgvisKm()+" km");
                    tv_localtime.setText(GlobalClass.tomorrowDayformat.format(GlobalClass.weainputdateformat.parse(mWeather.getResult().getForecast().getForecastday().get(i).getDate())));
                    Glide.with(this)
                            .load("https:"+mWeather.getResult().getForecast().getForecastday().get(i).getDay().getCondition().getIcon())
                            .into(condition_icon);
                    Log.d("set_sucess","tommorrow");
                }
            }*/
       //     tv_temp_f.setText(mWeather.getResult().getCurrent().getTempF()+"\u00B0");



           /* //compare today's weather condition with weather type and disply custom message
            List<WeatherType> weatherType=mWeather.getResult().getWeatherType();

            if (!weatherType.isEmpty()) {
                for(int i=0;i<weatherType.size();i++){
                    if(mWeather.getResult().getCurrent().getCondition().getText()
                            .equalsIgnoreCase(weatherType.get(i).getWeatherType())){
                        tv_custom_msg.setText(weatherType.get(i).getCustomMessage());
                    }
                }

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }



        return view;
    }
}