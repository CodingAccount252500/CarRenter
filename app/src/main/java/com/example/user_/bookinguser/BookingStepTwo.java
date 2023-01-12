package com.example.user_.bookinguser;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingStepTwo extends AppCompatActivity {

    TextView name,phone, idPerson;
    Button booking;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference,databaseReference1;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_step_two);
        progressDialog = new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        name=(TextView)findViewById(R.id.name);
        phone=(TextView)findViewById(R.id.phone);
        idPerson =(TextView)findViewById(R.id.idpersons);
        booking=(Button) findViewById(R.id.bookingcar);

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("")){
                    name.setError("Please enter your name");
                }else  if(phone.getText().toString().equals("") || phone.getText().toString().length() != 10){
                    phone.setError("Please enter your phone");
                }else  if(idPerson.getText().toString().equals("")){
                    idPerson.setError("Please enter your id");
                }else {
                    final Dialog dialog = new Dialog(BookingStepTwo.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog);
                    final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.cbox);
                    final Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_okay);
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(checkBox.isChecked()){
                                dialogBtn_okay.setEnabled(true);
                                dialogBtn_okay.setBackgroundColor(Color.parseColor("#ED683D"));

                            }else {
                                dialogBtn_okay.setBackgroundColor(Color.GRAY);
                                dialogBtn_okay.setEnabled(false);
                            }
                        }
                    });



                    Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                    dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseReference1 = FirebaseDatabase.getInstance().getReference("BookingCarTable");
                            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    progressDialog.setMessage("Please Wait...");
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                //ma elo da3i

                                    if (dataSnapshot.exists()){
                                        for (DataSnapshot data:dataSnapshot.getChildren()){
                                            for (DataSnapshot data1:dataSnapshot.getChildren()) {


                                                if (data1.child(BokkongStepOne.id).exists()) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingStepTwo.this);
                                                    builder.setTitle("Warning ");
                                                    builder.setMessage("Sorry, This is car is not available.");
                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            finish();

                                                            Intent intent=new Intent(BookingStepTwo.this,MainActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                                    Dialog alertDialog = builder.create();
                                                    alertDialog.setCanceledOnTouchOutside(false);
                                                    alertDialog.show();
                                                    progressDialog.dismiss();
                                                } else {
                                                    ListModel list=new ListModel(BokkongStepOne.fdate.getText().toString(),
                                                            BokkongStepOne.ldate.getText().toString(),
                                                            BokkongStepOne.ftime.getText().toString(),BokkongStepOne.ltime.getText().toString()
                                                            ,BokkongStepOne.covers.getText().toString(),
                                                            BokkongStepOne.totalprice.getText().toString(),BokkongStepOne.chosen.getText().toString(),
                                                            MapsActivity.yourlocation,name.getText().toString(),phone.getText().toString()
                                                            , idPerson.getText().toString(),BokkongStepOne.id,user.getUid());
                                                    databaseReference.child("BookingCarTable").child(user.getUid()).child(BokkongStepOne.id).setValue(list);
                                                    finish();
                                                    startActivity(new Intent(BookingStepTwo.this,MainActivity.class));
                                                    dialog.dismiss();
                                                    progressDialog.dismiss();

                                                }
                                            }}

                                    }
                                    else{
                                        ListModel list=new ListModel(BokkongStepOne.fdate.getText().toString(),
                                                BokkongStepOne.ldate.getText().toString(),
                                                BokkongStepOne.ftime.getText().toString(),BokkongStepOne.ltime.getText().toString()
                                                ,BokkongStepOne.covers.getText().toString(),
                                                BokkongStepOne.totalprice.getText().toString(),BokkongStepOne.chosen.getText().toString(),
                                                MapsActivity.yourlocation,name.getText().toString(),phone.getText().toString()
                                                , idPerson.getText().toString(),BokkongStepOne.id,user.getUid());
                                        databaseReference.child("BookingCarTable").child(user.getUid()).child(BokkongStepOne.id).setValue(list);
                                        finishAffinity();
                                        startActivity(new Intent(BookingStepTwo.this,MainActivity.class));
                                        dialog.dismiss();
                                        progressDialog.dismiss();
                                    }}
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    });

                    dialog.show();


                }

            }
        });





    }
}
