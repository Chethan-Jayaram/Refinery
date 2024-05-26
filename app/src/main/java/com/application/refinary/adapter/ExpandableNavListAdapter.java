package com.application.refinary.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.navigation.NavigationPojo;
import com.application.refinary.pojo.navigation.SideMenu;
import com.application.refinary.pojo.navigation.SubMenuItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.List;

public class ExpandableNavListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<SideMenu> sideMenuList;


    public ExpandableNavListAdapter(Context context, List<SideMenu> sideMenuList) {
        this.context = context;
        this.sideMenuList = sideMenuList;
    }

    @Override
    public int getGroupCount() {
        return sideMenuList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sideMenuList.get(groupPosition).getSubMenuItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return sideMenuList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return sideMenuList.get(groupPosition).getSubMenuItems().get(childPosition);
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
        try {
            String headerTitle = sideMenuList.get(groupPosition).getNavigationTitle();
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group_header, null);
            }
            ImageView iv_headericon=convertView.findViewById(R.id.iv_headericon);
            ImageView ig_view = convertView.findViewById(R.id.img_menu_services);
            ig_view.setVisibility(View.GONE);
            if (sideMenuList.get(groupPosition).getSubMenuItems().size()>0) {
                ig_view.setVisibility(View.VISIBLE);
                ig_view.setImageResource(R.drawable.ic_expand_more);
            }
            if (isExpanded) {
                ig_view.setImageResource(R.drawable.ic_expand_less);
            } else {
                ig_view.setImageResource(R.drawable.ic_expand_more);
            }
            Glide.with(convertView.getContext())
                    .load(sideMenuList.get(groupPosition).getNavigationSmallIcon())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_default).error(R.drawable.ic_default))
                    .into(iv_headericon);
            TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group_child, null);
            }
            TextView txtListChild = convertView
                    .findViewById(R.id.lblListItem);
            ImageView iv_icon=convertView.findViewById(R.id.iv_icon);
            txtListChild.setText(sideMenuList.get(groupPosition).getSubMenuItems().get(childPosition).getNavigationTitle());
            if (!GlobalClass.hasActiveBooking) {
                txtListChild.setEnabled(false);
            }
            Glide.with(convertView.getContext())
                    .load(sideMenuList.get(groupPosition).getSubMenuItems().get(childPosition).getNavigationSmallIcon())
                    .into(iv_icon);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
