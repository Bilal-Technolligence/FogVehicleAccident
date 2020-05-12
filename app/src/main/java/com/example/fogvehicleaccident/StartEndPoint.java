package com.example.fogvehicleaccident;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartEndPoint extends AppCompatActivity {
EditText from ,to;
Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_end_point);
        from =findViewById(R.id.edtFrom);
        to = findViewById(R.id.edtTo);
        start = findViewById(R.id.btnStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String From = from.getText().toString().toUpperCase();
                String To = to.getText().toString();
                if (From.equals("")) {
                    from.setError("Enter Valid Address");
                    from.setFocusable(true);
                } else if (To.equals("")) {
                    to.setError("Enter Valid Address");
                    to.setFocusable(true);
                }
                else {
                    Intent intent = new Intent(StartEndPoint.this,StartEndPoint.class);
                    intent.putExtra( "from",From);
                    intent.putExtra( "to",To);

                    startActivity(intent);
                }
            }
        });
    }
}
