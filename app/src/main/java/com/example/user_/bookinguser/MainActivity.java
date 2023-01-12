package com.example.user_.bookinguser;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends TabActivity {

    TabHost TabHostWindow;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);
        TabHostWindow.setup();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("First tab");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Second Tab");
        Intent intent =getIntent();
        String vlaue=intent.getStringExtra("flag");

        TabMenu2.setIndicator("Garage");
        TabMenu1.setIndicator("My Booking");
        TabMenu1.setContent(new Intent(this,MyBooking.class));

        TabMenu2.setContent(new Intent(this,ListCars.class));



        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);

        if(Objects.equals(vlaue, "1")){
            TabHostWindow.setCurrentTab(1);
            TabHostWindow.getTabWidget().getChildTabViewAt(1);
        }else {
            TabHostWindow.getTabWidget().getChildTabViewAt(0);

        }


        FloatingActionButton logout = (FloatingActionButton) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this,LogoAvtivity.class));

            }
        });
        FloatingActionButton setting = (FloatingActionButton) findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Setting.class));
            }
        });
    }

}
