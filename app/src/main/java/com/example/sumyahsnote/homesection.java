package com.example.sumyahsnote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class homesection extends Fragment {

    public homesection() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_homesection, container, false);
        CardView page1,page2,page3,page4;

        page1 = view.findViewById(R.id.card1);
        page2 = view.findViewById(R.id.card2);
        page3 = view.findViewById(R.id.card3);
        page4 = view.findViewById(R.id.card4);

        page1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home_page)getActivity()).callFragment(new MapFragment(),1);
            }
        });
        page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home_page)getActivity()).callFragment(new CameraFragment(),1);
            }});
        return view;
    }
}