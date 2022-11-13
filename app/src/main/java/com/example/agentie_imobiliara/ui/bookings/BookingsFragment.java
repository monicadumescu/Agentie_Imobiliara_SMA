package com.example.agentie_imobiliara.ui.bookings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.agentie_imobiliara.R;
import com.example.agentie_imobiliara.model.Booking;
import com.example.agentie_imobiliara.model.Date;
import com.example.agentie_imobiliara.model.House;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.List;


public class BookingsFragment extends Fragment {

    private DatabaseReference databaseReference, houseDatabaseReference;
    FirebaseAuth authAction=FirebaseAuth.getInstance();
    private List<Booking> bookingsList;
    private Date date;
    private String date_string;

    public BookingsFragment() {
        // Required empty public constructor
    }

    public static BookingsFragment newInstance(String param1, String param2) {
        BookingsFragment fragment = new BookingsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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
        View view =  inflater.inflate(R.layout.fragment_bookings, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendarView2);

        databaseReference = FirebaseDatabase.getInstance().getReference("Booking");
        houseDatabaseReference = FirebaseDatabase.getInstance().getReference("House");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Booking booking = dataSnapshot.getValue(Booking.class);
                    House house = houseDatabaseReference.child(booking.getHouse_key()).get().getResult().getValue(House.class);
                    if(booking.getUser().equals(authAction.getCurrentUser().getEmail()) || house.getOwner().equals(authAction.getCurrentUser().getEmail())) {
                        booking.setObject_key(dataSnapshot.getKey());
                        bookingsList.add(booking);
                        date = new Date(booking.getDate().getDay(), booking.getDate().getMonth(), booking.getDate().getYear());
                        if(booking.getUser().equals(authAction.getCurrentUser().getEmail()))
                        {
                            date_string = date.getYear() + "-" + date.getMonth() + "-" + date.getDay() + " 00:00:00";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                            ParsePosition parsePosition = new ParsePosition(0);
                            java.util.Date date1 = simpleDateFormat.parse(date_string, parsePosition);
                            long date_long = date1.getTime();
                            calendarView.setDate(date_long);
                            calendarView.setDateTextAppearance(android.R.color.holo_blue_light);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return  view;
    }
}