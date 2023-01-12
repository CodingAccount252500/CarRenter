package com.example.user_.bookinguser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SigInActivity extends Activity {

    String status;
    DatabaseReference databaseReference,databaseReference2;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    EditText Uemail,pass;
    //String statue;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(SigInActivity.this, WelcomToMyCardActivity.class);
        startActivity(intent);    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sig_in);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Button btn=(Button)findViewById(R.id.btnsignin);
        Uemail=(EditText) findViewById(R.id.editText4);
        pass=(EditText) findViewById(R.id.editText5);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        TextView textView=(TextView)findViewById(R.id.forgetpass);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SigInActivity.this,ForgetPssword.class));
            }
        });

        progressDialog = new ProgressDialog(this);



    }

    private void userLogin(){
        String email = Uemail.getText().toString().trim();
        String password  = pass.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }



        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                    user=firebaseAuth.getCurrentUser();
                                    databaseReference= FirebaseDatabase.getInstance().getReference("User").child(user.getUid());
                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            status= dataSnapshot.child("Statues").getValue().toString();
                                            if(status.equals("customer")) {
                                                finish();
                                                Intent intent=new Intent(SigInActivity.this, MainActivity.class);

                                                startActivity(intent);

                                            }
                                            else {
                                                Toast.makeText(SigInActivity.this,"Account not found ",Toast.LENGTH_LONG).show();

                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(SigInActivity.this,"Account not found ",Toast.LENGTH_LONG).show();

                                        }
                                    });


                                }
                                else {
                                    Toast.makeText(SigInActivity.this,"Email not verified",Toast.LENGTH_LONG).show();

                                }

                            }
                            else {
                                Toast.makeText(SigInActivity.this,"There was a proplem signing in ",Toast.LENGTH_LONG).show();

                            }

                        }
                    });


    }
}
