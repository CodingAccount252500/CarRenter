package com.example.user_.bookinguser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPssword extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    EditText editText;
    Button button;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
           }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pssword);
        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        editText=(EditText)findViewById(R.id.email);
        button=(Button)findViewById(R.id.resetpass);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(editText.getText().toString().equals("")){
                    editText.setError("Please Enter Your Email");
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Snackbar.make(view,"Check For your E-mail",Snackbar.LENGTH_LONG).setAction("Action",null).show();

                                finish();
                                startActivity(new Intent(ForgetPssword.this,SigInActivity.class));

                            }else {
                                Snackbar.make(view,"Error with your email",Snackbar.LENGTH_LONG).setAction("Action",null).show();

                            }
                        }
                    });
                }
            }
        });


    }
}
