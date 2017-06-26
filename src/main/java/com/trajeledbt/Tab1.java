package com.trajeledbt;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.trajeledbt.R;

public class Tab1 extends Fragment {


    Button mode1;
    Button mode2;
    Button modeoff;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1 , container , false);

        mode1 = (Button)view.findViewById(R.id.butMode1);
        mode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainMenu)getActivity()).connection){
                    ((MainMenu)getActivity()).connectedThread.send("led1");
                }
                else{
                    Toast.makeText(((MainMenu)getActivity()).getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                }
            }
        });

        mode2 = (Button)view.findViewById(R.id.butMode2);
        mode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainMenu)getActivity()).connection){
                    ((MainMenu)getActivity()).connectedThread.send("led2");
                }
                else{
                    Toast.makeText(((MainMenu)getActivity()).getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                }
            }
        });

        modeoff = (Button)view.findViewById(R.id.butOff);
        modeoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainMenu)getActivity()).connection){
                    ((MainMenu)getActivity()).connectedThread.send("off");
                }
                else{
                    Toast.makeText(((MainMenu)getActivity()).getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }




}
