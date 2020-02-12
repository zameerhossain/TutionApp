package com.zameer.porao1;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTuition extends AppCompatActivity {

    TextInputEditText courseTitle;
    TextInputEditText problemDescription;
    TextInputEditText contact;

    Button submitButton;

    TuitionRequest tuitionRequest;

    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tuition);



        courseTitle = (TextInputEditText) findViewById(R.id.addTuitionCourseTitle);
        problemDescription = (TextInputEditText) findViewById(R.id.addTuitionProblemDescription);
        contact = (TextInputEditText) findViewById(R.id.addTuitionContact);
        submitButton = (Button) findViewById(R.id.addTuitionSubmitButton);

        contact.setText(MainActivity.currentUser.getPhone());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.markersCounter++;

                tuitionRequest = new TuitionRequest(MainActivity.portechaiPosition.latitude,MainActivity.portechaiPosition.longitude,
                        MainActivity.currentUser.getPhone(),
                        courseTitle.getText().toString(),
                        problemDescription.getText().toString(),
                        MainActivity.markersCounter,
                        MainActivity.currentUser.getId());


                db.child("markers").child(Integer.toString(MainActivity.markersCounter)).setValue(tuitionRequest);
                db.child("marker_counter").setValue(MainActivity.markersCounter);

                MainActivity.allTuitionList.add(tuitionRequest);

                Intent i = new Intent(view.getContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
