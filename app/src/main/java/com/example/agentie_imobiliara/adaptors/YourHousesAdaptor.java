package com.example.agentie_imobiliara.adaptors;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agentie_imobiliara.AddHousesActivity;
import com.example.agentie_imobiliara.DAO.DAOHouses;
import com.example.agentie_imobiliara.EditHouseActivity;
import com.example.agentie_imobiliara.MainActivity;
import com.example.agentie_imobiliara.R;
import com.example.agentie_imobiliara.model.House;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.squareup.picasso.Picasso;

import java.util.List;

public class YourHousesAdaptor extends RecyclerView.Adapter<YourHousesAdaptor.ImageViewHolder>{

private Context mContext;
private List<House> mUploads;
private  DAOHouses daoHouses = new DAOHouses();
AlertDialog.Builder alertDialogBuilder;
public static final String EXTRA_TEXT = "com.example.agentie_imobiliara.key";

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

        alertDialogBuilder = new AlertDialog.Builder(mContext);

        holder.delete_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder.setMessage("Are you sure you want to delete this house?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        daoHouses.deleteHouse(currentUpload.getKey()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(mContext, "House was deleted successfully!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(mContext,""+ task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext, "Houses was not deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
                onBackPressed();
            }

        });
        alertDialogBuilder.setCancelable(false);

        holder.edit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editHouseIntent = new Intent(mContext, EditHouseActivity.class);
                editHouseIntent.putExtra(EXTRA_TEXT,currentUpload.getKey());
                mContext.startActivity(editHouseIntent);
            }
        });

        }

    public void onBackPressed()
    {
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Alert Dialog");
        alertDialog.show();
    }

    @Override
public int getItemCount() {
        return mUploads.size();
        }

public class ImageViewHolder extends  RecyclerView.ViewHolder{

    public TextView address, size, rooms, baths, floors, special, owner, price;
    public ImageView imageView;
    public FloatingActionButton delete_b, edit_b;

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
        delete_b = itemView.findViewById(R.id.delete_house);
        edit_b = itemView.findViewById(R.id.edit_house);

    }

}


}


