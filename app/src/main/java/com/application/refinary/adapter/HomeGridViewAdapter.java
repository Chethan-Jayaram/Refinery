package com.application.refinary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.navigation.MainMenu;
import com.application.refinary.pojo.navigation.NavigationPojo;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.List;

public class HomeGridViewAdapter extends BaseAdapter {
    private List<MainMenu> item;
    private Context mContext;
    private GridAdapterlistner gridAdapterlistner;
    private LayoutInflater inflater;
    private List home_menu;

    public HomeGridViewAdapter(Context mContext, List<MainMenu> item, GridAdapterlistner gridAdapterlistner) {
        this.mContext=mContext;
        this.item=item;
        this.gridAdapterlistner=gridAdapterlistner;
        inflater = (LayoutInflater.from(mContext));

    }

    public interface GridAdapterlistner { // create an interface
        void onItemClickListener(int position); // create callback function
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub\
        // TODO Auto-generated method stub
        View grid = null;
        try {
            if (convertView == null) {
                grid = inflater.inflate(R.layout.home_row, null);
                TextView textView = grid.findViewById(R.id.grid_text);
                ImageView imageView = grid.findViewById(R.id.grid_image);
                RelativeLayout layout = grid.findViewById(R.id.lyt_content);


               /* Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                        .setBaseColor(Color.parseColor("#F3F3F3"))
                        .setBaseAlpha(1)
                        .setHighlightColor(Color.parseColor("#E7E7E7"))
                        .setHighlightAlpha(1)
                        .setDropoff(50)
                        .build();
                ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
                shimmerDrawable.setShimmer(shimmer);*/

                Glide.with(mContext).load(item.get(position).getNavigationLargeIcon()).into(imageView);

                /*for (int i = 0; i<=item.size();i++){
                    if (item.get(i).getShowInDashboard()){
                        home_menu.add(item.get(i));
                        textView.setText(item.get(i).getNavigationTitle());
                        Glide.with(mContext).load(item.get(i).getNavigationSmallIcon()).into(imageView);
                    }else if (item.get(i).getSubMenuItems().size()>0){
                        for (int j = 0; j<=item.get(i).getSubMenuItems().size(); j++){
                            if (item.get(i).getSubMenuItems().get(j).getShowInDashboard()){
                                textView.setText(item.get(i).getSubMenuItems().get(j).getNavigationTitle());
                                Glide.with(mContext).load(item.get(i).getSubMenuItems().get(j).getNavigationSmallIcon()).into(imageView);
                                home_menu.add(item.get(i).getSubMenuItems().get(j));
                            }
                        }
                    }
                }*/

              /*  if(item.get(position).getShowInDashboard()){
                    layout.setVisibility(View.VISIBLE);
                    textView.setText(item.get(position).getNavigationTitle());
                    Glide.with(mContext).load(item.get(position).getNavigationSmallIcon()).into(imageView);
                }else{
                    layout.setVisibility(View.GONE);
                }*/


                textView.setText(item.get(position).getNavigationTitle());
            //    Glide.with(mContext).load(item.get(position).getNavigationLargeIcon()).into(imageView);


                layout.setOnClickListener(v->{
                    gridAdapterlistner.onItemClickListener(position);
                });


                if(!GlobalClass.hasActiveBooking){
                    textView.setEnabled(false);
                    imageView.setEnabled(false);
                }
            } else {
                grid = (View) convertView;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return grid;
    }
}
