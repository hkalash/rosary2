package com.smartsoftwaresolutions.rosary2.Wave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
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
import com.smartsoftwaresolutions.rosary2.Progress_Mode.ProgressBar_Mode;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Setting;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;
import com.smartsoftwaresolutions.rosary2.shared_Menu;

import java.util.Locale;

public class Wave extends AppCompatActivity  {
    private WaveHelper mWaveHelper;

   // private int mBorderColor = Color.parseColor("#44FFFFFF");
    private int mBorderColor = Color.parseColor("#40D28F1B");
    private int mBorderWidth = 5;
    float waterlevel =0.0f;
    float set_water_level=0.5f;
    public static final String PREFS_NAME = "Saved_Type";
    // TextView tv;
    DataHelper AndPOS_dbh;
    SQLiteDatabase AndPOS_db;
    int t_counter=0;
    int Grand_Total=0;
    String Zeker_counter_SQL;
    String value,value_ID,value_native,with_native;
    int set_counter;
    String z="";
    MenuItem myItem;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    Button tv_counter_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wave);
        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
     //   if(BuildConfig.FLAVOR.equals("Free")){
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


            mAdView = findViewById(R.id.adView9);


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
         //   Toast.makeText(Wave.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
      //  }

// admob
        // admob ID ca-app-pub-8537373371656761~9447992290
        // data to populate the RecyclerView with
        SharedPreferences SpType =getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        tv_counter_4=findViewById(R.id.tv_counter_4);
        // Reading from SharedPreferences
        value = SpType.getString("Type", "");
        value_native = SpType.getString("Type_native", "");
        value_ID = SpType.getString("Type_ID", "");
        with_native = SpType.getString("with_native", "1");
        set_counter=SpType.getInt("set_counter",33);
        tv_counter_4.setText("0");
        // set water level

      //  set_water_level= (float) (1.0/set_counter);
        set_water_level= (float) (0.9/set_counter);
        // tv = (TextView) findViewById(R.id.tv_progress);

        // we create an instance of data base helper  database this will create the database
        AndPOS_dbh = new DataHelper(getApplicationContext());
        // and we connect the data to the data base helper and specify its use as a writable
        AndPOS_db = AndPOS_dbh.getWritableDatabase();

        Get_Counter get_counter=new Get_Counter();
        get_counter.execute("");
        TextView wave_text=findViewById(R.id.tv_wave);

        if (with_native.equals("1")){
            wave_text.setText(value+"\n"+value_native);
        }else if (with_native.equals("2")){
            wave_text.setText(value);
        }else {
            wave_text.setText(value_native);
        }
       // wave_text.setText(value+"    "+value_native);
     //  waveView = (WaveView)
        final WaveView waveView=findViewById(R.id.wave);
        waveView.setBorder(mBorderWidth, mBorderColor);

        mWaveHelper = new WaveHelper(waveView);

        ((RadioGroup) findViewById(R.id.shapeChoice))
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.shapeCircle:
                                waveView.setShapeType(WaveView.ShapeType.CIRCLE);
                                break;
                            case R.id.shapeSquare:
                                waveView.setShapeType(WaveView.ShapeType.SQUARE);
                              //  getResources().getColorStateList(R.color.three);
                                break;
                            default:
                                break;
                        }
                    }
                });
        waveView.setBorder(mBorderWidth, mBorderColor);
//        ((SeekBar) findViewById(R.id.seekBar))
//                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                        mBorderWidth = i;
//                        waveView.setBorder(mBorderWidth, mBorderColor);
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                });

//        CompoundButtonCompat.setButtonTintList(
//                (RadioButton) findViewById(R.id.colorDefault),
//                getResources().getColorStateList(android.R.color.white));
//        CompoundButtonCompat.setButtonTintList(
//                (RadioButton) findViewById(R.id.colorRed),
//                getResources().getColorStateList(R.color.red));
        CompoundButtonCompat.setButtonTintList(
                (RadioButton) findViewById(R.id.colorGreen),
                getResources().getColorStateList(R.color.gold1));
        CompoundButtonCompat.setButtonTintList(
                (RadioButton) findViewById(R.id.colorBlue),
                getResources().getColorStateList(R.color.three));

        ((RadioGroup) findViewById(R.id.colorChoice))
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
//                            case R.id.colorRed:
//                                waveView.setWaveColor(
//                                        Color.parseColor("#28f16d7a"),
//                                        Color.parseColor("#3cf16d7a"));
//                                mBorderColor = Color.parseColor("#44f16d7a");
//                                waveView.setBorder(mBorderWidth, mBorderColor);
//                                break;
                            case R.id.colorGreen:
                                waveView.setWaveColor(
                                        Color.parseColor("#40D28F1B"),
                                        Color.parseColor("#80D28F1B"));
                                mBorderColor = Color.parseColor("#B0D28F1B");
                                waveView.setBorder(mBorderWidth, mBorderColor);
                                break;
                            case R.id.colorBlue:
                                waveView.setWaveColor(
                                        Color.parseColor("#404E7BA7"),
                                        Color.parseColor("#804E7BA7"));
                                mBorderColor = Color.parseColor("#4E7BA7");
                                waveView.setBorder(mBorderWidth, mBorderColor);
                                break;
                            default:
                                waveView.setWaveColor(
                                        WaveView.DEFAULT_BEHIND_WAVE_COLOR,
                                        WaveView.DEFAULT_FRONT_WAVE_COLOR);
                                mBorderColor = Color.parseColor("#BD28F1B");
                                waveView.setBorder(mBorderWidth, mBorderColor);
                                break;
                        }
                    }
                });
       // mWaveHelper.start();
        waveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    waterlevel=waterlevel+0.025f;
                waterlevel= waterlevel+set_water_level;
waveView.setWaterLevelRatio(waterlevel);
                if (t_counter<=set_counter) {
                    //  pStatus += 1;
                    t_counter++;
               //     myItem.setTitle(getString(R.string.count)+"  "+ t_counter);
                    tv_counter_4.setText(String.valueOf(t_counter));


                    if( t_counter==set_counter){

                        Update_Counter update_counter=new Update_Counter();
                        update_counter.execute("");
                        waterlevel =0.0f;
                        waveView.setWaterLevelRatio(waterlevel);
                      Intent intent=new Intent(Wave.this, Congratulation.class);
                       startActivity(intent);
                    }
            }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWaveHelper.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWaveHelper.start();
    }
    @Override
    protected void onStop() {
       Update_Counter update_counter=new Update_Counter();
        update_counter.execute("");
      //  if(BuildConfig.FLAVOR.equals("free")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
       // }
        super.onStop();
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
      //  myItem = menu.findItem(R.id.menu_counter);

        MenuItem english=menu.findItem(R.id.action_English);
        MenuItem arabic=menu.findItem(R.id.action_Arabic);
        english.setVisible(false);
        arabic.setVisible(false);
        // first to show zero
       // myItem.setTitle(getString(R.string.count)+"  "+t_counter);
        tv_counter_4.setText(String.valueOf(t_counter));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
             //   String shareBodyText = "Check it out.\n\n"+" https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.free";
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
                Intent intent=new Intent(Wave.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(Wave.this, Help_Activity.class);
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

              //  Toast.makeText(Wave.this,""+ z,Toast.LENGTH_LONG).show();
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
            protected void onPreExecute() {

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                t_counter=0;

             //   myItem.setTitle(getString(R.string.count)+"  "+t_counter);
                tv_counter_4.setText(String.valueOf(t_counter));
//                progress=3*t_counter;

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
        startActivity(refresh);
        finish();
    }
}
