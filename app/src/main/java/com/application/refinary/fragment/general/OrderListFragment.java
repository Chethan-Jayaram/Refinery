package com.application.refinary.fragment.general;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.refinary.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class OrderListFragment extends Fragment {

    private Context mContext;
    private ViewPager view_pager;
    private TextView toolbar_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_order_list, container, false);


        mContext=view.getContext();
        getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Order History");
        view_pager = view.findViewById(R.id.view_pager);
        TabLayout tab_lyt = view.findViewById(R.id.tab_lyt);
        tab_lyt.setupWithViewPager(view_pager);
        setupViewPager(view_pager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getChildFragmentManager());
        adapter.addFragment(new PendingTicketsList(), "PENDING ORDERS");
        adapter.addFragment(new ConfirmedTicektList(), "COMPLETED ORDERS");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            try {
                Bundle bundle = new Bundle();
                fragment.setArguments(bundle);
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}