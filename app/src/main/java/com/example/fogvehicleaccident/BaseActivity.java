package com.example.fogvehicleaccident;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ImageView imageView;
    TextView name,gmail;
    String eName,eImage;
    ActionBarDrawerToggle drawerToggle;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawarLayout);


        //adding drawar button
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        //navbar item click
        NavigationView navigationView = (NavigationView) findViewById(R.id.drawerNavigationView);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //header click navbar
        View headerview = navigationView.getHeaderView(0);
        imageView = (ImageView) headerview.findViewById(R.id.profile_image);
        name = (TextView)headerview.findViewById( R.id.name );
        String userId =FirebaseAuth.getInstance().getCurrentUser().getUid();
        //  final String uid=userName;

        databaseReference.child( "Users" ).child(userId).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    eName = dataSnapshot.child( "name" ).getValue().toString();
                    name.setText(String.valueOf( eName ));
                    if(dataSnapshot.child( "imageUrl" ).getValue().toString().equals( " " )) {
                        Picasso.get().load( dataSnapshot.child( "imageUrl" ).getValue().toString() ).into( imageView );
                    }
                    else {
                        Picasso.get().load( dataSnapshot.child( "imageUrl" ).getValue().toString() ).into( imageView );

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    protected abstract int getContentViewId();
    //drawer open close click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home: {
                startActivity(new Intent(this,MainActivity.class));
                finish();

                break;
            }
            case R.id.profile: {
//                startActivity(new Intent(this,ProfileActivity.class));
//                finish();
                Snackbar.make(drawerLayout, "Module is under Development", Snackbar.LENGTH_LONG).show();

                break;
            }
//            case R.id.currentParking: {
//
//
//                final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//                databaseReference.child("Bookings").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            startActivity(new Intent(getApplicationContext(),EndCurrentParking.class));
//                            finish();
//                        }
//                        else{
//                            Snackbar.make(drawerLayout, "No record found", Snackbar.LENGTH_LONG).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//                //Snackbar.make(drawerLayout, "Currently you noting have to Park", Snackbar.LENGTH_LONG).show();
//
//                break;
//
//            }

            case R.id.history: {
//                startActivity(new Intent(this,HistoryActivity.class));
//                finish();
                Snackbar.make(drawerLayout, "Under Development", Snackbar.LENGTH_LONG).show();
                break;

            }
            case R.id.report: {
//                startActivity(new Intent(this,IssueActivity.class));
//                finish();
                Snackbar.make(drawerLayout, "This module is Under Development", Snackbar.LENGTH_LONG).show();
                break;

            }
            case R.id.logout: {
//                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                final String uid = currentUser.getUid();
//                if (uid != null)
//                    databaseReference.child("Active").child(uid).setValue(null);
                FirebaseAuth.getInstance().signOut();
                Save.save(getApplicationContext(),"session","false");
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;

            }
        }
        return true;
    }
}
