package com.application.refinary.fragment.modules.news;

import android.content.Context;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.adapter.NewsAdapter;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.news.NewsResponse;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class News extends Fragment implements ApiListener {

    private NestedScrollView nestedScrollView;
    private RelativeLayout lyt_empty_news;
    private RecyclerView news_recycler;
    private ProgressBar loading;
    private Context mContext;
    private NewsResponse mReponse;
    private NewsAdapter adapter;
    private ImageView btn_back;
    int page = 1, limit = 10;
    String order = "ASC";
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);


        try {
            mContext=view.getContext();
            getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
            TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            lyt_empty_news = view.findViewById(R.id.lyt_empty_news);
            news_recycler= view.findViewById(R.id.news_recycler);
            loading = view.findViewById(R.id.progress_bar);
            lyt_empty_news.setVisibility(View.GONE);
            nestedScrollView = view.findViewById(R.id.nestedScrollView);

            shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
            shimmerFrameLayout.startShimmer();
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            news_recycler.setVisibility(View.GONE);

            Bundle args=getArguments();
            if(args!=null){
                toolbar_title.setText(args.getString("tool_bar_header"));
            }



            /*nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                        page++;
                        loading.setVisibility(View.VISIBLE);
                        getNewsList();
                    }
                }
            });*/


        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        getNewsList();
    }

    private void getNewsList() {
     //   loading.setVisibility(View.VISIBLE);
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<NewsResponse> guest = api.newsApi(headerMap,order,page,limit);
        APIResponse.callBackgroundRetrofit(guest, "news", mContext, this);
    }


    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        mReponse = (NewsResponse) response.body();
        try {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            news_recycler.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            if (apiCallName.equalsIgnoreCase("news")) {
                if (mReponse.getStatusCode().equals(200)) {
                    lyt_empty_news.setVisibility((mReponse.getData().getArticles().size()>0) ? View.GONE : View.VISIBLE);
                    adapter=new NewsAdapter(mReponse.getData().getArticles(),mContext,(position->{
                        Fragment fragment=new NewsDetails();
                        Bundle bundle = new Bundle();
                        bundle.putString("url",(mReponse.getData().getArticles().get(position).getArticleURL()));
                        fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
                    }));
                 //   adapter = new NewsAdapter(mReponse.getData().getArticles(),mContext);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    news_recycler.setLayoutManager(mLayoutManager);
                    //news_recycler.setItemAnimator(new DefaultItemAnimator());
                    news_recycler.setAdapter(adapter);
                } else {
                    GlobalClass.showErrorMsg(mContext, mReponse.getMessage());
                }
            }
        }catch (Exception e){
            e.getMessage();
        }
    }


    @Override
    public void onErrorListner() {

    }

}