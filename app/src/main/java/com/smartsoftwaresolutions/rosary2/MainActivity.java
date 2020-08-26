package com.smartsoftwaresolutions.rosary2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.stetho.Stetho;
//import com.facebook.stetho.Stetho;
import com.facebook.stetho.Stetho;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.rosary2.Clasic.Clasic;
import com.smartsoftwaresolutions.rosary2.DataBase.DataHelper;
import com.smartsoftwaresolutions.rosary2.Duaa.Duaa;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.Progress_Mode.ProgressBar_Mode;
import com.smartsoftwaresolutions.rosary2.Select_Tasbeh_List.Select_Tasbeh;
import com.smartsoftwaresolutions.rosary2.Swip.Swipe_Mode;
import com.smartsoftwaresolutions.rosary2.Tasbeh_CRUD.Tasbeh_item;
import com.smartsoftwaresolutions.rosary2.Tasbeh_Mode.explode.T_explode;
import com.smartsoftwaresolutions.rosary2.Path.Path;
import com.smartsoftwaresolutions.rosary2.Wave.Wave;
import com.smartsoftwaresolutions.rosary2.YouTube_videos.Youtube_Activity;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageView btn_tasbeh, btn_start, btn_setting, btn_pro;
    Button btn_help;
    //register the animation
    Animation alpa_to_go, alpa_to_go1, alpa_to_go2;
    ImageView default_Button;
    TextView pagetitle;
    Button btnguide;
    // TextView tv;
    DataHelper AndPOS_dbh;
    SQLiteDatabase AndPOS_db;
    //    int t_counter=0;
//    int Grand_Total=0;
//    String Zeker_counter_SQL;
    String value, value_ID, value_native, value_start, with_native;
    // String z="";
    SharedPreferences SpType;
    public static final String PREFS_NAME = "Saved_Type";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);
        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//      if(BuildConfig.FLAVOR.equals("Free")){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        mAdView = findViewById(R.id.adView1);


        // AdRequest adRequest = new AdRequest.Builder().build();
        //  AdRequest adRequest = new AdRequest.Builder().addTestDevice("35663D4E3BC0B627A8E5C8571E17588F").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("onAdLoaded", "AdLoaded");

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                Log.e("onAdFailedToLoad", "" + errorCode);

            }
        });
        //   Toast.makeText(MainActivity.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
        //  }

        SpType = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Reading from SharedPreferences
        value = SpType.getString("Type", "");
        value_native = SpType.getString("Type_native", "");
        value_ID = SpType.getString("Type_ID", "");
        value_start = SpType.getString("start", "");
        with_native = SpType.getString("with_native", "");
        // tv = (TextView) findViewById(R.id.tv_progress);

        // we create an instance of data base helper  database this will create the database
        AndPOS_dbh = new DataHelper(getApplicationContext());
        // and we connect the data to the data base helper and specify its use as a writable
        AndPOS_db = AndPOS_dbh.getWritableDatabase();

        default_Button = findViewById(R.id.imageButton);
        alpa_to_go = AnimationUtils.loadAnimation(this, R.anim.pop);
        // link animation
        default_Button.startAnimation(alpa_to_go);

        default_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Writing data to SharedPreferences
                //  SharedPreferences SpType = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                if (value_start.equals("")) {
                    SharedPreferences.Editor editor = SpType.edit();
                    editor.putString("Type", "ٱلْـحَـمْـدُ لله");
                    editor.putString("Type_native", "Al-ḥamdu li-llāh");
                    editor.putString("Type_ID", "1");
                    editor.putString("start", "");
                    editor.putString("with_native", "1");
                    editor.putInt("set_counter", 33);// the default counter
                    // editor.putString("show_help","1");
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, Clasic.class);
                    startActivity(intent);

                } else if (value_start.equals("1")) {
                    Intent intent = new Intent(MainActivity.this, ProgressBar_Mode.class);
                    startActivity(intent);
                    // Reading from SharedPreferences
//       value = SpType.getString("Type", "");
//       value_native = SpType.getString("Type_native", "");
//        value_ID = SpType.getString("Type_ID", "");
//        value_start=SpType.getString("start","");
                    // String p= String.valueOf(position);

                } else if (value_start.equals("5")) {

                    Intent intent = new Intent(MainActivity.this, T_explode.class);
                    startActivity(intent);
                } else if (value_start.equals("2")) {
                    Intent intent = new Intent(MainActivity.this, Path.class);
                    startActivity(intent);
                } else if (value_start.equals("0")) {
                    Intent intent = new Intent(MainActivity.this, Swipe_Mode.class);
                    startActivity(intent);
                } else if (value_start.equals("3")) {
                    Intent intent = new Intent(MainActivity.this, Wave.class);
                    startActivity(intent);
                } else if (value_start.equals("4")) {
                    Intent intent = new Intent(MainActivity.this, Clasic.class);
                    startActivity(intent);
                } else {
                    SharedPreferences.Editor editor = SpType.edit();
                    editor.putString("Type", "ٱلْـحَـمْـدُ لله");
                    editor.putString("Type_native", "Al-ḥamdu li-llāh");
                    editor.putString("Type_ID", "1");
                    editor.putString("start", "");
                    editor.putString("with_native", "1");
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, Path.class);
                    startActivity(intent);
                }// read from shared preferance
