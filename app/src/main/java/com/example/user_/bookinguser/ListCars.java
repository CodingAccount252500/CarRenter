

package com.example.user_.bookinguser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListCars extends Activity {
    DatabaseReference databaseReference,databaseReferenceCheck;
    MyCustomAdapter myadpter;
    GridView ls;
    TextView nodata;
    ProgressDialog progressDialog;
    int r=0;
    SwipeRefreshLayout mSwipeRefreshLayout;

    final ArrayList<ListItem> Items = new ArrayList<ListItem>();

    ListItem l;
    int i=0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.listcars);
        ls = (GridView) findViewById(R.id.listview);
        myadpter = new MyCustomAdapter(ListCars.this,Items);
        nodata= (TextView) findViewById(R.id.nodata);
        mSwipeRefreshLayout = findViewById(R.id.refresh);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading,Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        databaseReference = FirebaseDatabase.getInstance().getReference("carage");
        progressDialog.show();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
        fetchData();



    }
    public  void  fetchData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                if(dataSnapshot1.exists()){

                    Items.clear();

                    for (DataSnapshot dataSnapshot : dataSnapshot1.getChildren()){
                        Items.add(new ListItem(dataSnapshot.child("id").getValue().toString(),
                                dataSnapshot.child("name").getValue().toString(),
                                dataSnapshot.child("model").getValue().toString(),
                                dataSnapshot.child("price").getValue().toString(),
                                dataSnapshot.child("numperson").getValue().toString(),
                                dataSnapshot.child("numbag").getValue().toString(),
                                dataSnapshot.child("numdoor").getValue().toString(),
                                dataSnapshot.child("type").getValue().toString(),
                                dataSnapshot.child("conditon").getValue().toString(),
                                dataSnapshot.child("image").getValue().toString()));
                    }

                    ls.setAdapter(myadpter);
                    myadpter.notifyDataSetChanged();
                    progressDialog.dismiss();
                    mSwipeRefreshLayout.setRefreshing(false);
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
    }

    class MyCustomAdapter extends BaseAdapter {
        ArrayList<ListItem> Items = new ArrayList<ListItem>();
        DatabaseReference removedata;
        FirebaseAuth firebaseAuth;
        Context c;


        public MyCustomAdapter(Context c, ArrayList<ListItem> items) {
            Items=items;
            this.c=c;

        }


        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public String getItem(int position) {
            return Items.get(position).id;

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
            View view1=c1.inflate(R.layout.listcarscustom,viewGroup,false);
            final TextView name = (TextView) view1.findViewById(R.id.name);
            final TextView model = (TextView) view1.findViewById(R.id.model);
            final TextView door = (TextView) view1.findViewById(R.id.door);
            TextView person = (TextView) view1.findViewById(R.id.person);
            final TextView type = (TextView) view1.findViewById(R.id.type);
            final TextView bag = (TextView) view1.findViewById(R.id.bag);
            final TextView price = (TextView) view1.findViewById(R.id.price);
            final TextView condition = (TextView) view1.findViewById(R.id.condition);
            CardView constraintLayout=(CardView) view1.findViewById(R.id.backitem);
            ImageView circleImageView = (ImageView) view1.findViewById(R.id.profile_image);
            final Button booking = (Button) view1.findViewById(R.id.booking);

            Picasso.with(c).load(Uri.parse(Items.get(i).image)).into(circleImageView);

            name.setText(Items.get(i).name);
            model.setText(Items.get(i).model);
            price.setText(Items.get(i).price + " JD for each day");
            person.setText("Number of person "+Items.get(i).numperson);
            door.setText("Number of bags "+Items.get(i).numdoor);
            type.setText("Transmission type "+Items.get(i).conditon);
            condition.setText("Support condition "+Items.get(i).type);
            bag.setText("Number of doors  "+Items.get(i).numbag);
            databaseReferenceCheck = FirebaseDatabase.getInstance().getReference("BookingCarTable");
            databaseReferenceCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot data:dataSnapshot.getChildren()){
                            for (DataSnapshot data1:dataSnapshot.getChildren()) {
                                if (data1.child(Items.get(i).id).exists()) {
                                    booking.setEnabled(false);
                                    booking.setText("Booked up");
                                    booking.setBackgroundColor(Color.WHITE);
                                    booking.setTextColor(Color.BLACK);
                                }
                            }}}}
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(c, BokkongStepOne.class);

                    in.putExtra("id", Items.get(i).id);
                    in.putExtra("name", Items.get(i).name);
                    in.putExtra("model", Items.get(i).model);
                    in.putExtra("price", Items.get(i).price);
                    in.putExtra("person", Items.get(i).numperson);
                    in.putExtra("door", Items.get(i).numdoor);
                    in.putExtra("type", Items.get(i).type);
                    in.putExtra("cond", Items.get(i).conditon);
                    in.putExtra("bag", Items.get(i).numbag);
                    in.putExtra("image", Items.get(i).image);

                    c.startActivity(in);

                }
            });

            return view1;

        }
        public void remove (int id){
            Items.remove(id);
        }


    }


}





