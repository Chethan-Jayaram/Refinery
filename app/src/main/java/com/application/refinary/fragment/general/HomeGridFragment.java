package com.application.refinary.fragment.general;

import static com.application.refinary.helper.GlobalClass.greetingBasedOntime;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.activity.HomeScreenActivity;
import com.application.refinary.activity.UserAuthenticationActivity;
import com.application.refinary.adapter.ExpandableNavListAdapter;
import com.application.refinary.adapter.HomeGridViewAdapter;
import com.application.refinary.fragment.modules.HomeScreenBanner;
import com.application.refinary.fragment.modules.HotelInformationFragment;
import com.application.refinary.fragment.modules.StoreFragment;
import com.application.refinary.fragment.modules.news.NewsDetails;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.ExpandableHeightGridView;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.navigation.MainMenu;
import com.application.refinary.pojo.navigation.NavigationPojo;
import com.application.refinary.pojo.navigation.SideMenu;
import com.application.refinary.pojo.navigation.SubMenuItem;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class HomeGridFragment extends Fragment implements ApiListener {


    private ExpandableHeightGridView gridView;
    private Context context = getContext();
    private ProgressBar loading;


    private ExpandableListView expandableListView;
    private DrawerLayout drawer;
    private TextView toolBarText;
    private int notification;
    private TextView tv_user;
    private APIMethods api;
    private int count  = 0;
    private ImageView banner;
    private ShimmerFrameLayout shimmerFrameLayout;



    private String token;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    token = GlobalClass.sharedPreferences1.getString("logic_token","");
        getNavMenuItems();
    }

    @Override
    public void onResume() {
        super.onResume();
        getNavMenuItems();
    }

    private void getNavMenuItems() {

        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<NavigationPojo> navApiCall = api.navigationAPI(headerMap);
        APIResponse.callBackgroundRetrofit(navApiCall, "navitems", context, this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_grid, container, false);

        context = getContext();
        loading = getActivity().findViewById(R.id.loading);
        gridView = view.findViewById(R.id.gridview);
        getActivity().findViewById(R.id.btn_back).setVisibility(View.GONE);
        getActivity().findViewById(R.id.nav_menu).setVisibility(View.VISIBLE);
        expandableListView = getActivity().findViewById(R.id.expandableListView);
        tv_user = view.findViewById(R.id.tv_user);
        drawer = getActivity().findViewById(R.id.drawer_layout);
        expandableListView.setDividerHeight(0);
        toolBarText = getActivity().findViewById(R.id.toolbar_title);
        tv_user.setText(greetingBasedOntime() + GlobalClass.sharedPreferences.getString("fName", ""));
        banner = view.findViewById(R.id.img_view);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        expandableListView.setVisibility(View.GONE);

        toolBarText.setText("Home");


        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new HomeScreenBanner();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {

        try {
            GlobalClass.mPreviousRouteName = "general.HomeGridFragment";
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            expandableListView.setVisibility(View.VISIBLE);
            GlobalClass.headerList.clear();
            NavigationPojo navigation = (NavigationPojo) response.body();
            if (apiCallName.equalsIgnoreCase("navitems")){
               /* HashMap<SideMenu, List<SubMenuItem>> childList = new HashMap<>();
                for (int i = 0; i < navigation.getSideMenu().size(); i++) {
                    GlobalClass.headerList.add(navigation.getSideMenu().get(i));
                    if (!navigation.getSideMenu().get(i).getSubMenuItems().isEmpty()) {
                        for (int j = 0; j < navigation.getSideMenu().get(i).getSubMenuItems().size(); j++) {
                            List<SubMenuItem> routesSubcategory = navigation.getSideMenu().get(i).getSubMenuItems();
                            childList.put(GlobalClass.headerList.get(i), routesSubcategory);
                        }
                    }
                }*/

                List<SideMenu> sideMenuList = navigation.getSideMenu();
                List<MainMenu> mainMenus = navigation.getMainMenu();

                populateExpandableList(context, sideMenuList);
                setAdapter(mainMenus);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*private void populateExpandableList(Context context, NavigationPojo headerList, HashMap<SideMenu, List<SubMenuItem>> childList) {
        ExpandableListAdapter expandableListAdapter = new ExpandableNavListAdapter(context, headerList, childList);

        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupClickListener((parent, v, groupPosition, ID) -> {
            try {
                if (headerList.getSideMenu().get(groupPosition).getSubMenuItems().size() <= 0) {
                    ChangeFragment(headerList.getSideMenu().get(groupPosition).getNavigationRoute(), headerList.getSideMenu().get(groupPosition).getNavigationTitle());
                    if (headerList.getSideMenu().get(groupPosition).getSubMenuItems().size() <= 0) {
                        handelNavDrawer();
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            return false;
        });

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            try {
                if (GlobalClass.hasActiveBooking) {
                    if (GlobalClass.ChangeChildFragment(childList.get(headerList.getSideMenu().get(groupPosition)).get(childPosition).getNavigationRoute(), childList.get(headerList.getSideMenu().get(groupPosition)).get(childPosition).getNavigationTitle(), (FragmentActivity) context)) {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.home_fragment_container, new DoorUnlockFragment()).commit();
                            getActivity().getSupportFragmentManager().popBackStack();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        handelNavDrawer();
                    }
                } else {
                    GlobalClass.ShowAlet(context, "Message", "You require an active booking to avail context service");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });
    }*/


    private void populateExpandableList(Context context, List<SideMenu> sideMenuList) {
        ExpandableListAdapter expandableListAdapter = new ExpandableNavListAdapter(context, sideMenuList);

        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupClickListener((parent, v, groupPosition, ID) -> {
            try {
                if (sideMenuList.get(groupPosition).getSubMenuItems().size() <= 0) {
                    ChangeFragment(sideMenuList.get(groupPosition).getNavigationRoute(), sideMenuList.get(groupPosition).getNavigationTitle());
                    if (sideMenuList.get(groupPosition).getSubMenuItems().size() <= 0) {
                        handelNavDrawer();
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            return false;
        });

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            try {
                if (GlobalClass.hasActiveBooking) {
                    if (GlobalClass.ChangeChildFragment(sideMenuList.get(groupPosition).getSubMenuItems().get(childPosition).getNavigationRoute(), sideMenuList.get(groupPosition).getSubMenuItems().get(childPosition).getNavigationTitle(), (FragmentActivity) context)) {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.home_fragment_container, new DoorUnlockFragment()).commit();
                            getActivity().getSupportFragmentManager().popBackStack();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        handelNavDrawer();
                    }
                } else {
                    GlobalClass.ShowAlet(context, "Message", "You require an active booking to avail context service");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });
    }


    private void ChangeFragment(String className, String title) {
        try {
            className = GlobalClass.getClassName(className);
            if (className.contains("test")) {
                GlobalClass.ShowAlet(context, "Alert", "Work in Progress..!!");
            } else if (className.contains("instagram")) {
                Uri uri = Uri.parse("https://www.instagram.com/refineryhotel/?hl=en");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/refineryhotel/?hl=en")));
                }
            }else if (className.contains("modules.StoreFragment")){
                Fragment fragment=new StoreFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
            }

            else if (className.contains("modules.HotelInformationFragment")){
                Fragment fragment=new HotelInformationFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
            }


            else if (!GlobalClass.mPreviousRouteName.equalsIgnoreCase(className)) {
                if (className.equalsIgnoreCase("general.Logout")) {
                    Intent intent = new Intent(context, UserAuthenticationActivity.class);
                    intent.putExtra("logout", true);
                    startActivity(intent);
                    getActivity().finish();
                } else if (className.contains("HomeGridFragment")) {
                    GlobalClass.mPreviousRouteName = "general.HomeGridFragment";
                    /*Intent intent = new Intent(context, HomeScreenActivity.class);
                    intent.putExtra("changes", "");
                    context.startActivity(intent);
                    getActivity().finish();*/
                    Fragment fragment=new HomeGridFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
                } else if (!className.isEmpty()) {
                    GlobalClass.mPreviousRouteName = className;
                    String fullPathOfTheClass = "com.application.refinary.fragment." + className;
                    Class<?> cls = Class.forName(fullPathOfTheClass);
                    Fragment fragment = (Fragment) cls.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("tool_bar_header", title);
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();

                }
            }

        } catch (Exception e) {
            Log.d("error", e.getMessage());
            e.printStackTrace();
        }
    }


    private void setAdapter(List<MainMenu> item) {
        HomeGridViewAdapter adapter = new HomeGridViewAdapter(context, item, position -> {
            if (GlobalClass.hasActiveBooking) {
             //   item.get(position).getId();
                if (GlobalClass.ChangeChildFragment(item.get(position).getNavigationRoute(), item.get(position).getNavigationTitle(), (FragmentActivity) context)) {
                    try {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_fragment_container, new DoorUnlockFragment()).commit();
                        getActivity().getSupportFragmentManager().popBackStack();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                GlobalClass.ShowAlet(context, "Message", "You require an active booking to avail these service");
            }
        });

        gridView.setAdapter(adapter);
        gridView.setExpanded(true);
    }


    @Override
    public void onErrorListner() {

    }

    private void handelNavDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}