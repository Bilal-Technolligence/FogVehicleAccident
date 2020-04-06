package com.example.fogvehicleaccident;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private Button btnLogin, btnLogin2, btnSignup;
    TextView btnRecoverPass;
    EditText email,password;
    ProgressDialog progressDialog;
    private String selection;
    String category;


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure want to exit?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin2 = (Button) findViewById(R.id.button3);
        btnRecoverPass = findViewById(R.id.recoverpasswordtv);
        btnSignup = (Button) findViewById(R.id.btnSignup);
       // final String arr[] = getResources().getStringArray(R.array.selection);
        password = (EditText) findViewById(R.id.editText2);
        email=(EditText) findViewById(R.id.editText);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging In..... ");
        final FirbaseAuthenticationClass firbaseAuthenticationClass=new FirbaseAuthenticationClass();
        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EMAIL = email.getText().toString().trim();
                String PASSWORD = password.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
                    email.setError("Invalid email");
                    email.setFocusable(true);
                } else {
                    progressDialog.show();
                    firbaseAuthenticationClass.LoginUser(EMAIL, PASSWORD, LoginActivity.this, progressDialog);

                }
            }
        });
        btnRecoverPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetPassword.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                            startActivity(intent);
                        //    finish();
            }
        });
    }


}
