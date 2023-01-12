package com.example.user_.bookinguser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MyBooking extends Activity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference,databaseReference1;
    MyCustomAdapter myadpter;
    GridView ls;
    TextView nodata;
    EditText fdate,ldate,ftime,ltime;
    Button btnupdate;
    Calendar myCalendar;
    String pricenew="";
    ProgressDialog progressDialog;
    int r=0;
    SwipeRefreshLayout mSwipeRefreshLayout;

    final ArrayList<ListBooking> Items = new ArrayList<ListBooking>();
    ListItem ItemsCar ;

    ListItem l;

    Context context;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog.setCanceledOnTouchOutside(false);
        finish();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        ls = (GridView) findViewById(R.id.listview);
         mSwipeRefreshLayout=findViewById(R.id.refresh);
        myadpter = new MyCustomAdapter(this,Items);
        nodata= (TextView) findViewById(R.id.nodata);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading,Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        databaseReference = FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid());
        progressDialog.show();
        myCalendar = Calendar.getInstance();
        context=this;
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Items.clear();
                ls.setAdapter(null);
                fetchData();

            }
        });
        progressDialog.dismiss();

        ItemsCar=new ListItem();
        fetchData();
    }


    public  void fetchData(){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if(dataSnapshot1.exists()){
                    Items.clear();
                    for (final DataSnapshot dataSnapshot : dataSnapshot1.getChildren()){
                        databaseReference1 = FirebaseDatabase.getInstance().getReference("carage")
                                .child(dataSnapshot.child("IDCar").getValue().toString());

                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot2) {
                                int c=0;
                                ItemsCar.id=dataSnapshot2.child("id").getValue().toString();
                                ItemsCar.name=dataSnapshot2.child("name").getValue().toString();
                                ItemsCar.model= dataSnapshot2.child("model").getValue().toString();
                                ItemsCar.price=dataSnapshot2.child("price").getValue().toString();
                                ItemsCar.image=dataSnapshot2.child("image").getValue().toString();

                                Items.add(new ListBooking(dataSnapshot.child("Fdate").getValue().toString(),
                                        dataSnapshot.child("Ldate").getValue().toString(),
                                        dataSnapshot.child("Ftime").getValue().toString(),
                                        dataSnapshot.child("Ltime").getValue().toString(),
                                        dataSnapshot.child("Totalprice").getValue().toString(),
                                        dataSnapshot.child("Locations").getValue().toString(),
                                        dataSnapshot.child("Covers").getValue().toString(),
                                        dataSnapshot.child("Mychosen").getValue().toString(),
                                        dataSnapshot.child("IDCar").getValue().toString(),
                                        dataSnapshot2.child("id").getValue().toString(),
                                        dataSnapshot2.child("name").getValue().toString(),
                                        dataSnapshot2.child("model").getValue().toString(),
                                        dataSnapshot2.child("price").getValue().toString(),
                                        dataSnapshot2.child("numperson").getValue().toString(),
                                        dataSnapshot2.child("numbag").getValue().toString(),
                                        dataSnapshot2.child("numdoor").getValue().toString(),
                                        dataSnapshot2.child("type").getValue().toString(),
                                        dataSnapshot2.child("conditon").getValue().toString(),
                                        dataSnapshot2.child("image").getValue().toString(),
                                        dataSnapshot.child("Name").getValue().toString(),
                                        dataSnapshot.child("ID").getValue().toString(),
                                        dataSnapshot.child("Phone").getValue().toString()


                                ));
                                ls.setAdapter(myadpter);
                                myadpter.notifyDataSetChanged();
                                progressDialog.dismiss();
                                mSwipeRefreshLayout.setRefreshing(false);
                                c++;

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }


                }else{
                    nodata.setVisibility(View.VISIBLE);
                    ls.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    mSwipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        progressDialog.dismiss();

    }

    class MyCustomAdapter extends BaseAdapter {
        ArrayList<ListBooking> Items = new ArrayList<ListBooking>();

        DatabaseReference removedata;
        FirebaseAuth firebaseAuth;
        Context c;


        public MyCustomAdapter(Context c, ArrayList<ListBooking> items) {
            Items=items;
            this.c=c;

        }


        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public String getItem(int position) {
            return Items.get(position).IDCar;

        }



        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            progressDialog.dismiss();

            LayoutInflater c1=(LayoutInflater)c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
            firebaseAuth = FirebaseAuth.getInstance();
            View view1=c1.inflate(R.layout.listcustom,viewGroup,false);

            final TextView name = (TextView) view1.findViewById(R.id.name);
            final TextView model = (TextView) view1.findViewById(R.id.model);
            final TextView door = (TextView) view1.findViewById(R.id.door);
            TextView person = (TextView) view1.findViewById(R.id.person);
            final TextView type = (TextView) view1.findViewById(R.id.type);
            final TextView bag = (TextView) view1.findViewById(R.id.bag);
            final TextView price = (TextView) view1.findViewById(R.id.price);
            final TextView condition = (TextView) view1.findViewById(R.id.condition);
            ImageView circleImageView = (ImageView) view1.findViewById(R.id.profile_image);
            Button buttondelet=(Button)view1.findViewById(R.id.removebooking);
            buttondelet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setTitle("Warning ");
                    builder.setMessage("Are you sure you want to delete this car?");
                    builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int ii) {
                            removedata = FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid()).child(Items.get(i).IDCar);
                            removedata.removeValue();
                            myadpter.remove(i);
                            myadpter.notifyDataSetChanged();
                            Toast.makeText(c, "Successfully deleted", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    Dialog alertDialog =builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();

                }
            });

            Button buttoneditelocation=(Button)view1.findViewById(R.id.editelocation);
            buttoneditelocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MyBooking.this,MapsActivityEdite.class);
                    intent.putExtra("idcar",Items.get(i).IDCar);
                    startActivity(intent);
                }
            });

            Button buttoneditedate=(Button)view1.findViewById(R.id.editedate);
            buttoneditedate.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(MyBooking.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialogeditedate);
                    fdate = (EditText) dialog.findViewById(R.id.Fdate);
                    ldate = (EditText) dialog.findViewById(R.id.Ldate1);
                    ftime = (EditText) dialog.findViewById(R.id.Ftime);
                    ltime = (EditText) dialog.findViewById(R.id.Ltime);
                    fdate.setText(Items.get(i).Fdate);
                    ldate.setText(Items.get(i).Ldate);
                    ftime.setText(Items.get(i).Ftime);
                    ltime.setText(Items.get(i).Ltime);

                    btnupdate = (Button) dialog.findViewById(R.id.btnupdate);

                    btnupdate.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View v) {
                            long d=Date.parse(ldate.getText().toString())-Date.parse(fdate.getText().toString());
                            long days=TimeUnit.DAYS.convert(d, TimeUnit.MILLISECONDS);
                            long dlast=Date.parse(Items.get(i).Ldate)-Date.parse(Items.get(i).Fdate);
                            long daysnews=TimeUnit.DAYS.convert(dlast, TimeUnit.MILLISECONDS);
                            int basicprice;
                            Date date1 = null,date2= null,date4 = null,date3=null;
                            Calendar calendar1 = Calendar.getInstance();
                            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yy h:m a");
                            String currentDate = formatter1.format(calendar1.getTime());
                            try {
                                date4=new SimpleDateFormat("MM/dd/yy h:m a").parse(currentDate);
                                date2=new SimpleDateFormat("MM/dd/yy h:m a").parse(ldate.getText().toString()+" "+ltime.getText().toString());
                                date1=new SimpleDateFormat("MM/dd/yy h:m a").parse(fdate.getText().toString()+" "+ftime.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if(fdate.isEnabled()){
                                if(date1.before(date4)){
                                    Toast.makeText(context, "Please Check from first date and first time", Toast.LENGTH_SHORT).show();
                                }else if(date2.before(date4)){
                                    Toast.makeText(context, "Please Check from last date and last time", Toast.LENGTH_SHORT).show();
                                }else if(date2.equals(date1)){
                                    Toast.makeText(context, "Please Check  date and  time", Toast.LENGTH_SHORT).show();
                                }else {

                                    if(days==0){

                                        int p=Integer.parseInt(Items.get(i).Totalprice.substring(0,Items.get(i).Totalprice.length()-2))
                                                - Integer.parseInt(Items.get(i).Covers.substring(0,Items.get(i).Covers.length()-2))
                                                -Integer.parseInt(Items.get(i).Mychosen.substring(0,Items.get(i).Mychosen.length()-2));
                                        basicprice=p/Integer.parseInt(String.valueOf(1));

                                        pricenew= String.valueOf(Integer.parseInt(Items.get(i).Mychosen.substring(0,Items.get(i).Mychosen.length()-2))+
                                                Integer.parseInt(Items.get(i).Covers.substring(0,Items.get(i).Covers.length()-2))
                                                +(int) (basicprice*1))+"JD";

                                        if(Objects.equals(pricenew, "")){
                                            FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                    .child(Items.get(i).IDCar).child("Totalprice").setValue(Items.get(i).Totalprice);
                                        }else{
                                            FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                    .child(Items.get(i).IDCar).child("Totalprice").setValue(pricenew);
                                        }
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Fdate").setValue(fdate.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ldate").setValue(ldate.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ftime").setValue(ftime.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ltime").setValue(ltime.getText().toString());
                                        Toast.makeText(MyBooking.this, "Succssfule Update", Toast.LENGTH_SHORT).show();
                                        myadpter.notifyDataSetChanged();
                                        ls.setAdapter(myadpter);
                                        dialog.dismiss();

                                    }else {

                                        int p=Integer.parseInt(Items.get(i).Totalprice.substring(0,Items.get(i).Totalprice.length()-2))
                                                - Integer.parseInt(Items.get(i).Covers.substring(0,Items.get(i).Covers.length()-2))
                                                -Integer.parseInt(Items.get(i).Mychosen.substring(0,Items.get(i).Mychosen.length()-2));
                                        if(daysnews==0){
                                            basicprice=p/Integer.parseInt(String.valueOf(1));

                                        }else{
                                            basicprice=p/Integer.parseInt(String.valueOf(daysnews));

                                        }

                                        pricenew= String.valueOf(Integer.parseInt(Items.get(i).Mychosen.substring(0,Items.get(i).Mychosen.length()-2)) +
                                                Integer.parseInt(Items.get(i).Covers.substring(0,Items.get(i).Covers.length()-2))
                                                +(int) (basicprice*days))+"JD";
                                        if(Objects.equals(pricenew, "")){
                                            FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                    .child(Items.get(i).IDCar).child("Totalprice").setValue(Items.get(i).Totalprice);
                                        }else{
                                            FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                    .child(Items.get(i).IDCar).child("Totalprice").setValue(pricenew);
                                        }
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Fdate").setValue(fdate.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ldate").setValue(ldate.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ftime").setValue(ftime.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ltime").setValue(ltime.getText().toString());
                                        Toast.makeText(MyBooking.this, "Succssfule Update", Toast.LENGTH_SHORT).show();
                                        myadpter.notifyDataSetChanged();
                                        ls.setAdapter(myadpter);
                                        dialog.dismiss();
                                    }
                                }

                            }else {
                                if(date2.before(date4)){
                                    Toast.makeText(context, "Please Check from last date and last time", Toast.LENGTH_SHORT).show();
                                }else if(date2.equals(date1)){
                                    Toast.makeText(context, "Please Check  date and  time", Toast.LENGTH_SHORT).show();
                                }else {

                                    if(days==0){

                                        int p=Integer.parseInt(Items.get(i).Totalprice.substring(0,Items.get(i).Totalprice.length()-2))
                                                - Integer.parseInt(Items.get(i).Covers.substring(0,Items.get(i).Covers.length()-2))
                                                -Integer.parseInt(Items.get(i).Mychosen.substring(0,Items.get(i).Mychosen.length()-2));
                                        basicprice=p/Integer.parseInt(String.valueOf(1));

                                        pricenew= String.valueOf(Integer.parseInt(Items.get(i).Mychosen.substring(0,Items.get(i).Mychosen.length()-2))+
                                                Integer.parseInt(Items.get(i).Covers.substring(0,Items.get(i).Covers.length()-2))
                                                +(int) (basicprice*1))+"JD";

                                        if(Objects.equals(pricenew, "")){
                                            FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                    .child(Items.get(i).IDCar).child("Totalprice").setValue(Items.get(i).Totalprice);
                                        }else{
                                            FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                    .child(Items.get(i).IDCar).child("Totalprice").setValue(pricenew);
                                        }
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Fdate").setValue(fdate.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ldate").setValue(ldate.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ftime").setValue(ftime.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ltime").setValue(ltime.getText().toString());
                                        Toast.makeText(MyBooking.this, "Succssfule Update", Toast.LENGTH_SHORT).show();
                                        myadpter.notifyDataSetChanged();
                                        ls.setAdapter(myadpter);
                                        dialog.dismiss();

                                    }else {

                                        int p=Integer.parseInt(Items.get(i).Totalprice.substring(0,Items.get(i).Totalprice.length()-2))
                                                - Integer.parseInt(Items.get(i).Covers.substring(0,Items.get(i).Covers.length()-2))
                                                -Integer.parseInt(Items.get(i).Mychosen.substring(0,Items.get(i).Mychosen.length()-2));
                                        if(daysnews==0){
                                            basicprice=p/Integer.parseInt(String.valueOf(1));

                                        }else{
                                            basicprice=p/Integer.parseInt(String.valueOf(daysnews));

                                        }

                                        pricenew= String.valueOf(Integer.parseInt(Items.get(i).Mychosen.substring(0,Items.get(i).Mychosen.length()-2))+
                                                Integer.parseInt(Items.get(i).Covers.substring(0,Items.get(i).Covers.length()-2))
                                                +(int) (basicprice*days))+"JD";
                                        if(Objects.equals(pricenew, "")){
                                            FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                    .child(Items.get(i).IDCar).child("Totalprice").setValue(Items.get(i).Totalprice);
                                        }else{
                                            FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                    .child(Items.get(i).IDCar).child("Totalprice").setValue(pricenew);
                                        }
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Fdate").setValue(fdate.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ldate").setValue(ldate.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ftime").setValue(ftime.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("BookingCarTable").child(user.getUid())
                                                .child(Items.get(i).IDCar).child("Ltime").setValue(ltime.getText().toString());
                                        Toast.makeText(MyBooking.this, "Succssfule Update", Toast.LENGTH_SHORT).show();
                                        myadpter.notifyDataSetChanged();
                                        ls.setAdapter(myadpter);
                                        dialog.dismiss();
                                    }
                                }

                            }

                        }
                    });

                    Button btncancel = (Button) dialog.findViewById(R.id.btncancel);

                    btncancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

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

                    Calendar calendar1 = Calendar.getInstance();
                    SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yy h:m a");
                    String currentDate = formatter1.format(calendar1.getTime());
                    Date date1= null;
                    try {
                        Date date7=new SimpleDateFormat("MM/dd/yy h:m a").parse(currentDate);
                        Date date8 = new SimpleDateFormat("MM/dd/yy h:m a").parse(Items.get(i).Fdate+" "+Items.get(i).Ftime);
                        if(date8.after(date7)){
                            fdate.setEnabled(true);
                            ftime.setEnabled(true);
                        }else{
                            fdate.setEnabled(false);
                            ftime.setEnabled(false);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }




                    fdate.setOnClickListener(new View.OnClickListener() {

                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(MyBooking.this, date, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                        }
                    });

                    ldate.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(MyBooking.this, Lastdate, myCalendar
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


                    dialog.show();
                }
            });


            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(MyBooking.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.customshow);
                    final TextView fdate = (TextView) dialog.findViewById(R.id.fdate);
                    final TextView ldate1 = (TextView) dialog.findViewById(R.id.ldate);
                    final TextView ftime = (TextView) dialog.findViewById(R.id.ftime);
                    final TextView personname = (TextView) dialog.findViewById(R.id.nameperson);
                    final TextView idnaio = (TextView) dialog.findViewById(R.id.idnationality);
                    final TextView phone = (TextView) dialog.findViewById(R.id.NPhone);
                    final TextView ltime = (TextView) dialog.findViewById(R.id.ltime);
                    final TextView location = (TextView) dialog.findViewById(R.id.location);
                    final TextView Tprice = (TextView) dialog.findViewById(R.id.totalprice);
                    final LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.linear);

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    fdate.setText("Start date:"+Items.get(i).Fdate);
                    ldate1.setText("End date:"+Items.get(i).Ldate);
                    ftime.setText("Start time:"+Items.get(i).Ftime);
                    ltime.setText("End time:"+Items.get(i).Ltime);
                    location.setText("Location:"+Items.get(i).Locations);
                    Tprice.setText("Total price:"+Items.get(i).Totalprice);
                    personname.setText("name:"+Items.get(i).Name);
                    idnaio.setText("id:"+Items.get(i).ID);
                    phone.setText("phone:"+Items.get(i).Phone);

                    dialog.show();
                }

            });
            Picasso.with(c).load(Uri.parse(Items.get(i).image))
                    .into(circleImageView);

            name.setText(Items.get(i).name);
            model.setText(Items.get(i).model);
            price.setText(Items.get(i).price + " JD for each day");
            person.setText("Number of person "+Items.get(i).numperson);
            door.setText("Number of bags "+Items.get(i).numdoor);
            type.setText("Transmission type "+Items.get(i).conditon);
            condition.setText("Support condition "+Items.get(i).type);
            bag.setText("Number of doors  "+Items.get(i).numbag);





            return view1;

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public  void showTime(final Context context, final TextView textView) {

            final Calendar myCalendar = Calendar.getInstance();
            TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String am_pm = "";
                    myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalendar.set(Calendar.MINUTE, minute);
                    if (myCalendar.get(Calendar.AM_PM) == Calendar.AM)
                        am_pm = "am";
                    else if (myCalendar.get(Calendar.AM_PM) == Calendar.PM)
                        am_pm = "pm";
                    String strHrsToShow = (myCalendar.get(Calendar.HOUR) == 0) ? "12" : myCalendar.get(Calendar.HOUR) + "";
                    textView.setText(strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
                }
            };
            new TimePickerDialog(context, mTimeSetListener, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), false).show();
        }


        public void remove (int id){
            Items.remove(id);
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        private void Ldate() {
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            ldate.setText(sdf.format(myCalendar.getTime()));





        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private void Fdate() {
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            fdate.setText(sdf.format(myCalendar.getTime()));
        }
    }


}