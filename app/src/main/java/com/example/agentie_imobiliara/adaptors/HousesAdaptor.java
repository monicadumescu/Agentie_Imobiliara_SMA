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

import com.example.agentie_imobiliara.AddHousesActivity;
import com.example.agentie_imobiliara.R;
import com.example.agentie_imobiliara.model.House;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Scanner;

public class HousesAdaptor extends RecyclerView.Adapter<HousesAdaptor.ImageViewHolder> {

    private Context mContext;
    private List<House> mUploads;

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

            }
        });

        holder.book_visit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
