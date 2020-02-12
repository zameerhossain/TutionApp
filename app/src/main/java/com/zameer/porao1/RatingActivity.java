package com.zameer.porao1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class RatingActivity extends AppCompatActivity {


    PendingRating pr;
    Button submit;

    RadioButton b1;
    RadioButton b2;
    RadioButton b3;
    RadioButton b4;
    RadioButton b5;

    TextView tv;

    Button btn;

    int toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);


        tv = (TextView) findViewById(R.id.textViewRating);
        b1 = (RadioButton) findViewById(R.id.radioButton1);
        b2 = (RadioButton) findViewById(R.id.radioButton2);
        b3 = (RadioButton) findViewById(R.id.radioButton3);
        b4 = (RadioButton) findViewById(R.id.radioButton4);
        b5 = (RadioButton) findViewById(R.id.radioButton5);
        btn = (Button) findViewById(R.id.buttonSubmitRating);

        Log.i("ratingActivity","um here");

        //pr = (PendingRating) getIntent().getSerializableExtra("MyClass");
        int index = getIntent().getIntExtra("MyClass",0);
        if(index==-100)
            pr = MainActivity.prThis;
        else
            pr = MainActivity.prList.get(index);

        toggle = getIntent().getIntExtra("toggle",2);

        tv.setText(pr.prText() + "\n\nNow, give a rating based on your experience:");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = new User();
                for(User u : MainActivity.allUserList){
                    if(u.getId()==pr.getTeachersId()){
                        user = u;
                        break;
                    }
                }

                double nowRated = 0;
                if(b1.isChecked()) nowRated=1;
                else if(b2.isChecked()) nowRated=2;
                else if(b3.isChecked()) nowRated=3;
                else if(b4.isChecked()) nowRated=4;
                else if(b5.isChecked()) nowRated=5;

                Log.i("rating",Double.toString(nowRated));

                if(toggle==2){
                    double currentRating = user.getTeacher_rating()*user.getTeacher_rating_counter();
                    currentRating+=nowRated;
                    int ratingCounter = user.getTeacher_rating_counter();
                    ratingCounter++;
                    currentRating=currentRating/ratingCounter;

                    user.setTeacher_rating(currentRating);
                    user.setTeacher_rating_counter(ratingCounter);
                }else if(toggle==1){
                    double currentRating = user.getStudent_rating()*user.getStudent_rating_counter();
                    currentRating+=nowRated;
                    int ratingCounter = user.getStudent_rating_counter();
                    ratingCounter++;
                    currentRating=currentRating/ratingCounter;

                    user.setStudent_rating(currentRating);
                    user.setStudent_rating_counter(ratingCounter);
                }


                FirebaseDatabase.getInstance().getReference().child("user").child(Integer.toString(user.getId())).setValue(user);
                FirebaseDatabase.getInstance().getReference().child("pending_rating").child(Integer.toString(pr.getRatingId())).removeValue();
                finish();
            }
        });

    }
}
