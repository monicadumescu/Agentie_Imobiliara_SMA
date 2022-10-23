package com.example.agentie_imobiliara.ui.yourHouses;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.agentie_imobiliara.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class YourHousesFragment extends Fragment {


    public YourHousesFragment() {
        // Required empty public constructor
    }


    public static YourHousesFragment newInstance(String param1, String param2) {
        YourHousesFragment fragment = new YourHousesFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_your_houses, container, false);

        Button addHouseButton = (Button) view.findViewById(R.id.addHouses);
        Dialog addHouseDialog = new Dialog(getContext());

        addHouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHouseDialog.setContentView(R.layout.add_houses_layout);
                addHouseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                Button addHouse = (Button) addHouseDialog.findViewById(R.id.addHouseB);
                Button addPictures = (Button) addHouseDialog.findViewById(R.id.addPicturesB);
                EditText address = (EditText) addHouseDialog.findViewById(R.id.address);
                EditText size = (EditText) addHouseDialog.findViewById(R.id.size);
                EditText rooms = (EditText) addHouseDialog.findViewById(R.id.rooms);
                EditText baths = (EditText) addHouseDialog.findViewById(R.id.baths);
                EditText floors = (EditText) addHouseDialog.findViewById(R.id.floors);
                EditText special = (EditText) addHouseDialog.findViewById(R.id.special);

                addPictures.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String path = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                            path = Environment.getStorageDirectory() + "/";
                        }
                        Uri uri = Uri.parse(path);
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(uri, "*/*");
                        startActivity(intent);
                    }
                });

                addHouse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                addHouseDialog.setCancelable(false);
                addHouseDialog.create();
                addHouseDialog.show();
            }
        });


        return  view;
    }
}