package com.application.refinary.fragment.general;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.refinary.R;


public class About extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        Bundle args=getArguments();
        if(args!=null){
            toolbar_title.setText(args.getString("tool_bar_header"));
        }

        return view;
    }
}