package com.application.refinary.fragment.modules;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;

public class HotelInformationFragment extends Fragment {

    private WebView web_view;
    private Context mContext;
    private ImageView btn_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_information2, container, false);
        try {
            mContext = view.getContext();

            web_view = view.findViewById(R.id.web_view);
            btn_back= getActivity().findViewById(R.id.btn_back);
            TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            toolbar_title.setText("Hotel Information");
            btn_back.setVisibility(View.VISIBLE);

            web_view.getSettings().setLoadsImagesAutomatically(true);
            web_view.getSettings().setJavaScriptEnabled(true);
            web_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            Bundle args = getArguments();

            web_view.loadUrl("https://refineryhotelnewyork.reztrip.com/mobile/hotels/NYCREF");

            web_view.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    GlobalClass.ShowAlet(mContext,"Alert",description);
                }
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    GlobalClass.ShowAlet(mContext,"Alert",rerr.getDescription().toString());
                }
            });
            web_view.setOnKeyListener((v, keyCode, event) -> { //This is the filter
                if (event.getAction()!= KeyEvent.ACTION_DOWN)
                    return true;
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    backpressed();
                    return true;
                } return false;
            });



        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    private void backpressed() {
        if (web_view.canGoBack()) {
            web_view.goBack();
        } else {
            (getActivity()).onBackPressed();
        }
    }
}