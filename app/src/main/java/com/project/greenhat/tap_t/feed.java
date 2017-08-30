package com.project.greenhat.tap_t;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.FeedList;
import control.acontrol;
import data.Feeditem;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class feed extends AppCompatActivity {


    private ListView listView;
    private ProgressBar spinner;
    private  List<Feeditem> feedItems;
    private FeedList listAdapter;
    SQLiteDatabase db1;
    ImageLoader imageLoader = acontrol.getInstance().getImageLoader();
    private static Twitter twitter;
    private DatePicker datePicker;
    private Calendar calendar;
    private EditText dateView;
    private int year, month, day;
    static int i=0;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);



        spinner = (ProgressBar)findViewById(R.id.prog);
        spinner.setVisibility(View.VISIBLE);
        feedItems = new ArrayList<Feeditem>();


      final  LinearLayout l=(LinearLayout)findViewById(R.id.back_menu_ll);
      final   LinearLayout l1=(LinearLayout)findViewById(R.id.search);
        l1.setVisibility(View.GONE);
      try {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(getString(R.string.twitter_consumer_key));
        builder.setOAuthConsumerSecret(getString(R.string.twitter_consumer_secret));

        Intent i=getIntent();

        Bundle extras=i.getExtras();
        // Access Token
        String access_token =extras.getString("token");


        String access_token_secret = extras.getString("secret");

        AccessToken accessToken = new AccessToken(access_token, access_token_secret);
        Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

        final User user = twitter.showUser(twitter.getId());
        String url=user.getProfileImageURL();


            NetworkImageView profilePic = (NetworkImageView)findViewById(R.id.logo);
            profilePic.setImageUrl(url,imageLoader);

            TextView t=(TextView)findViewById(R.id.title);
            TextView t1=(TextView)findViewById(R.id.title2);
            t1.setText(user.getName());



        }catch(Exception e){}


        ImageView v=(ImageView)findViewById(R.id.serc);
        ImageView v1=(ImageView)findViewById(R.id.sedb);
        ImageView v2=(ImageView)findViewById(R.id.backserch);
        final ImageView v3=(ImageView)findViewById(R.id.logout);
        final EditText ser=(EditText)findViewById(R.id.serch);
            v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        l.setVisibility(View.GONE);
                        l1.setVisibility(View.VISIBLE);
                    }
                });



                v2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        l1.setVisibility(View.GONE);
                        l.setVisibility(View.VISIBLE);

                    }
                });
                  v3.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          PopupMenu popup = new PopupMenu(feed.this,v3);
                          //Inflating the Popup using xml file
                          popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());


                          popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                              public boolean onMenuItemClick(MenuItem item) {

                                  switch(item.getItemId()) {
                                      case R.id.one:
                                          Intent log=new Intent(getBaseContext(),MainActivity.class);
                                          startActivity(log);
                                          finish();
                                          break;
                                      case R.id.two:

                                          SharedPreferences preferences =getSharedPreferences("sample_twitter_pref", Activity.MODE_PRIVATE);
                                          SharedPreferences.Editor editor = preferences.edit();
                                          editor.clear();
                                          editor.commit();
                                          Intent log1=new Intent(getBaseContext(),MainActivity.class);
                                          startActivity(log1);
                                          finish();
                                          break;
                                      case R.id.three:
                                          finish();
                                          break;

                                  };
                                  Toast.makeText(feed.this, item.getTitle(),Toast.LENGTH_SHORT).show();
                                  return true;
                              }
                          });

                          popup.show();

                      }
                  });



        listView = (ListView) findViewById(R.id.list);

 Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(c.getTime());


        inser(date);

        dateView = (EditText) findViewById(R.id.serch);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);


     /*   getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));



/*
*/         /*ser.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                feed.this.listAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/

    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        if(month/10==0)
        dateView.setText(new StringBuilder().append(day).append("/0").append(month).append("/").append(year));
        else
            dateView.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
        feedItems.clear();
    }

    public void inser(String s) {


        database dba=new database(getApplicationContext(),"tapzo",null,3);
        db1=dba.getWritableDatabase();
        try {


           
                Cursor c2=null;


                c2 = db1.rawQuery("select * from tstat where timestamp='"+s+"'", null);


                Log.d("here der", "netu");
                if (c2.moveToFirst()) {
                    do {
                        Feeditem item = new Feeditem();
                        item.setName(c2.getString(1));
                        item.setUrl(c2.getString(3));
                        item.setTimeStamp(c2.getString(4));
                        item.setProfilePic(c2.getString(5));
                        feedItems.add(item);

                    }
                    while (c2.moveToNext());
                }


                listAdapter = new FeedList(this.getBaseContext(), feedItems);
                listView.setAdapter(listAdapter);

            db1.close();
            spinner.setVisibility(View.GONE);


        } catch (Exception e) {

//
            Log.d("Failed to Fetch!", e.getMessage());

        }
    }

}
