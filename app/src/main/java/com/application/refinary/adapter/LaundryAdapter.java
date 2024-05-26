package com.application.refinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.laundry.Item;

import java.util.List;

public class LaundryAdapter extends BaseExpandableListAdapter {

    private Context context;
    private  List<Item> items;
    private Boolean show_price,place_order;
    private GlobalClass.laundryAdapterListener mClickListener;

    public LaundryAdapter(Context context, List<Item> items, Boolean show_price, Boolean place_order, GlobalClass.laundryAdapterListener mClickListener) {
        this.context = context;
        this.items = items;
        this.place_order = place_order;
        this.show_price = show_price;
        this.mClickListener = mClickListener;
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return items.get(groupPosition).getItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).getItems().get(childPosition);
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
            convertView = layoutInflater.inflate(R.layout.laundry_category_header_card, null);

        }

        LinearLayout lyt_expand = convertView.findViewById(R.id.lyt_expand);

        TextView title = convertView.findViewById(R.id.tv_header_title);


        lyt_expand.setOnClickListener(v->{
            if (isExpanded){
                ExpandableListView elv = (ExpandableListView)  parent;
                elv.collapseGroup(groupPosition);
            }else{
                ExpandableListView elv = (ExpandableListView)  parent;
                elv.expandGroup(groupPosition);
            }
        });

        if (isExpanded){
            title.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24,0);
        }else {
            title.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_right,0);
        }


        title.setText(items.get(groupPosition).getCategoryName());

        /*ExpandableListView eLV = (ExpandableListView) parent;
        eLV.expandGroup(groupPosition);*/

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
        View add = convertView.findViewById(R.id.add_lyt);
        View counter = convertView.findViewById(R.id.number_stepper);
        TextView tv_count = convertView.findViewById(R.id.tv_count1);
        ImageView img_mius = convertView.findViewById(R.id.img_mius);
        ImageView img_add = convertView.findViewById(R.id.img_add);


        items.get(groupPosition).getItems().get(childPosition).setItemUUID(items.get(groupPosition).getItems().get(childPosition).getLaundryItemUUID());

        end_lyt.setVisibility(View.GONE);
        tv_count.setText(items.get(groupPosition).getItems().get(childPosition).getCount().toString());

        if (items.get(groupPosition).getItems().get(childPosition).getItemDescription() == null){
            tv_category_desc.setVisibility(View.GONE);
        }else{
            tv_category_desc.setVisibility(View.VISIBLE);
            tv_category_desc.setText(items.get(groupPosition).getItems().get(childPosition).getItemDescription().toString());
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
            tv_category_price.setText("$"+items.get(groupPosition).getItems().get(childPosition).getItemPrice());
        }


        tv_category_title.setText(items.get(groupPosition).getItems().get(childPosition).getItemName());

        if (items.get(groupPosition).getItems().get(childPosition).getCount() != 0){
            add.setVisibility(View.GONE);
            counter.setVisibility(View.VISIBLE);
        }else{
            add.setVisibility(View.VISIBLE);
            counter.setVisibility(View.GONE);
        }

        add.setOnClickListener(v -> {
            add.setVisibility(View.GONE);
            counter.setVisibility(View.VISIBLE);
            items.get(groupPosition).getItems().get(childPosition).setCount(items.get(groupPosition).getItems().get(childPosition).getCount() + 1);
            tv_count.setText(items.get(groupPosition).getItems().get(childPosition).getCount().toString());
            mClickListener.onItemClicked(items.get(groupPosition));
        });


        img_add.setOnClickListener(v -> {
            if (items.get(groupPosition).getItems().get(childPosition).getCount() < items.get(groupPosition).getItems().get(childPosition).getMaxOrderQuantity()){
                items.get(groupPosition).getItems().get(childPosition).setCount(items.get(groupPosition).getItems().get(childPosition).getCount() + 1);
                tv_count.setText(items.get(groupPosition).getItems().get(childPosition).getCount().toString());
                mClickListener.onItemClicked(items.get(groupPosition));
            }
        });

        img_mius.setOnClickListener(v -> {
            if (items.get(groupPosition).getItems().get(childPosition).getCount() > 0){
                items.get(groupPosition).getItems().get(childPosition).setCount(items.get(groupPosition).getItems().get(childPosition).getCount() - 1);
                tv_count.setText(items.get(groupPosition).getItems().get(childPosition).getCount().toString());
                mClickListener.onItemClicked(items.get(groupPosition));
            }
            if(items.get(groupPosition).getItems().get(childPosition).getCount() == 0){
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
