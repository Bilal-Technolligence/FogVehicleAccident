package com.example.fogvehicleaccident;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button btnLogin, btnSignup;
    EditText txtName, txtEmail, txtPassword, rePass;
    ProgressDialog progressDialog;
    String category;
    private String selection;
    TextView mHaveAccountTv;
    ProgressDialog pd;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference = database.getReference("Users");

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.txtSignUp);
        txtEmail = (EditText) findViewById(R.id.editTextEmail);
        txtPassword = (EditText) findViewById(R.id.editTextPassword);
        txtName = (EditText) findViewById(R.id.editTextName);
        rePass = (EditText) findViewById(R.id.editTextRePassword);
        mHaveAccountTv = findViewById(R.id.have_accounttv);
        progressDialog = new ProgressDialog(this);
        pd = new ProgressDialog(this);
        pd.setMessage("Signing In..... ");
      //  Intent in = getIntent();
       // category = in.getStringExtra("name");
        mHaveAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String name = txtName.getText().toString();
                String Phone = rePass.getText().toString();

                if (name == null || name.equals("")) {
                    txtName.setError("Please Fill Name");
                }
                if (Phone.equals("")) {
                    rePass.setError("Please Fill Password");
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    txtEmail.setError("Invalid Email");
                    txtEmail.setFocusable(true);

                } else if (password.length() < 6) {
                    txtPassword.setError("Pasword Length Must Be grater than 6 characters");
                    txtPassword.setFocusable(true);

                } else {

                        RegisterUser(email, password, " ", name, " ", " ", category, " ", " ", " ", " ", pd);

                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void RegisterUser(final String userGmail, String userPassword, final String contact, final String name, final String parkingName, final String parkingSpace, final String userCategory, final String imagePath, final String lati, final String loni, final String addressString, final ProgressDialog progressDialog) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(userGmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            UserAttr userAttr = new UserAttr();
                            userAttr.setEmail(userGmail);
                            userAttr.setContact(contact);
                            userAttr.setName(name);
                            userAttr.setAddress(addressString);
                            userAttr.setLatitude(lati);
                            userAttr.setLongitude(loni);
                            userAttr.setParkingName(parkingName);
                            userAttr.setParkingSpace(parkingSpace);
                            userAttr.setCategory(userCategory);
                            userAttr.setRating("0");
                            userAttr.setNumRating("0");
                            userAttr.setId(uid);
                            userAttr.setImageUrl(imagePath);
                            reference.child(uid).setValue(userAttr);
                            startActivity(new Intent(SignUpActivity.this, CompleteProfileActivity.class));
                            Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
//                                  getApplicationContext().finish();
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}
