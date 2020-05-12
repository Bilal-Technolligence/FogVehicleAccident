package com.example.fogvehicleaccident;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class StartEndPoint extends AppCompatActivity {
    EditText from, to, no;
    Button start;
    String type;
    Spinner serviceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_end_point);
        from = findViewById(R.id.edtFrom);
        to = findViewById(R.id.edtTo);
        start = findViewById(R.id.btnStart);
        no = findViewById(R.id.edtNo);
        serviceSpinner = (Spinner) findViewById(R.id.spinnerType);
        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String From = from.getText().toString().toUpperCase();
                String To = to.getText().toString();
                String No = no.getText().toString();
                if (From.equals("")) {
                    from.setError("Enter Valid Address");
                    from.setFocusable(true);
                } else if (To.equals("")) {
                    to.setError("Enter Valid Address");
                    to.setFocusable(true);
                }else if (No.equals("")) {
                    no.setError("Enter Vehicle No.");
                    no.setFocusable(true);
                } else {
                    Intent intent = new Intent(StartEndPoint.this, MapsActivity.class);
                    intent.putExtra("from", From);
                    intent.putExtra("to", To);
                    intent.putExtra("no", No);
                    intent.putExtra("type", type);
                    startActivity(intent);
                }
            }
        });
    }
}
