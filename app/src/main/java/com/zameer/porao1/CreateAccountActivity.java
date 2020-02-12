package com.zameer.porao1;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountActivity extends AppCompatActivity {


    TextInputEditText name;
    TextInputEditText email;
    TextInputEditText contact;
    TextInputEditText password;
    int id;
    Toast toast;

    Button submitButton;

    User user;

    Context context;


    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        name = (TextInputEditText) findViewById(R.id.createAccountName);
        email = (TextInputEditText) findViewById(R.id.createAccountEmail);
        contact = (TextInputEditText) findViewById(R.id.createAccountContact);
        password = (TextInputEditText) findViewById(R.id.createAccountPassword);
        submitButton = (Button) findViewById(R.id.createAccountButton);


        context = this;

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = db.child("user_count").getRef();
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        id = dataSnapshot.getValue(Integer.class);
                        id++;
                        user = new User(name.getText().toString(),contact.getText().toString(),
                                email.getText().toString(),password.getText().toString());
                        user.setId(id);
                        db2.child("user").child(Integer.toString(id)).setValue(user);
                        db2.child("user_count").setValue(id);

                        MainActivity.currentUser = user;

                        Intent i = new Intent(context, Splash.class);
                        startActivity(i);
                        finish();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

            Intent i = new Intent(context, LoginActivity.class);
            startActivity(i);

    }

}
