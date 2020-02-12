package com.zameer.porao1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mimtiaze on 08-Mar-17.
 */

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);

        final Context context = this;

        new fetchingData().execute();

        new CountDownTimer(1500,500){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){
                //set the new Content of your activity
                Intent i = new Intent(context, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }.start();

    }


    public static class fetchingData extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            MainActivity.allTuitionList = new ArrayList<>();
            MainActivity.allMarker = new HashMap<>();

            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("marker_counter").getRef();
            db1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Integer markerCount = dataSnapshot.getValue(Integer.class);
                    if(markerCount!=null)
                        MainActivity.markersCounter = markerCount;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("markers").getRef();
            db.addChildEventListener(new ChildEventListener() {
                    @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    TuitionRequest tuitionRequest = dataSnapshot.getValue(TuitionRequest.class);
                    if(tuitionRequest!=null) {
                        MainActivity.allTuitionList.add(tuitionRequest);

                    }else{

                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            MainActivity.allUserList = new ArrayList<>();
            DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("user").getRef();
            db2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    User usr = dataSnapshot.getValue(User.class);
                    if(usr!=null) {
                        MainActivity.allUserList.add(usr);
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            MainActivity.allPendingRatingList = new ArrayList<>();
            DatabaseReference db3 = FirebaseDatabase.getInstance().getReference().child("pending_rating").getRef();
            db3.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    PendingRating pendingRating = dataSnapshot.getValue(PendingRating.class);
                    if(pendingRating!=null) {
                        MainActivity.allPendingRatingList.add(pendingRating);
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }

    }

}
