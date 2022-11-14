package com.example.agentie_imobiliara.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agentie_imobiliara.DAO.DAOBooking;
import com.example.agentie_imobiliara.R;
import com.example.agentie_imobiliara.model.Booking;
import com.example.agentie_imobiliara.model.House;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BookingsAdaptor extends RecyclerView.Adapter<BookingsAdaptor.ImageViewHolder>{
    private Context mContext;
    private List<Booking> mUploads;
    private DAOBooking daoHouses = new DAOBooking();
    AlertDialog.Builder alertDialogBuilder;

    public BookingsAdaptor(Context context, List<Booking> uploads)
    {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public BookingsAdaptor.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.your_bookings_container, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Booking currentUpload = mUploads.get(position);
        holder.booking_address.setText("Booking address: " + currentUpload.getAddress());
        holder.visitor.setText("Visitor: " + currentUpload.getUser());
        holder.date.setText("Date: " + currentUpload.getDate().getDay() + "/" + currentUpload.getDate().getMonth() + "/" + currentUpload.getDate().getYear());
        holder.hour.setText("Hour: " + currentUpload.getHour());
        if(!currentUpload.isAccept_booking() && currentUpload.getRejection_message().equals(""))
        {
            holder.status.setText("Status: pending");
        }
        else
        if(currentUpload.isAccept_booking())
        {
            holder.status.setText("Status: accepted");
        }
        else
        if(!currentUpload.isAccept_booking() && !currentUpload.getRejection_message().equals(""))
        {
            holder.status.setText("Status: disapproved");
            holder.reason.setText("Reason: " + currentUpload.getRejection_message());
        }

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public static class ImageViewHolder extends  RecyclerView.ViewHolder{
        public TextView booking_address, visitor, date, hour, status, reason;
        public FloatingActionButton approve, disapprove;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            booking_address = itemView.findViewById(R.id.house_address);
            visitor = itemView.findViewById(R.id.visitor);
            date = itemView.findViewById(R.id.date_booking);
            hour = itemView.findViewById(R.id.booking_hour);
            status = itemView.findViewById(R.id.status);
            reason = itemView.findViewById(R.id.reason);
            approve = itemView.findViewById(R.id.approve);
            disapprove = itemView.findViewById(R.id.disapprove);
        }

    }
}
