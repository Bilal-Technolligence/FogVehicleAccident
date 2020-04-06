package com.example.fogvehicleaccident;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CompleteProfileActivity extends AppCompatActivity {
    CardView btnRegister;
    ProgressDialog progressDialog;
    ImageView profileImage;
    private Uri imagePath;
    int count = 0;
    String userName, userGmail, userCategory, userPassword;
    private LocationManager locationManager;
    String provider, lati, loni, addressString;
    Double latitude = 0.0, longitude = 0.0;
    // FusedLocationProviderClient mFusedLocationClient;
    EditText name, contact, address;
    //  final FirbaseAuthenticationClass firbaseAuthenticationClass = new FirbaseAuthenticationClass();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference = database.getReference("Users");


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        userCategory = getIntent().getStringExtra("Category");
//        if(userCategory.equals("Service"))
//        {
//            parkingSpace.setVisibility( View.VISIBLE );
//            parkingName.setVisibility(View.VISIBLE);
//            age.setVisibility( View.INVISIBLE );
//
//        }
//        if(userCategory.equals("User")) {
//            parkingSpace.setVisibility(View.INVISIBLE);
//            parkingName.setVisibility(View.INVISIBLE);
//            age.setVisibility(View.VISIBLE);
//
//        }
        userName = getIntent().getStringExtra("name");
        profileImage = (ImageView) findViewById(R.id.profileImage);
        name = (EditText) findViewById(R.id.name);
        //Setting UserName
        name.setText(String.valueOf(userName));
        contact = (EditText) findViewById(R.id.contact);
        address = (EditText) findViewById(R.id.txtAddress);
        //address=(EditText) findViewById(R.id.address);
        btnRegister = (CardView) findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering..... ");
        userGmail = getIntent().getStringExtra("Email");
        userPassword = getIntent().getStringExtra("Password");

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location1) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location1 != null) {
//                            // Logic to handle location object
//
//                            longitude = location1.getLongitude();
//                            latitude = location1.getLatitude();
//                            lati = (String.valueOf(latitude));
//                            loni = (String.valueOf(longitude));
//                            //Toast.makeText(getApplicationContext() , lati + " " + loni ,  Toast.LENGTH_LONG).show();
//                            Geocoder geoCoder = new Geocoder(CompleteProfileActivity.this, Locale.getDefault());
//                            StringBuilder builder = new StringBuilder();
//                            try {
//                                List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
//                                int maxLines = address.get(0).getMaxAddressLineIndex();
//                                for (int i = 0; i < maxLines; i++) {
//                                    String addressStr = address.get(0).getAddressLine(i);
//                                    builder.append(addressStr);
//                                    builder.append(" ");
//                                }
//                                if (address.size() > 0) {
//                                    System.out.println(address.get(0).getLocality());
//                                    System.out.println(address.get(0).getCountryName());
//                                    //Toast.makeText(getApplicationContext() , address.get(0).getAddressLine(0) , Toast.LENGTH_LONG).show();
//                                }
//                                String finalAddress = builder.toString(); //This is the complete address.
//                                addressString = address.get(0).getAddressLine(0);
//                            } catch (IOException e) {
//                                // Handle IOException
//                            } catch (NullPointerException e) {
//                                // Handle NullPointerException
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Please enable your location", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//        Criteria criteria = new Criteria();
//        provider = locationManager.getBestProvider(criteria, false);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                recreate();
//                return;
//            }
//        }
//        final Location location = locationManager.getLastKnownLocation(provider);
//

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().toUpperCase();
                String Address = address.getText().toString();
                String Contact = contact.getText().toString();
                if (Name.equals("")) {
                    name.setError("Enter Valid Name");
                    name.setFocusable(true);
//                } else if (userCategory.equals("User")&&Age.equals("")) {
//                    age.setError("Enter Valid Age");
//                    age.setFocusable(true);
                } else if (Address.equals("")) {
                    contact.setError("Enter Valid Address");
                    contact.setFocusable(true);
                } else if (Contact.equals("")) {
                    contact.setError("Enter Valid Contact Number");
                    contact.setFocusable(true);
                } else if (count == 0) {
                    Snackbar.make(v, "Please Select Image", Snackbar.LENGTH_LONG).show();
                } else {
                    progressDialog.show();
                    RegisterUser(userGmail, userPassword, Contact, Name, imagePath, Address, progressDialog);

                }
            }
        });

    }

    public void RegisterUser(final String userGmail, String userPassword, final String contact, final String name, final Uri imagePath, final String address, final ProgressDialog progressDialog) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(userGmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                            storageReference.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful()) ;
                                    Uri downloadUri = uriTask.getResult();

                                    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    UserAttr userAttr = new UserAttr();
                                    userAttr.setEmail(userGmail);
                                    userAttr.setContact(contact);
                                    userAttr.setName(name);
                                    userAttr.setAddress(address);
                                    userAttr.setId(uid);
                                    userAttr.setImageUrl(downloadUri.toString());
                                    reference.child(uid).setValue(userAttr);

                                    Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
//                                  getApplicationContext().finish();
                                    //save session
                                    //saving value true for session
                                    Save.save(getApplicationContext(),"session","true");
                                    Intent intent = new Intent(CompleteProfileActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    progressDialog.dismiss();

                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CompleteProfileActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CompleteProfileActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == resultCode
                && data != null && data.getData() != null) {

            imagePath = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath);
                profileImage.setImageBitmap(bitmap);
                count = 1;

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
