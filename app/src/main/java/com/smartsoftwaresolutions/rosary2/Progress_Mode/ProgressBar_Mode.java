package com.smartsoftwaresolutions.rosary2.Progress_Mode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.rosary2.BuildConfig;
import com.smartsoftwaresolutions.rosary2.Congratulation.Congratulation;
import com.smartsoftwaresolutions.rosary2.DataBase.DataHelper;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.MainActivity;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Setting;
import com.smartsoftwaresolutions.rosary2.Swip.Swipe_Mode;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;
import com.smartsoftwaresolutions.rosary2.shared_Menu;

import java.util.Locale;


public class ProgressBar_Mode extends AppCompatActivity {
    int progress=0;
Button btn_progress,btn_reset,tv_counter_2;
    public static final String PREFS_NAME = "Saved_Type";
   // TextView tv;
    DataHelper AndPOS_dbh;
    SQLiteDatabase AndPOS_db;
    int t_counter=0,progres_unit;
    int Grand_Total=0;
    String Zeker_counter_SQL;
    String value,value_ID,value_native,with_native;
    int set_counter;
    String z="";
    MenuItem myItem;
    ProgressBar mProgress;
    private InterstitialAd mInterstitialAd;
    public ProgressBar_Mode() {
        mProgress = null;

    }
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar__mode);


        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
      //  if(BuildConfig.FLAVOR.equals("Free")){
            /**           Admob inti                               **/
            mInterstitialAd = new InterstitialAd(this);
            // real id is :ca-app-pub-8537373371656761/2013021686
            mInterstitialAd.setAdUnitId("ca-app-pub-8537373371656761/2013021686");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());



            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });


            mAdView = findViewById(R.id.adView3);


            // AdRequest adRequest = new AdRequest.Builder().build();
            //  AdRequest adRequest = new AdRequest.Builder().addTestDevice("35663D4E3BC0B627A8E5C8571E17588F").build();
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.e("onAdLoaded","AdLoaded");

                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    Log.e("onAdFailedToLoad",""+errorCode);

                }
            });
          //  Toast.makeText(MainActivity.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
      //  }

        tv_counter_2=findViewById(R.id.tv_counter_2);
tv_counter_2.setText("0");

        mProgress =  findViewById(R.id.circularProgressbar);
Create_ProgressBar();
//        Resources res = getResources();
//        Drawable drawable = res.getDrawable(R.drawable.circular_prograss);
//        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
//        mProgress.setProgress(0);   // Main Progress
//        mProgress.setSecondaryProgress(100); // Secondary Progress
//        mProgress.setMax(100); // Maximum Progress
//        mProgress.setProgressDrawable(drawable);

        // data to populate the RecyclerView with
        SharedPreferences SpType =getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Reading from SharedPreferences
        value = SpType.getString("Type", "");
        value_native = SpType.getString("Type_native", "");
        value_ID = SpType.getString("Type_ID", "");
        with_native = SpType.getString("with_native", "1");
        set_counter=SpType.getInt("set_counter",33);
        progres_unit=100/set_counter;
       // tv = (TextView) findViewById(R.id.tv_progress);
        btn_progress   =  findViewById(R.id.btn_progress);
        btn_reset   =  findViewById(R.id.btn_reset);
        // we create an instance of data base helper  database this will create the database
        AndPOS_dbh = new DataHelper(getApplicationContext());
        // and we connect the data to the data base helper and specify its use as a writable
        AndPOS_db = AndPOS_dbh.getWritableDatabase();

       Get_Counter get_counter=new Get_Counter();
        get_counter.execute("");
        if (with_native.equals("1")){
            btn_progress.setText(value+"\n\n"+value_native);
        }else if (with_native.equals("2")){
            btn_progress.setText(value);
        }else {
            btn_progress.setText(value_native);
        }


btn_reset.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Update_Counter update_counter=new Update_Counter();
        update_counter.execute("");
     //   t_counter=0;
        Create_ProgressBar();
    }
});
        btn_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   if (t_counter<=32) {
                if (t_counter<=set_counter) {
                  //  pStatus += 1;
                    t_counter++;
                   // myItem.setTitle(getString(R.string.count)+"  "+t_counter);
                    tv_counter_2.setText(String.valueOf(t_counter));
                   // progress=3*t_counter;
                    progress=progres_unit*t_counter;
                    mProgress.setProgress(progress);
                 //   String s= String.valueOf(t_counter);
                  //  tv.setText(s );

                    if( t_counter==set_counter){
                        mProgress.setProgress(progress+1);
                       // tv.setText("Congratulation 33" );
                        Update_Counter update_counter=new Update_Counter();
                        update_counter.execute("");
                        Create_ProgressBar();
                        tv_counter_2.setText("0");
                        Intent intent=new Intent(ProgressBar_Mode.this, Congratulation.class);
                        startActivity(intent);
                    }

                }
            }
        });

