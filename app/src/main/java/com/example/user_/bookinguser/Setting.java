package com.example.user_.bookinguser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Setting extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    TextView newpass;
    Button changepass;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actuvity_setting);
        firebaseAuth=FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        newpass=(TextView)findViewById(R.id.txtnewpass);
        changepass=(Button) findViewById(R.id.btnchangepass);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (newpass.getText().toString().equals("")){
                    newpass.setError("Please Enter New Password");
                }else {
                    if (user != null) {
                        user=firebaseAuth.getCurrentUser();
                        user.updatePassword(newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Setting.this, "Complete Edit", Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(new Intent(Setting.this,MainActivity.class));
                                } else {
                                    Toast.makeText(Setting.this, "Cannot Edit Password", Toast.LENGTH_LONG).show();


                                }
                            }
                        });
                    }
                }

            }
        });




    }
}




