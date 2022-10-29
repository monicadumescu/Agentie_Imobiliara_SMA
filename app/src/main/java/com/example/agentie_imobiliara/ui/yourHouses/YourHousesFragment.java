package com.example.agentie_imobiliara.ui.yourHouses;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agentie_imobiliara.DAO.DAOHouses;
import com.example.agentie_imobiliara.R;
import com.example.agentie_imobiliara.model.House;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class YourHousesFragment extends Fragment {

private Uri imageUri;

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

        FirebaseAuth authAction=FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(getContext());
        DAOHouses daoHouses = new DAOHouses();

        Button addHouseButton = (Button) view.findViewById(R.id.addHouses);
        Dialog addHouseDialog = new Dialog(getContext());

        ActivityResultLauncher<String> getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result != null)
                {
                    imageUri = result;
                }
            }
        });

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
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        getContent.launch("image/*");
                    }
                });

                addHouse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(address.getText().toString().equals("") || size.getText().toString().equals("") || rooms.getText().toString().equals("") || baths.getText().toString().equals("") || floors.getText().toString().equals("") || special.getText().toString().equals(""))
                        {
                            Toast.makeText(getContext(), "Please complete all fields!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                           House house = new House(address.getText().toString(), size.getText().toString(), rooms.getText().toString(), baths.getText().toString(), floors.getText().toString(), special.getText().toString(), authAction.getCurrentUser().getEmail().toString(),imageUri);
                           daoHouses.addHouse(house).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       Toast.makeText(getContext(),"House was added successfully", Toast.LENGTH_SHORT).show();
                                   }
                                   else{
                                       Toast.makeText(getContext(),""+ task.getException(), Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
                        }
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