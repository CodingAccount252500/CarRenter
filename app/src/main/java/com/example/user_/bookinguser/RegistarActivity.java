package com.example.user_.bookinguser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistarActivity extends Activity {

    private ProgressDialog progressDialog;
    DatabaseReference databaseReference,databaseReference2;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Button buttonSignup;
    EditText editTextEmail,editTextPassword;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        Intent intent = new Intent(RegistarActivity.this,WelcomToMyCardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registar);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        databaseReference= FirebaseDatabase.getInstance().getReference();

        editTextEmail = (EditText) findViewById(R.id.edemail);
        editTextPassword = (EditText) findViewById(R.id.edpass);
        buttonSignup=(Button)findViewById(R.id.btnSignup);


        progressDialog = new ProgressDialog(this);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser(){


        final String email = editTextEmail.getText().toString().trim();
        final String password  = editTextPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user=firebaseAuth.getCurrentUser();
                            databaseReference2=FirebaseDatabase.getInstance().getReference("User").child(user.getUid());
                            databaseReference2.child("Statues").setValue("customer");
                            databaseReference2.child("email").setValue(email);
                            databaseReference2.child("fdate").setValue(user.getUid());
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegistarActivity.this, "Please Check your email to verify your account ", Toast.LENGTH_LONG).show();

                                    }else {
                                        Toast.makeText(RegistarActivity.this, "Can not send your email,make sure it is entered correctly", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                            firebaseAuth.signOut();
                            finish();
                            Intent intent=new Intent(RegistarActivity.this,WelcomToMyCardActivity.class);
                            startActivity(intent);
                        }else{
                            Log.d("1",task.getException().toString());
                            Toast.makeText(RegistarActivity.this,"be sure to enter email correctly",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }





}