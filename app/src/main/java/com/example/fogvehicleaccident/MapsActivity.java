package com.example.fogvehicleaccident;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String from , to, no, type, name, contact;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    LocationManager locationManager;
    String lati, loni;
    int icon;
    Double latitude = 0.0, longitude = 0.0;
    FusedLocationProviderClient mFusedLocationClient;
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        no = getIntent().getStringExtra("no");
        type = getIntent().getStringExtra("type");

    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location1) {
                        // Got last known location. In some rare situations this can be null.
                        if (location1 != null) {
                            // Logic to handle location object

                            longitude = location1.getLongitude();
                            latitude = location1.getLatitude();
                            lati = (String.valueOf(latitude));
                            loni = (String.valueOf(longitude));
                            dref.child("User").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        name = dataSnapshot.child("name").getValue().toString();
                                        contact = dataSnapshot.child("contact").getValue().toString();
                                        live(name,contact, lati , loni);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            //showing on map
                            LatLng latLng1 = new LatLng(latitude, longitude);
                            googleMap.addMarker(new MarkerOptions().position(latLng1).title("Your location"));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng1));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 50));
                        } else {

                            Toast.makeText(getApplicationContext(), "Please allow location to this app", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

        dref.child("Live").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (!uid.equals(ds.getKey())) {
                        Location_Attr Attr = ds.getValue(Location_Attr.class);
                        LatLng latLng = new LatLng(Double.valueOf(Attr.getLatitude()), Double.valueOf(Attr.getLongitude()));
                        String Speed = Attr.getSpeed();
                        String VNo = Attr.getVehicle();
                        String Contact = Attr.getContact();
                        String type = Attr.getType();
                        if (type.equals("Car")) {
                            icon = R.drawable.car;
                        } else if (type.equals("Bike")) {
                            icon = R.drawable.car;
                        } else if (type.equals("Bus")) {
                            icon = R.drawable.car;
                        } else if (type.equals("Truck")) {
                            icon = R.drawable.car;
                        } else if (type.equals("Jeep")) {
                            icon = R.drawable.car;
                        }
                        googleMap.addMarker(new MarkerOptions().position(latLng).
                                icon(BitmapDescriptorFactory.fromBitmap(
                                        createCustomMarker(MapsActivity.this, icon, Speed, VNo)))).setTitle(Contact);
                        //googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 50));

                    }
                }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void live(String name, String contact, String lati, String loni) {

    }


    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String speed, String VNo) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_layout, null);

        ImageView markerImage = (ImageView) marker.findViewById(R.id.user_dp);
        markerImage.setImageResource(resource);
        TextView txt_name = (TextView)marker.findViewById(R.id.speed);
        TextView txt_name1 = (TextView)marker.findViewById(R.id.no);
        txt_name.setText(speed);
        txt_name1.setText(VNo);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }
}
