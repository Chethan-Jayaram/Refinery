package com.application.refinary.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.pojo.housekeeping.ChildItem;
import com.application.refinary.pojo.housekeeping.WithCategory;
import com.application.refinary.pojo.laundry.Item;

import org.w3c.dom.Text;

import java.util.List;

public class ServiceModuleSegmentAdapter extends BaseExpandableListAdapter {

    private List<WithCategory> details;
    private Context context;


    public ServiceModuleSegmentAdapter(List<WithCategory> details, Context context) {
        this.details = details;
        this.context = context;
    }



    @Override
    public int getGroupCount() {
        return details.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return details.get(groupPosition).getChildItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return details.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return details.get(groupPosition).getChildItems().get(childPosition);
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
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_header, null);
        }

        TextView title = convertView.findViewById(R.id.tv_category_title);

        title.setText(details.get(groupPosition).getCategoryName());


        ExpandableListView eLV = (ExpandableListView) parent;
        eLV.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.housekeeping_segment_content, null);
        }

        TextView sub_title = convertView.findViewById(R.id.tv_category_subtitle);
        TextView count = convertView.findViewById(R.id.tv_category_count);
        LinearLayout lyt_sub_cat = convertView.findViewById(R.id.lyt_sub_cat);

        lyt_sub_cat.setVisibility(View.GONE);

        if (details.get(groupPosition).getChildItems().get(childPosition).getCount() > 0){
            lyt_sub_cat.setVisibility(View.VISIBLE);
            sub_title.setText(details.get(groupPosition).getChildItems().get(childPosition).getItemName());
            count.setText("x"+String.valueOf(details.get(groupPosition).getChildItems().get(childPosition).getCount()));
        }else {
            lyt_sub_cat.setVisibility(View.GONE);
        }



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
