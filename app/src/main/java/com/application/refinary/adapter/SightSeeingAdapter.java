package com.application.refinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.sightseeing.Data;
import com.application.refinary.pojo.sightseeing.Image;
import com.application.refinary.pojo.sightseeing.Place;
import com.bumptech.glide.Glide;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class SightSeeingAdapter extends RecyclerView.Adapter<SightSeeingAdapter.MyViewHolder> {

    private Context mContext;
    private List<Place> data;
    private GlobalClass.AdapterClickListner onclicklistner;

    public SightSeeingAdapter(Context mContext, List<Place> data, GlobalClass.AdapterClickListner onclicklistner) {
        this.data = data;
        this.mContext = mContext;
        this.onclicklistner = onclicklistner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tour_guide_recycler_content, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            holder.tour_title.setText(data.get(position).getPlaceName());
            holder.tour_loc.setText(data.get(position).getPlaceLocation());
            holder.tour_desc.setText(data.get(position).getPlaceDescription());
            if(data.get(position).getDistanceFromHotel()!=null){
                holder.tour_distance.setText(data.get(position).getDistanceFromHotel());
            }else{
                holder.tour_distance.setText(0 + " kms from hotel");
            }
            /*holder.tour_loc.setOnClickListener(v -> {
                clickListner.onItemClickListener(position);
            });
            holder.tour_distance.setOnClickListener(v -> {
                clickListner.onItemClickListener(position);
            });*/
            SlidingImage_Adapter adapter=new SlidingImage_Adapter(mContext,data.get(position).getImages());
            holder.tour_indicator.setViewPager(holder.tour_pager);
            holder.tour_pager.setAdapter(adapter);
            holder.tour_indicator.setViewPager(holder.tour_pager);
            holder.tour_timings.setText(data.get(position).getPlaceTimings());
            holder.tour_distance.setOnClickListener(v -> {
                onclicklistner.onItemClickListener(position);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ViewPager2 tour_pager;
        private CircleIndicator3 tour_indicator;
        private TextView tour_title, tour_loc, tour_timings, tour_desc, tour_distance, tour_button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tour_title = itemView.findViewById(R.id.tour_title);
            tour_loc = itemView.findViewById(R.id.tour_loc);
            tour_timings = itemView.findViewById(R.id.tour_timings);
            tour_desc = itemView.findViewById(R.id.tour_desc);
            tour_distance = itemView.findViewById(R.id.tour_distance);
            //tour_button = view.findViewById(R.id.tour_button);
            tour_pager = itemView.findViewById(R.id.tour_pager);
            tour_indicator = itemView.findViewById(R.id.tour_indicator);
        }
    }


    class SlidingImage_Adapter extends RecyclerView.Adapter<SlidingImage_Adapter.ViewHolder> {

        private List<Image> images;
        private Context context;


        SlidingImage_Adapter(Context context, List<Image> images) {
            this.context = context;
            this.images=images;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View imageLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.tour_sliding_images, parent, false);
            return new ViewHolder(imageLayout);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Glide.with(context).load(images.get(position).getPlaceImageFilePath()).into(holder.img_tour);
        }

        @Override
        public int getItemCount() {
            return images.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView img_tour;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                img_tour =  itemView.findViewById(R.id.img_tour);
            }
        }
    }

}
