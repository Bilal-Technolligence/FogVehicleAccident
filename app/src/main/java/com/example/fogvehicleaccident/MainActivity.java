package com.example.fogvehicleaccident;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Save.save(getApplicationContext(),"session","false");
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
}
