package com.example.user_.bookinguser;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BokkongStepOne extends AppCompatActivity {

    DatabaseReference databaseReference;
    private StorageReference mStorage;
    StorageReference filepath;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    static EditText fdate,ldate,ftime,ltime;
    static TextView chosen ,basic,covers,protication,totalprice;
    Calendar myCalendar;
    Button aboutConvers,stepone,mychosen;
    CheckBox r1,r2,r3,r4,r5;
    String namecar,  modelcar,  numperson,  numdoor,  typecar,  conditon,  numbag,image;
    int priceBooking,p;
    static String id;
    static int coversprice, accident,risk, scdw, sthw;
    Context context;
    Date date4,date2,date1;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bokkong_step_one);
        final Intent intent=getIntent();
        id = intent.getStringExtra("id");
        namecar = intent.getStringExtra("name");
        modelcar = intent.getStringExtra("model");
        priceBooking = Integer.parseInt(intent.getStringExtra("price"));
        numperson = intent.getStringExtra("person");
        numdoor = intent.getStringExtra("door");
        numbag = intent.getStringExtra("bag");
        conditon = intent.getStringExtra("cond");
        image = intent.getStringExtra("image");
        typecar = intent.getStringExtra("type");


        myCalendar = Calendar.getInstance();
        final TextView name = (TextView) findViewById(R.id.name);
        final TextView model = (TextView) findViewById(R.id.model);
        final TextView door = (TextView) findViewById(R.id.door);
        TextView person = (TextView) findViewById(R.id.person);
        final TextView type = (TextView) findViewById(R.id.type);
        final TextView bag = (TextView) findViewById(R.id.bag);
        final TextView price = (TextView) findViewById(R.id.price);
        final TextView condition = (TextView) findViewById(R.id.condition);

        totalprice = (TextView) findViewById(R.id.totalprice);
        covers = (TextView) findViewById(R.id.covers);
        chosen = (TextView) findViewById(R.id.chhosen);
        basic = (TextView) findViewById(R.id.basic);

        ImageView circleImageView = (ImageView) findViewById(R.id.profile_image);
        Picasso.with(this).load(Uri.parse(image))
                .into(circleImageView);


        name.setText(namecar);
        model.setText(modelcar);
        price.setText(priceBooking + " JD for each day");
        person.setText("Number of person "+numperson);
        door.setText("Number of door "+numdoor);
        type.setText("Transmission type "+conditon);
        condition.setText("Support condition "+typecar);
        bag.setText("Number of bag  "+numbag);
        basic.setText(priceBooking+"JD");
        totalprice.setText(priceBooking+ accident + risk +coversprice+ sthw + scdw +"JD");
        fdate = (EditText) findViewById(R.id.Fdate);
        ldate = (EditText) findViewById(R.id.Ldate1);
        ftime = (EditText) findViewById(R.id.Ftime);
        ltime = (EditText) findViewById(R.id.Ltime);
        stepone = (Button) findViewById(R.id.stepone);


        r1=(CheckBox)findViewById(R.id.radio_ninja);
        r2=(CheckBox)findViewById(R.id.radio_ninjas);
        r3=(CheckBox)findViewById(R.id.radio_pirates);
        r4=(CheckBox)findViewById(R.id.additinaldriver);
        r5=(CheckBox)findViewById(R.id.youngdriver);
        context=this;
        aboutConvers = (Button) findViewById(R.id.aboutConvers);
        aboutConvers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r1.getVisibility()==View.GONE){
                    r1.setVisibility(View.VISIBLE);
                    r3.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.VISIBLE);
                    aboutConvers.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                    aboutConvers.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                }else {
                    r1.setVisibility(View.GONE);
                    r3.setVisibility(View.GONE);
                    r2.setVisibility(View.GONE);
                    aboutConvers.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                    aboutConvers.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                }

            }
        });

        mychosen = (Button) findViewById(R.id.mychosenoption);
        mychosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(r5.getVisibility()==View.GONE){
                    r5.setVisibility(View.VISIBLE);
                    r4.setVisibility(View.VISIBLE);
                    mychosen.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                    mychosen.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                }else {
                    r5.setVisibility(View.GONE);
                    r4.setVisibility(View.GONE);
                    mychosen.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                    mychosen.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                }

            }
        });



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Fdate();
            }

        };

        final DatePickerDialog.OnDateSetListener Lastdate = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Ldate();
            }

        };

        fdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(BokkongStepOne.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ldate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(BokkongStepOne.this, Lastdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ftime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTime(context,ftime);
            }
        });
        ltime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTime(context,ltime);
            }
        });



        stepone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yy h:m a");
                String currentDate = formatter1.format(calendar1.getTime());
                try {
                    date1=new SimpleDateFormat("MM/dd/yy h:m a").parse(fdate.getText().toString()+" "+ftime.getText().toString());
                    date4=new SimpleDateFormat("MM/dd/yy h:m a").parse(currentDate);
                    date2=new SimpleDateFormat("MM/dd/yy h:m a").parse(ldate.getText().toString()+" "+ltime.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
                if(Objects.equals(fdate.getText().toString(), "")){
                    fdate.setError("please enter first date");
                }else if(ldate.getText().toString().equals("")) {
                    ldate.setError("please enter last date");
                }else if(Objects.equals(ftime.getText().toString(), "")){
                    fdate.setError("please enter first time");

                }else if ( ftime.getText().toString().equals("12")  ){
                    Toast.makeText(context, "please put the time before the 12am oclock", Toast.LENGTH_SHORT).show();
                    }
                else if(ltime.getText().toString().equals("")) {
                    ldate.setError("please enter last time");
                }else{
                    if(date1.before(date4)){
                        Toast.makeText(context, "Please Check from first date and first time", Toast.LENGTH_SHORT).show();
                    }else if(date2.before(date4)){
                        Toast.makeText(context, "Please Check from last date and last time", Toast.LENGTH_SHORT).show();

                    }else if(date2.before(date1)){
                    Toast.makeText(context, "Please Check from last date and last time", Toast.LENGTH_SHORT).show();
                }else {
                        startActivity(new Intent(BokkongStepOne.this, MapsActivity.class));
                    }


                } }
        });

    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked){
                    coversprice=10;
                    covers.setText(accident + risk +coversprice+"JD");
                    totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");
                    break;
                }
                else{
                    coversprice=0;
                    covers.setText(accident + risk +coversprice+"JD");
                    totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");
                    break;
                }
            case R.id.radio_ninjas:
                if (checked){
                    risk =10;
                    covers.setText(accident + risk +coversprice+"JD");
                    totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");
                    break;
                }
                else{
                    risk =0;
                    covers.setText(accident + risk +coversprice+"JD");
                    totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");
                    break;
                }
            case R.id.radio_ninja:
                if (checked){
                    accident =10;
                    covers.setText(accident + risk +coversprice+"JD");
                    totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");
                    break;
                }
                else{
                    accident =0;
                    covers.setText(accident + risk +coversprice+"JD");
                    totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");
                    break;
                }
            case R.id.additinaldriver:
                if (checked){
                    sthw =10 ;
                    chosen.setText(sthw + scdw +"JD");
                    totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");
                    break;
                }
                else{
                    sthw =0;
                    chosen.setText(sthw + scdw +"JD");
                    totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");
                    break;
                }
            case R.id.youngdriver:
                if (checked){
                    scdw =10;
                    chosen.setText(sthw + scdw +"JD");
                    totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");

                    break;
                }
                else{
                    scdw =0;
                    chosen.setText(sthw + scdw +"JD");
                    totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");
                    break;
                }
              }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void showTime(final Context context, final TextView textView) {

        final Calendar myCalendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String am_pm = "";
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                if (myCalendar.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (myCalendar.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";
                String strHrsToShow = (myCalendar.get(Calendar.HOUR) == 0) ? "12" : myCalendar.get(Calendar.HOUR) + "";
                textView.setText(strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
            }
        };
        new TimePickerDialog(context, mTimeSetListener, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), false).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Ldate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ldate.setText(sdf.format(myCalendar.getTime()));
        long d=Date.parse(ldate.getText().toString())-Date.parse(fdate.getText().toString());
        long days=TimeUnit.DAYS.convert(d, TimeUnit.MILLISECONDS);
        if(days==0){
            p= (int) (priceBooking*1);
        }else {
            p= (int) (priceBooking*days);
        }
        basic.setText(p+"JD");
        totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Fdate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fdate.setText(sdf.format(myCalendar.getTime()));
        if(!ldate.getText().toString().equals("")){
            long d=Date.parse(ldate.getText().toString())-Date.parse(fdate.getText().toString());
            long days=TimeUnit.DAYS.convert(d, TimeUnit.MILLISECONDS);
            if(days==0){
                p= (int) (priceBooking*1);
            }else {
                p= (int) (priceBooking*days);
            }
            basic.setText(p+"JD");
            totalprice.setText(p+ accident + risk +coversprice+ sthw + scdw +"JD");
        }

    }





}

