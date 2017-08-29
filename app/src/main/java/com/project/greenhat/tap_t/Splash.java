package com.project.greenhat.tap_t;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Splash extends AppCompatActivity {


    private static boolean check=true;

    private SharedPreferences shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        LinearLayout le=(LinearLayout)findViewById(R.id.splach);


        Handler h= new Handler();

        if(check){

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                check=false;
                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
    else
        {
           le.setVisibility(View.GONE);
            Intent i= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();

        }
}

}
