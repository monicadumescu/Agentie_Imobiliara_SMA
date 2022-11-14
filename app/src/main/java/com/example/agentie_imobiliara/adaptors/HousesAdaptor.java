package com.example.agentie_imobiliara.adaptors;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agentie_imobiliara.AddHousesActivity;
import com.example.agentie_imobiliara.Book_Visit_Activity;
import com.example.agentie_imobiliara.DAO.DAOHouses;
import com.example.agentie_imobiliara.DAO.DAOSavedHouses;
import com.example.agentie_imobiliara.EditHouseActivity;
import com.example.agentie_imobiliara.R;
import com.example.agentie_imobiliara.model.House;
import com.example.agentie_imobiliara.model.SavedHouses;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Scanner;

public class HousesAdaptor extends RecyclerView.Adapter<HousesAdaptor.ImageViewHolder> {

    private Context mContext;
    private List<House> mUploads;
    private DatabaseReference databaseReference;
    private DAOSavedHouses daoSavedHouses = new DAOSavedHouses();
    boolean liked = false;
    String liked_key;
    public static final String EXTRA_TEXT = "com.example.agentie_imobiliara.key";
    public static final String EXTRA_ADDRESS = "com.example.agentie_impobiliara.address";

    public  HousesAdaptor(Context context, List<House> uploads)
    {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.house_container, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        House currentUpload = mUploads.get(position);
        holder.address.setText("Address: " + currentUpload.getAddress());
        holder.size.setText("Size: " + currentUpload.getSize() + " sq");
        holder.rooms.setText("Number of rooms: " + currentUpload.getRooms());
        holder.baths.setText("Number of baths: " + currentUpload.getBaths());
        holder.floors.setText("Number of floors: " + currentUpload.getFloors());
        holder.special.setText("Special features: " + currentUpload.getSpecial());
        holder.owner.setText("Owner: " + currentUpload.getOwner());
        holder.price.setText("Price: " + currentUpload.getPrice());
        Picasso.get().load(currentUpload.getPictureName()).fit().centerCrop().into(holder.imageView);


        FirebaseAuth authAction=FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(mContext);

        holder.search_address_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = holder.address.getText().toString();
                Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                searchIntent.putExtra(SearchManager.QUERY, address);
                mContext.startActivity(searchIntent);
            }
        });

        holder.save_house_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("SavedHouses");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            SavedHouses savedHouses1 = dataSnapshot.getValue(SavedHouses.class);
                           if(savedHouses1.getKey().equals(currentUpload.getKey()) && savedHouses1.getEmail().equals(authAction.getCurrentUser().getEmail()))
                           {
                               liked = true;
                               liked_key = dataSnapshot.getKey();
                           }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if(liked == false)
                {
                    databaseReference = FirebaseDatabase.getInstance().getReference("SavedHouses");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            SavedHouses savedHouses = new SavedHouses(authAction.getCurrentUser().getEmail(), currentUpload.getKey());
                            daoSavedHouses.addHouse(savedHouses).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(mContext, "House was saved successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(mContext,""+ task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if(liked == true)
                {
                    databaseReference = FirebaseDatabase.getInstance().getReference("SavedHouses");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            daoSavedHouses.deleteHouse(liked_key).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(mContext, "House was unsaved successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(mContext,""+ task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

        });

        holder.book_visit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent book_visit = new Intent(mContext, Book_Visit_Activity.class);
                book_visit.putExtra(EXTRA_TEXT,currentUpload.getKey());
                book_visit.putExtra(EXTRA_ADDRESS, currentUpload.getAddress());
                mContext.startActivity(book_visit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends  RecyclerView.ViewHolder{

        public TextView address, size, rooms, baths, floors, special, owner, price;
        public ImageView imageView;
        FloatingActionButton search_address_b, save_house_b, book_visit_b;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            size = itemView.findViewById(R.id.size);
            rooms = itemView.findViewById(R.id.rooms);
            baths = itemView.findViewById(R.id.baths);
            floors = itemView.findViewById(R.id.floors);
            special = itemView.findViewById(R.id.special);
            owner = itemView.findViewById(R.id.owner);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.picture);
            search_address_b = itemView.findViewById(R.id.edit_house);
            save_house_b = itemView.findViewById(R.id.delete_house);
            book_visit_b = itemView.findViewById(R.id.book_visit);
        }

    }

}
