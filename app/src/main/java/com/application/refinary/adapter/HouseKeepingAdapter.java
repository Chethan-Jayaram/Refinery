package com.application.refinary.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.housekeeping.Data;
import com.application.refinary.pojo.housekeeping.WithCategory;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.List;


public class HouseKeepingAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<WithCategory> data;
    private Boolean show_price;
    private Boolean place_order;
    private GlobalClass.houseKeepingAdapterListener mClickListener;


    public HouseKeepingAdapter(Context context, List<WithCategory> data, Boolean show_price, Boolean place_order, GlobalClass.houseKeepingAdapterListener mClickListener) {
        this.context = context;
        this.data = data;
        this.show_price = show_price;
        this.place_order = place_order;
        this.mClickListener = mClickListener;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getChildItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getChildItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.category_header_card, null);
        }

        TextView title = convertView.findViewById(R.id.tv_header_title);
        LinearLayout lyt_expand = convertView.findViewById(R.id.lyt_expand);

        title.setText(data.get(groupPosition).getCategoryName());

        lyt_expand.setOnClickListener(v->{
            if (isExpanded){
                ExpandableListView elv = (ExpandableListView)  parent;
                elv.collapseGroup(groupPosition);
            }else{
                ExpandableListView elv = (ExpandableListView)  parent;
                elv.expandGroup(groupPosition);
            }
        });

        /*ExpandableListView eLV = (ExpandableListView) parent;
        eLV.expandGroup(groupPosition);*/

        if (isExpanded){
            title.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24,0);
        }else {
            title.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_right,0);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.category_subcategory_card, null);
        }

                TextView tv_category_title  = convertView.findViewById(R.id.tv_category_title);
                TextView tv_category_desc = convertView.findViewById(R.id.tv_category_desc);
                TextView tv_category_price = convertView.findViewById(R.id.tv_category_price);
                RelativeLayout end_lyt = convertView.findViewById(R.id.end_lyt);
                CardView lyt_add = convertView.findViewById(R.id.lyt_add);
                CardView lyt_counter = convertView.findViewById(R.id.lyt_counter);
                View add = convertView.findViewById(R.id.add_lyt);
                View counter = convertView.findViewById(R.id.number_stepper);
                TextView tv_count = convertView.findViewById(R.id.tv_count1);
                ImageView img_mius = convertView.findViewById(R.id.img_mius);
                ImageView img_add = convertView.findViewById(R.id.img_add);


                tv_count.setText(data.get(groupPosition).getChildItems().get(childPosition).getCount().toString());

                data.get(groupPosition).getChildItems().get(childPosition).setItemPrice(data.get(groupPosition).getChildItems().get(childPosition).getItemPrice());

                end_lyt.setVisibility(View.GONE);

                if (data.get(groupPosition).getChildItems().get(childPosition).getItemDescription().equalsIgnoreCase("")){
                    tv_category_desc.setVisibility(View.GONE);
                }else{
                    tv_category_desc.setVisibility(View.VISIBLE);
                    tv_category_desc.setText(data.get(groupPosition).getChildItems().get(childPosition).getItemDescription());
                }

                if (place_order){
                    end_lyt.setVisibility(View.VISIBLE);
                }else{
                    end_lyt.setVisibility(View.GONE);
                }


                if (!show_price){
                tv_category_price.setVisibility(View.GONE);
                }else{
                tv_category_price.setVisibility(View.VISIBLE);
                tv_category_price.setText(data.get(groupPosition).getChildItems().get(childPosition).getPrice());

                }


                tv_category_title.setText(data.get(groupPosition).getChildItems().get(childPosition).getItemName());


                if (data.get(groupPosition).getChildItems().get(childPosition).getCount() != 0){
                    add.setVisibility(View.GONE);
                    counter.setVisibility(View.VISIBLE);
                }else{
                    add.setVisibility(View.VISIBLE);
                    counter.setVisibility(View.GONE);
                }

                add.setOnClickListener(v -> {
                    add.setVisibility(View.GONE);
                    counter.setVisibility(View.VISIBLE);
                    data.get(groupPosition).getChildItems().get(childPosition).setCount(data.get(groupPosition).getChildItems().get(childPosition).getCount() + 1);
                    tv_count.setText(data.get(groupPosition).getChildItems().get(childPosition).getCount().toString());
                    mClickListener.onItemClicked(data.get(groupPosition));
                });


                img_add.setOnClickListener(v -> {
                    if (data.get(groupPosition).getChildItems().get(childPosition).getCount() < data.get(groupPosition).getChildItems().get(childPosition).getMaxQuantity()){
                        data.get(groupPosition).getChildItems().get(childPosition).setCount(data.get(groupPosition).getChildItems().get(childPosition).getCount() + 1);
                        tv_count.setText(data.get(groupPosition).getChildItems().get(childPosition).getCount().toString());
                        mClickListener.onItemClicked(data.get(groupPosition));
                    }
                });

                img_mius.setOnClickListener(v -> {
                    if (data.get(groupPosition).getChildItems().get(childPosition).getCount() > 0){
                        data.get(groupPosition).getChildItems().get(childPosition).setCount(data.get(groupPosition).getChildItems().get(childPosition).getCount() - 1);
                        tv_count.setText(data.get(groupPosition).getChildItems().get(childPosition).getCount().toString());
                        mClickListener.onItemClicked(data.get(groupPosition));
                    }
                    if(data.get(groupPosition).getChildItems().get(childPosition).getCount() == 0){
                        add.setVisibility(View.VISIBLE);
                        counter.setVisibility(View.GONE);
                    }
                });



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
