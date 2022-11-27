package com.example.agentie_imobiliara;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.agentie_imobiliara.ui.home.HomeFragment;
import com.example.agentie_imobiliara.ui.logout.LogoutFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.agentie_imobiliara.databinding.ActivityMainPageBinding;

public class MainPage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainPageBinding binding;
    NavigationView navigationView;
    Dialog addFilters;
    private Spinner spinner_floors, spinner_rooms, spinner_baths;
    private EditText location, special;
    private Button apply;
    private CheckBox location_check, rooms_check, min_check, max_check, floors_check, baths_check, special_check, price_check;
    private SeekBar price, min_size, max_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth authAction=FirebaseAuth.getInstance();

        navigationView = (NavigationView) findViewById(R.id.nav_view);


        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainPage.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

//
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_yourHouses, R.id.liked_houses, R.id.bookings, R.id.boiking_history, R.id.account, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case R.id.filter_button:
                openFilterDialog();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void openFilterDialog() {
        addFilters  = new Dialog(MainPage.this);
        addFilters.setContentView(R.layout.filters_layout);
        addFilters.setTitle("Add filters");
        addFilters.setCancelable(true);
        addFilters.show();

        location = addFilters.findViewById(R.id.location);
        special = addFilters.findViewById(R.id.special_req);
        apply = addFilters.findViewById(R.id.apply);

        location_check = addFilters.findViewById(R.id.location_check);
        min_check = addFilters.findViewById(R.id.min_size_check);
        max_check = addFilters.findViewById(R.id.max_size_check);
        rooms_check = addFilters.findViewById(R.id.rooms_check);
        baths_check = addFilters.findViewById(R.id.baths_check);
        floors_check = addFilters.findViewById(R.id.floors_check);
        special_check = addFilters.findViewById(R.id.special_check);
        price_check = addFilters.findViewById(R.id.price_check);

        min_size = addFilters.findViewById(R.id.min_size);
        max_size = addFilters.findViewById(R.id.max_size);
        price = addFilters.findViewById(R.id.price);

        spinner_floors = (Spinner) addFilters.findViewById(R.id.rooms_no);
        ArrayAdapter<CharSequence> adapter_floors = ArrayAdapter.createFromResource(this,
                R.array.floors_spinner, android.R.layout.simple_spinner_item);

        adapter_floors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_floors.setAdapter(adapter_floors);

        spinner_floors.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getApplicationContext());


        spinner_baths = (Spinner) addFilters.findViewById(R.id.baths_no);
        ArrayAdapter<CharSequence> adapter_baths = ArrayAdapter.createFromResource(this,
                R.array.baths_spinner, android.R.layout.simple_spinner_item);

        adapter_floors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_baths.setAdapter(adapter_baths);

        spinner_baths.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getApplicationContext());


        spinner_rooms = (Spinner) addFilters.findViewById(R.id.rooms_no);
        ArrayAdapter<CharSequence> adapter_rooms = ArrayAdapter.createFromResource(this,
                R.array.room_spinner, android.R.layout.simple_spinner_item);

        adapter_floors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rooms.setAdapter(adapter_rooms);

        spinner_rooms.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getApplicationContext());


    }

}