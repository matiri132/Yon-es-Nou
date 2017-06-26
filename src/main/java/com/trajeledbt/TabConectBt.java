package com.trajeledbt;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TabConectBt extends Fragment {

    Button butconnectBt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.conect_bt , container , false);

        butconnectBt = (Button)view.findViewById(R.id.butconnectBt);
        butconnectBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ((MainMenu)getActivity()).connectBluetooth();

            }

        });

        return view;
    }




}
