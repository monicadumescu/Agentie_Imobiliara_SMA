package com.example.agentie_imobiliara;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.agentie_imobiliara.DAO.DAOHouses;
import com.example.agentie_imobiliara.adaptors.YourHousesAdaptor;
import com.example.agentie_imobiliara.model.House;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class EditHouseActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Uri imageUri;
    ActivityResultLauncher<Intent> activityResultLauncher;
    String fileName;
    House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_house);

        Intent intent = getIntent();
        String key = intent.getStringExtra(YourHousesAdaptor.EXTRA_TEXT);

        Button addHouse = (Button) findViewById(R.id.addHouseBH);
        Button addPictures = (Button) findViewById(R.id.addPicturesBH);
        EditText address = (EditText) findViewById(R.id.addressH);
        EditText size = (EditText) findViewById(R.id.sizeH);
        EditText rooms = (EditText) findViewById(R.id.roomsH);
        EditText baths = (EditText) findViewById(R.id.bathsH);
        EditText floors = (EditText) findViewById(R.id.floorsH);
        EditText special = (EditText) findViewById(R.id.specialH);
        EditText price = (EditText) findViewById(R.id.priceH);
        ImageView housePicture = (ImageView) findViewById(R.id.imageViewH);

        StorageReference imagesRef;

        FirebaseAuth authAction = FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(getApplicationContext());
        DAOHouses daoHouses = new DAOHouses();

        imagesRef = FirebaseStorage.getInstance().getReference();

        databaseReference = FirebaseDatabase.getInstance().getReference("House");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.getKey().equals(key)) {
                        house = dataSnapshot.getValue(House.class);
                        address.setText(house.getAddress());
                        size.setText(house.getSize());
                        rooms.setText(house.getRooms());
                        baths.setText(house.getBaths());
                        floors.setText(house.getFloors());
                        special.setText(house.getSpecial());
                        price.setText(house.getPrice());
                        Picasso.get().load(house.getPictureName()).fit().centerCrop().into(housePicture);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null)
                {
                    Toast.makeText(getApplicationContext(),"Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    imageUri = (Uri) result.getData().getData();
                    fileName = imageUri.toString() + ".jpg";
                    housePicture.setImageURI(imageUri);
                }
            }

        });

        addPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        });

        addHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(address.getText().toString().equals("") || size.getText().toString().equals("") || rooms.getText().toString().equals("") || baths.getText().toString().equals("") || floors.getText().toString().equals("") || special.getText().toString().equals("") || price.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please complete all fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    StorageReference fileRefrence = imagesRef.child("image" + System.currentTimeMillis() + ".jpg");
                    StorageReference pictureRef = imagesRef.child("images/" + "image" + System.currentTimeMillis());

                    fileRefrence.getName().equals(pictureRef.getName());
                    fileRefrence.getPath().equals(pictureRef.getPath());

                    housePicture.setDrawingCacheEnabled(true);
                    housePicture.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) housePicture.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , baos);

                    byte [] data = baos.toByteArray();

                    UploadTask uploadTask = fileRefrence.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Upss...Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    house.setAddress(address.getText().toString());
                                    house.setSize(size.getText().toString());
                                    house.setRooms(rooms.getText().toString());
                                    house.setBaths(baths.getText().toString());
                                    house.setFloors(floors.getText().toString());
                                    house.setSpecial(special.getText().toString());
                                    house.setPrice(price.getText().toString());
                                    house.setPictureName(uploadTask.getSnapshot().getStorage().getDownloadUrl().toString());

                                    daoHouses.editHouse(key, house).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(),"House was updated successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    Toast.makeText(getApplicationContext(),"House was updated successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }
            }
        });

    }
}