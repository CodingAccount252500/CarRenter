package com.example.user_.bookinguser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomToMyCardActivity extends Activity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcom_to_my_card);
        firebaseAuth = FirebaseAuth.getInstance();
 if(firebaseAuth.getCurrentUser() != null) {
            finish();

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        Button button1=(Button)findViewById(R.id.btnreg);

        TextView textView=(TextView)findViewById(R.id.txtLogIn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(WelcomToMyCardActivity.this,RegistarActivity.class);
                startActivity(intent);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(WelcomToMyCardActivity.this,SigInActivity.class);
                startActivity(intent);
            }
        });
    }

}
