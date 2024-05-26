package com.application.refinary.fragment.modules.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.weather.Weather;
import com.bumptech.glide.Glide;

import java.util.List;

public class Today extends Fragment {

    private Weather mWeather;

    private TextView tv_name, tv_region_country, tv_localtime, tv_condition, tv_max_f, tv_min_f, tv_temp_f,
            tv_humidity, tv_precipitation, tv_pressure, tv_visiblity, tv_custom_msg;
    private ImageView condition_icon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);

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
         //   tv_pressure = view.findViewById(R.id.tv_pressure);
            tv_visiblity = view.findViewById(R.id.tv_visiblity);
            condition_icon = view.findViewById(R.id.condition_icon);
            tv_custom_msg = view.findViewById(R.id.tv_custom_msg);

            Bundle data = getArguments();
            if (data != null) {
                mWeather = data.getParcelable("weather_data");
            }


          //  tv_name.setText((mWeather.getData().getMeta().getLocation()));
            tv_name.setText("New York City");
            tv_region_country.setText((mWeather.getData().getMeta().getLocation()));


            for (int i = 0; i< mWeather.getData().getWeathersData().size(); i++ ){

                if (mWeather.getData().getWeathersData().get(i).getDateType().equalsIgnoreCase("today")){
                    tv_condition.setText(mWeather.getData().getWeathersData().get(i).getDayData().getCondition().getText());
                    tv_max_f.setText(mWeather.getData().getWeathersData().get(i).getDayData().getMaxtempF() + "\u00B0");
                    tv_min_f.setText(mWeather.getData().getWeathersData().get(i).getDayData().getMintempF() + "\u00B0");
                    tv_localtime.setText(mWeather.getData().getWeathersData().get(i).getWeatherDate());
                    tv_temp_f.setText(mWeather.getData().getWeathersData().get(i).getDayData().getAvgtempF() + "\u00B0");
                   // tv_temp_f.setText("25.3"+ "\u00B0");
                    tv_humidity.setText(mWeather.getData().getWeathersData().get(i).getDayData().getAvghumidity() + "%");
                    tv_precipitation.setText(mWeather.getData().getWeathersData().get(i).getDayData().getTotalprecipMm() + " mm");
                    tv_visiblity.setText(mWeather.getData().getWeathersData().get(i).getDayData().getAvgvisMiles() + " m");

                    Glide.with(this)
                            .load("https:" + mWeather.getData().getWeathersData().get(i).getDayData().getCondition().getIcon())
                            .into(condition_icon);
                }

            }




        //    String currentDate = GlobalClass.weainputdateformat.format(GlobalClass.weatherinputdateformat.parse(mWeather.getData().getMeta().getCurrentTime()));





           // tv_precipitation.setText(mWeather.getResult().getCurrent().getPrecipMm() + " mm");
           //  tv_pressure.setText(mWeather.getResult().getCurrent().getPressureMb() + " mBar");
            // tv_visiblity.setText(mWeather.getResult().getCurrent().getVisKm() + " km");




            //compare today's weather condition with weather type and disply custom message
            /*List<WeatherType> weatherType=mWeather.getResult().getWeatherType();

            if (!weatherType.isEmpty()) {
                for(int i=0;i<weatherType.size();i++){
                    if(mWeather.getResult().getCurrent().getCondition().getText()
                            .equalsIgnoreCase(weatherType.get(i).getWeatherType())) {
                        tv_custom_msg.setVisibility(View.VISIBLE);
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