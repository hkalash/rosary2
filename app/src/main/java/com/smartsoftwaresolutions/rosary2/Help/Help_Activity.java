package com.smartsoftwaresolutions.rosary2.Help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.VideoView;

//import com.facebook.stetho.Stetho;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.rosary2.BuildConfig;
import com.smartsoftwaresolutions.rosary2.Duaa.Duaa;
import com.smartsoftwaresolutions.rosary2.MainActivity;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Setting;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;
import com.smartsoftwaresolutions.rosary2.shared_Menu;
import com.smartsoftwaresolutions.rosary2.video.Video_Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Help_Activity extends AppCompatActivity {
    MenuItem myItem;
    private AdView mAdView;
    Switch switch2;
    SharedPreferences SpType ;
    Button btn_helpvideo;
    public static final String PREFS_NAME = "Saved_Type";
  //  private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
      //  Stetho.initializeWithDefaults(this);
        btn_helpvideo=findViewById(R.id.btn_helpvideo);
        btn_helpvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Help_Activity.this, Video_Activity.class);
                startActivity(intent);
            }
        });
        switch2=findViewById(R.id.switch2);
        switch2.setChecked(true);
        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2.setChecked(false);
                SpType=getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = SpType.edit();
                editor.putString("show_help","0");
                editor.apply();
            }
        });
        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
       // if(BuildConfig.FLAVOR.equals("Free")){

//            /**           Admob inti                               **/
//            mInterstitialAd = new InterstitialAd(this);
//            // real id is :ca-app-pub-8537373371656761/2013021686
//            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//            mInterstitialAd.loadAd(new AdRequest.Builder().build());


            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });


            mAdView = findViewById(R.id.adView11);


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
          //  Toast.makeText(Help_Activity.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
    //    }
        List<Help_Data> data = fill_with_data();

        RecyclerView recyclerView = findViewById(R.id.help_recyclerview);
        Help_Adapter adapter = new Help_Adapter(data, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }




    public List<Help_Data> fill_with_data() {

        List<Help_Data> data = new ArrayList<>();

// position 0
        data.add(new Help_Data(getString(R.string.swip_motion),
                getString(R.string.swip_discription),
                R.drawable.rosary_white));
        // position 1
        data.add(new Help_Data(getString(R.string.progress_ring),
                getString(R.string.progress_discription),
                R.drawable.one));

        // position 2
        data.add(new Help_Data(getString(R.string.path_motion),
                getString(R.string.path_discription),
                R.drawable.two));
        // position 3
        data.add(new Help_Data(getString(R.string.wave),
                getString(R.string.wave_discription),
                R.drawable.three));
        // position 4
        data.add(new Help_Data(getString(R.string.clasic),
                getString(R.string.clasic_description),
                R.drawable.four));
        // position 5
        data.add(new Help_Data(getString(R.string.list_in_motion),
                getString(R.string.list_in_motion_discription),
                R.drawable.five));

        return data;
    }

    @Override
    protected void onStop() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//        }
//        Intent i=new Intent(Help_Activity.this, MainActivity.class);
//        startActivity(i);
        super.onStop();
    }

    //menu
    @Override
    public boolean onNavigateUp(){
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//        }
//        Intent i=new Intent(Help_Activity.this, MainActivity.class);
//        startActivity(i);
        finish();
        return true;
    }
    public void onBackPressed()
    {
        //do whatever you want the 'Back' button to do
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
        this.startActivity(new Intent(Help_Activity.this,MainActivity.class));
finish();
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        myItem = menu.findItem(R.id.menu_counter);

        MenuItem english=menu.findItem(R.id.action_English);
        MenuItem arabic=menu.findItem(R.id.action_Arabic);
        english.setVisible(false);
        arabic.setVisible(false);
        // first to show zero
        //  myItem.setTitle("count  "+t_counter);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
              //  String shareBodyText = "Check it out.\n\n"+"https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.free";

                shared_Menu sharedMenu=new shared_Menu();
                String flaver =BuildConfig.FLAVOR;
                String shareBodyText =sharedMenu.flavor(flaver);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;
            case R.id.menu_back:
                finish();
Intent i=new Intent(Help_Activity.this, MainActivity.class);
                startActivity(i);

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
                Intent intent=new Intent(Help_Activity.this, Setting.class);
                startActivity(intent);

                return true;
//            case R.id.action_reminder:
//                Intent intent3=new Intent(this, Notefication_Activity.class);
//                startActivity(intent3);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
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