//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while (pStatus < 100) {
//                    pStatus += 1;
//
//                    handler.post(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//                            mProgress.setProgress(pStatus);
//                            tv.setText(pStatus + "%");
//
//                        }
//                    });
//                    try {
//                        // Sleep for 200 milliseconds.
//                        // Just to display the progress slowly
//                        Thread.sleep(16); //thread will take approx 3 seconds to finish
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
   }

    private void Create_ProgressBar() {
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular_prograss);

        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
    }

    @Override
    protected void onStop() {
       Update_Counter update_counter=new Update_Counter();
        update_counter.execute("");
    //    if(BuildConfig.FLAVOR.equals("free")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
     //   }
        super.onStop();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    //menu
    @Override
    public boolean onNavigateUp(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
       // myItem = menu.findItem(R.id.menu_counter);


        MenuItem english=menu.findItem(R.id.action_English);
        MenuItem arabic=menu.findItem(R.id.action_Arabic);
        english.setVisible(false);
        arabic.setVisible(false);
        // first to show zero
     //   myItem.setTitle(getString(R.string.count)+"  "+t_counter);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                //String shareBodyText = "Check it out.\n\n"+" https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.free";
                shared_Menu sharedMenu=new shared_Menu();
                String flaver =BuildConfig.FLAVOR;
                String shareBodyText =sharedMenu.flavor(flaver);

                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;
            case R.id.menu_back:

                finish();
                return true;
            case R.id.action_Arabic:
                setLocale("ar");

                Toast.makeText(this, R.string.la, Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_English:
                setLocale("en_US");

                Toast.makeText(this, R.string.le, Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_setting:
                Intent intent=new Intent(ProgressBar_Mode.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(ProgressBar_Mode.this, Help_Activity.class);
                startActivity(intent2);
                return true;
//            case R.id.action_reminder:
//                Intent intent3=new Intent(this, Notefication_Activity.class);
//                startActivity(intent3);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class Get_Counter extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

         //   Toast.makeText(ProgressBar_Mode.this,""+ z,Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            /*********************************************read couter from sql**/
            String query_SQL = "select Zeker_count from Zeker_Tbl where Zeker_Id="+value_ID;
            // we run this select statment by creating a cursor and execute it
            Cursor cursor=AndPOS_db.rawQuery(query_SQL,null);
            /***find data**/
            if(cursor.moveToFirst()){
                do{
                    Zeker_counter_SQL= cursor.getString(cursor.getColumnIndex("Zeker_count"));
                }while (cursor.moveToNext());
                cursor.close();
                z = "Success";
            }else{
                z = "Error retrieving data from table";
            }


            /***********************explode *******************************/





            return null;
        }
    }

    public class Update_Counter extends AsyncTask<String,String ,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            t_counter=0;
        //    myItem.setTitle(getString(R.string.count)+"  "+t_counter);
            progress=3*t_counter;

          //  mProgress.setProgress(progress);
        }

        @Override
        protected String doInBackground(String... strings) {
            Grand_Total= Integer.parseInt(Zeker_counter_SQL)+t_counter;

            /***************update the counter****************/
            // Create a new map of values, where column names are the keys
            ContentValues values1 = new ContentValues();
            // the customer Id is auto increament

            values1.put("Zeker_count", Grand_Total);
            //the insert() will give -1 if the statment failed and id number if successfully inserted
            // it took table name and the value we make a content value to put all the column names in it
            try {
                //the update commend will take (1,2,3,4) 1 database name, 2 values , 3 condition ===> where statmente
                long rowUpdate = AndPOS_db.update("Zeker_Tbl",values1,"Zeker_Id="+value_ID,null);
                if (rowUpdate != -1) {
                    //Toast.makeText(AddCustomer.this, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
                    z = "Updated Successfully";
                    Zeker_counter_SQL= String.valueOf(Grand_Total);
                } else {
                    //  Toast.makeText(AddCustomer.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    z = "Something wrong";
                }
            } catch (SQLiteConstraintException e1) {
                //Toast.makeText(AddCustomer.this,  e1.toString(),Toast.LENGTH_LONG).show();
                // z = e1.toString();
                z = "Counter not updated ";
            }
            /****************************************/
            return null;
        }
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        /// change the direction of the app from left to right to right to left
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(myLocale);
        }
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);

    }
    }

