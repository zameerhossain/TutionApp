package com.zameer.porao1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText phone;
    TextInputEditText password;

    Button loginButton;
    Button signupButton;

    Context context;

    SharedPreferences prefs;

    Toast toast;

    public static String ratingText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = (TextInputEditText) findViewById(R.id.phoneLogin);
        password = (TextInputEditText) findViewById(R.id.passwordLogin);
        loginButton = (Button) findViewById(R.id.loginButton);
        signupButton = (Button) findViewById(R.id.signupButton);


        context = this;
        prefs = this.getSharedPreferences(
                "com.example.porao", Context.MODE_PRIVATE);

        toast = Toast.makeText(context,"hello",Toast.LENGTH_SHORT);

        String ph = prefs.getString("phone","");
        String pass = prefs.getString("password","");
        /*if(checkUserValidity(ph,pass)){
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
            finish();
        }else{*/
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String ph = phone.getText().toString();
                    String pass = password.getText().toString();
                    if(checkUserValidity(ph,pass)){
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        finish();

                        prefs.edit().putString("phone",ph).apply();
                        prefs.edit().putString("password",pass).apply();
                        MainActivity.prList = new ArrayList<>();
                        int prCounter = 0;

                        while(true){
                            PendingRating pr = checkPendingRating(MainActivity.currentUser);
                            if(pr==null) break;

                            MainActivity.prList.add(pr);


                            Intent i = new Intent(context, RatingActivity.class);
                            i.putExtra("MyClass", prCounter);
                            i.putExtra("toggle",2);
                            //i.putExtra("MyClass", (Serializable) pr);
                            startActivity(i);
                            prCounter++;
                            MainActivity.allPendingRatingList.remove(pr);
                            //finish();
                        }

                        Log.i("ratingActivity",Integer.toString(prCounter));

                    }else{
                        toast.cancel();
                        toast = Toast.makeText(context,"Incorrect phone no or password",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            });

            signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, CreateAccountActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        //}



    }

    boolean checkUserValidity(String ph,String pass){
        for(User user:MainActivity.allUserList){
            if(ph.equals(user.getPhone()) && pass.equals(user.getPassword())){
                MainActivity.currentUser = user;
                return true;
            }
        }
        return false;
    }

    PendingRating checkPendingRating(User user){
        for(PendingRating pr : MainActivity.allPendingRatingList){
            if(pr.getMyid()==MainActivity.currentUser.getId()){
                return pr;
            }
        }
        return null;
    }
}
