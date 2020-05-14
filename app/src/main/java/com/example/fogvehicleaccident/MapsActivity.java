package com.example.fogvehicleaccident;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.text.DecimalFormat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String from, to, no, type, name, contact;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    LocationManager locationManager;
    String lati, loni;
    int icon;
    Double latitude = 0.0, longitude = 0.0;
    FusedLocationProviderClient mFusedLocationClient;
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Button end;

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
        end = findViewById(R.id.Button);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dref.child("Live").child(uid).setValue(null);
                Intent i = new Intent(MapsActivity.this, StartEndPoint.class);
                startActivity(i);
                finish();
            }
        });

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
                                    if (dataSnapshot.exists()) {
                                        name = dataSnapshot.child("name").getValue().toString();
                                        contact = dataSnapshot.child("contact").getValue().toString();
                                        live(name, contact, lati, loni);
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
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (!uid.equals(ds.child("id"))) {
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

    private void live(final String name, final String contact, final String lati, final String loni) {

        dref.child("Live").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Double la = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                    Double lo = Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());
                    double lat2 = Double.parseDouble(lati);
                    double lon2 = Double.parseDouble(loni);
                    // googleMap.addMarker(new MarkerOptions().position(new LatLng(lat2, lon2)).title("Destination"));//.icon(BitmapDescriptorFactory.fromResource(R.drawable.dp)));
                    final int Radius = 6371;// radius of earth in Km
                    double dLat = Math.toRadians(lat2 - la);
                    double dLon = Math.toRadians(lon2 - lo);
                    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                            + Math.cos(Math.toRadians(la))
                            * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                            * Math.sin(dLon / 2);
                    double c = 2 * Math.asin(Math.sqrt(a));
                    double valueResult = Radius * c;
                    double km = valueResult / 1;
                    DecimalFormat newFormat = new DecimalFormat("####");
                    int kmInDec = Integer.valueOf(newFormat.format(km));
                    double meter = valueResult % 1000;
                    int meterInDec = Integer.valueOf(newFormat.format(meter));
                    Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                            + " Meter   " + meterInDec);
                    int mm = (int) (valueResult / 1000);
                    int speed = mm /10;
                    Double fspeed =  (speed * 3.6);

                    dref.child("Live").child(uid).child("latitude").setValue(lati);
                    dref.child("Live").child(uid).child("longitude").setValue(loni);
                    dref.child("Live").child(uid).child("speed").setValue(String.valueOf(fspeed));

                } else {
                    dref.child("Live").child(uid).child("id").setValue(uid);
                    dref.child("Live").child(uid).child("from").setValue(from);
                    dref.child("Live").child(uid).child("to").setValue(to);
                    dref.child("Live").child(uid).child("type").setValue(type);
                    dref.child("Live").child(uid).child("name").setValue(name);
                    dref.child("Live").child(uid).child("contact").setValue(contact);
                    dref.child("Live").child(uid).child("latitude").setValue(lati);
                    dref.child("Live").child(uid).child("longitude").setValue(loni);
                    dref.child("Live").child(uid).child("speed").setValue("0 km/h");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 10 seconds
                live(name, contact, lati, loni);
            }
        }, 10000);
    }


    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String speed, String VNo) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_layout, null);

        ImageView markerImage = (ImageView) marker.findViewById(R.id.user_dp);
        markerImage.setImageResource(resource);
        TextView txt_name = (TextView) marker.findViewById(R.id.speed);
        TextView txt_name1 = (TextView) marker.findViewById(R.id.no);
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
