package com.zameer.porao1;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    MapView mMapView;
    private GoogleMap googleMap;

    Context context;

    boolean portechaiclicked = false;
    public static LatLng portechaiPosition;
    public static int markersCounter;
    public static ArrayList<TuitionRequest> allTuitionList;
    public static ArrayList<User> allUserList;
    public static ArrayList<PendingRating> allPendingRatingList;
    public static HashMap<Marker, Integer> allMarker;
    public TuitionRequest selectedMarkerInformation;
    public Marker currentSelectedMarker;

    public int pending_rating_counter;
    public static ArrayList<PendingRating> prList;
    public static PendingRating prThis;
    //public static transient PendingRating pendingRating2 = null;


    public static User currentUser;

    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        context = this;


        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("pending_rating_counter").getRef();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pending_rating_counter = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 2);
                    System.exit(0);
                    return;
                }

                googleMap.setMyLocationEnabled(true);


                // For dropping a marker at a point on the Map
                //LatLng sydney = new LatLng(-34, 151);
                //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                //CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                /*CameraPosition cameraPosition1 = new CameraPosition.Builder()
                        .target(new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to east
                        .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));*/


                toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);


                //LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                //assert locationManager != null;
                if (locationManager != null)
                    Log.i("Location", "Location: " + locationManager);
                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
                if (location != null) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));

                } else {
                    Log.i("Location", "Location NULL ");
                }


                Log.i("listoflatlng", Integer.toString(allTuitionList.size()));
                if (allTuitionList != null) {
                    int i = 0;
                    for (TuitionRequest temp : allTuitionList) {
                        Marker m = googleMap.addMarker(new MarkerOptions().position(new LatLng(temp.lat, temp.lng)));
                        allMarker.put(m, i);
                        i++;

                    }
                }

                googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MainActivity.this));


                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng point) {
                        // TODO Auto-generated method stub
                        //lstLatLngs.add(point);

                        googleMap.clear();
                        portechaiPosition = point;

                        //Log.i("listoflatlng", Integer.toString(allTuitionList.size()));
                        if (allTuitionList != null) {
                            int i = 0;
                            for (TuitionRequest temp : allTuitionList) {
                                Marker m = googleMap.addMarker(new MarkerOptions().position(new LatLng(temp.lat, temp.lng)));
                                allMarker.put(m, i);
                                i++;
                            }
                        }

                        googleMap.addMarker(new MarkerOptions().position(point));
                    }
                });

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        int index = allMarker.get(marker);
                        Log.i("marker", allTuitionList.get(index).toString() + "  " + index);
                        marker.showInfoWindow();
                        return true;
                    }
                });


                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(final Marker marker) {
                        selectedMarkerInformation = allTuitionList.get(allMarker.get(marker));
                        currentSelectedMarker = marker;

                        Log.i("marker",currentSelectedMarker.toString());


                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        int in = MainActivity.allMarker.get(currentSelectedMarker);
                        int id = MainActivity.allTuitionList.get(in).getUserID();
                        User user = null;
                        for(User usr:MainActivity.allUserList){
                            if(usr.getId()==id){
                                user=usr;
                            }
                        }

                        if(user!=null)
                            builder.setMessage(user.toStringForShowInfo());
                        else
                            builder.setMessage("");

                        builder.setTitle("What do you want to do?");

                        builder.setPositiveButton("Accept Tuition", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {


                                int index = MainActivity.allMarker.remove(currentSelectedMarker);
                                int counterid = MainActivity.allTuitionList.get(index).getCounterid();
                                pending_rating_counter++;
                                PendingRating pendingRating = new PendingRating(MainActivity.allTuitionList.get(index).getUserID(),
                                        MainActivity.currentUser.getId(),pending_rating_counter,MainActivity.allTuitionList.get(index));




                                PendingRating pendingRating2 = new PendingRating(MainActivity.currentUser.getId(),
                                        MainActivity.allTuitionList.get(index).getUserID(),
                                        -1,
                                        MainActivity.allTuitionList.get(index));

                                MainActivity.allTuitionList.remove(index);
                                prThis = pendingRating2;

                                Intent intent= new Intent(context, RatingActivity.class);
                                intent.putExtra("MyClass", -100);
                                intent.putExtra("toggle",1);
                                //i.putExtra("MyClass", (Serializable) pr);
                                startActivity(intent);


                                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                                db.child("markers").child(Integer.toString(counterid)).removeValue();
                                db.child("pending_rating_counter").setValue(pending_rating_counter);
                                db.child("pending_rating").child(Integer.toString(pending_rating_counter)).setValue(pendingRating);


                                googleMap.clear();
                                if (allTuitionList != null) {
                                    int i = 0;
                                    for (TuitionRequest temp : allTuitionList) {
                                        Marker m = googleMap.addMarker(new MarkerOptions().position(new LatLng(temp.lat, temp.lng)));
                                        allMarker.put(m, i);
                                        i++;
                                    }
                                }

                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Make Call", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + selectedMarkerInformation.getContact()));
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    dialog.dismiss();
                                }
                                startActivity(intent);
                                //finish();


                                dialog.dismiss();
                            }
                        });

                        builder.setNeutralButton("direction", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {







                                                                  String latitude = String.valueOf(marker.getPosition().latitude);
                                String longitude = String.valueOf(marker.getPosition().longitude);
                                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);



                            }
                        });



                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });


            }
        });





        Button portechai = (Button) findViewById(R.id.buttonPorteChai);
        portechai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(portechaiPosition==null){
                    toast.cancel();
                    toast = Toast.makeText(context,"No location chosen",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    startActivity(new Intent(context, AddTuition.class));
                    finish();
                }

            }
        });







        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("Place", "Place: " + place.getName());
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(place.getLatLng())   // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to east
                        .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Place", "An error occurred: " + status);
            }
        });



        new CountDownTimer(10000,5000){
            @Override
            public void onTick(long millisUntilFinished){
                googleMap.clear();
                if (allTuitionList != null) {
                    int i = 0;
                    for (TuitionRequest temp : allTuitionList) {
                        Marker m = googleMap.addMarker(new MarkerOptions().position(new LatLng(temp.lat, temp.lng)));
                        allMarker.put(m, i);
                        i++;
                    }
                }
            }

            @Override
            public void onFinish(){
                googleMap.clear();
                if (allTuitionList != null) {
                    int i = 0;
                    for (TuitionRequest temp : allTuitionList) {
                        Marker m = googleMap.addMarker(new MarkerOptions().position(new LatLng(temp.lat, temp.lng)));
                        allMarker.put(m, i);
                        i++;
                    }
                }
            }
        }.start();

    }





    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions.length == 1 &&
                permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);
        } else {
            // Permission was denied. Display an error message.
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            currentUser=null;
            Intent i = new Intent(context, LoginActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
