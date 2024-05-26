package com.application.refinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.weather.Weather;
import com.application.refinary.pojo.weather.WeathersDatum;
import com.bumptech.glide.Glide;

import java.util.List;

public class WeatherTenDayAdapter extends RecyclerView.Adapter<WeatherTenDayAdapter.ViewHolder>  {
    private List<WeathersDatum> futureWeather;
    private Context mCtx;

    public WeatherTenDayAdapter(List<WeathersDatum> futureWeather, Context mCtx) {
        this.futureWeather = futureWeather;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public WeatherTenDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_recycler_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherTenDayAdapter.ViewHolder holder, int position) {
        try {

            holder.temp_max_f.setText(futureWeather.get(position).getDayData().getMaxtempF()+"\u00B0");
            holder.temp_min_f.setText(futureWeather.get(position).getDayData().getMintempF()+"\u00B0");
            holder.prediction.setText(futureWeather.get(position).getDayData().getCondition().getText());
            Glide.with(mCtx).load("https:"+futureWeather.get(position).getDayData().getCondition().getIcon()).into(holder.iv_condition);
            holder.tv_date.setText(futureWeather.get(position).getWeatherDate());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return futureWeather.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date, temp_min_f,prediction,temp_max_f;
        private ImageView iv_condition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            temp_min_f = itemView.findViewById(R.id.temp_min_f);
            iv_condition= itemView.findViewById(R.id.iv_condition);
            prediction= itemView.findViewById(R.id.prediction);
            temp_max_f= itemView.findViewById(R.id.temp_max_f);
        }
    }
}