//                SharedPreferences.Editor editor = SpType.edit();
//                editor.putString("Type", Tasbeh_value_name);
//                editor.putString("Type_native", Tasbeh_value_name_native);
//                editor.putString("Type_ID", Tasbeh_ID);
//                editor.commit();
            }
        });

        btnguide = findViewById(R.id.btn_help);
        alpa_to_go2 = AnimationUtils.loadAnimation(this, R.anim.pop2);
        // link animation
        btnguide.startAnimation(alpa_to_go2);

        pagetitle = findViewById(R.id.pagetitle);
        alpa_to_go1 = AnimationUtils.loadAnimation(this, R.anim.pop1);
        // link animation
        pagetitle.startAnimation(alpa_to_go1);
        btn_setting = findViewById(R.id.btn_setting);
        btn_pro = findViewById(R.id.btn_pro);
        btn_tasbeh = findViewById(R.id.btn_tasbeh);
        btn_start = findViewById(R.id.btn_start);
        btn_help = findViewById(R.id.btn_help);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Help_Activity.class);
                startActivity(intent);
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Select_Tasbeh.class);
                startActivity(intent);
            }
        });
        btn_tasbeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Tasbeh_item.class);
                startActivity(intent);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BuildConfig.FLAVOR.equals("Free")) {
                    Intent intent = new Intent(MainActivity.this, Youtube_Activity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.this_option, Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Duaa.class);
                startActivity(intent);
            }
        });
//        // create an instance of a fragment
//        curve_path fragmentCurve=new curve_path();
//        // show the fragment in the container
//        getSupportFragmentManager()
//                .beginTransaction()
//                // we r replacing the container with the fragment
//                .replace(R.id.container, fragmentCurve)
//                .commit();

    }

    //    public void shareText(View view) {
//        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        String shareBodyText = "Your shearing message goes here";
//        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
//        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
//        startActivity(Intent.createChooser(intent, "Choose sharing method"));
//    }
// And override this method
    // Action Bar actions
    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences.Editor editor = SpType.edit();

        switch (item.getItemId()) {
            case R.id.menu_share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                shared_Menu sharedMenu = new shared_Menu();
                String flaver = BuildConfig.FLAVOR;
                String shareBodyText = sharedMenu.flavor(flaver);

//                if(BuildConfig.FLAVOR.equals("Free")) {
//                    shareBodyText = getString(R.string.check_it) + getString(R.string.sharepath);
//                }else if (BuildConfig.FLAVOR.equals("NM")){
//                     // nouhad moukahal
//                //    shareBodyText = getString(R.string.check_it) + getString(R.string.sharepath_NM);
//                }


                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;
            case R.id.action_Arabic:
                setLocale("ar");

                editor.putString("lang", "ar");
                editor.commit();
                Toast.makeText(this, R.string.la, Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_English:
                setLocale("en_US");

                editor.putString("lang", "en_US");
                editor.commit();
                Toast.makeText(this, R.string.le, Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_Help:
                Intent intent2 = new Intent(MainActivity.this, Help_Activity.class);
                startActivity(intent2);
                return true;
//            case R.id.action_reminder:
//                Intent intent3 = new Intent(MainActivity.this, Notefication_Activity.class);
//                startActivity(intent3);
//                return true;
            case R.id.menu_setting:
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.menu_back:

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        value_start = SpType.getString("start", "");
        super.onRestart();
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
