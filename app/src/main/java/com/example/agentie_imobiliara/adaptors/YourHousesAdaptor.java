package com.example.agentie_imobiliara.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agentie_imobiliara.R;
import com.example.agentie_imobiliara.model.House;
import com.squareup.picasso.Picasso;

import java.util.List;

public class YourHousesAdaptor extends RecyclerView.Adapter<YourHousesAdaptor.ImageViewHolder>{

private Context mContext;
private List<House> mUploads;

public  YourHousesAdaptor(Context context, List<House> uploads)
        {
        mContext = context;
        mUploads = uploads;
        }

@NonNull
@Override
public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.your_houses_container, parent, false);
        return new ImageViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        House currentUpload = mUploads.get(position);
        holder.address.setText("Address" + currentUpload.getAddress());
        holder.size.setText("Size: " + currentUpload.getSize() + " sq");
        holder.rooms.setText("Number of rooms: " + currentUpload.getRooms());
        holder.baths.setText("Number of baths: " + currentUpload.getBaths());
        holder.floors.setText("Number of floors: " + currentUpload.getFloors());
        holder.special.setText("Special features: " + currentUpload.getSpecial());
        holder.owner.setText("Owner: " + currentUpload.getOwner());
        holder.price.setText("Price: " + currentUpload.getPrice());
        Picasso.get().load(currentUpload.getPictureName()).fit().centerCrop().into(holder.imageView);
        }

@Override
public int getItemCount() {
        return mUploads.size();
        }

public class ImageViewHolder extends  RecyclerView.ViewHolder{

    public TextView address, size, rooms, baths, floors, special, owner, price;
    public ImageView imageView;

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
    }
}
}
