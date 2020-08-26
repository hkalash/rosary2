package com.smartsoftwaresolutions.rosary2.Duaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.rosary2.BuildConfig;
import com.smartsoftwaresolutions.rosary2.Path.Path;
import com.smartsoftwaresolutions.rosary2.R;

public class Show_Duaa extends AppCompatActivity {
TextView tv_duaa_title,tv_duaa_main;
    String name,value_ID,discription;
    public static final String PREFS_NAME = "Saved_Type";
    MenuItem myItem;
  //  private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__duaa);

        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
        if(BuildConfig.FLAVOR.equals("Free")){
            /**           Admob inti                               **/
            mInterstitialAd = new InterstitialAd(this);
            // real id is :ca-app-pub-8537373371656761/2013021686
            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());


            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });


         //   mAdView = findViewById(R.id.adView16);


            // AdRequest adRequest = new AdRequest.Builder().build();
            //  AdRequest adRequest = new AdRequest.Builder().addTestDevice("35663D4E3BC0B627A8E5C8571E17588F").build();
            //AdRequest adRequest = new AdRequest.Builder().build();
           // mAdView.loadAd(adRequest);

//            mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
//                @Override
//                public void onAdLoaded() {
//                    super.onAdLoaded();
//                    Log.e("onAdLoaded","AdLoaded");
//
//                }
//
//                @Override
//                public void onAdFailedToLoad(int errorCode) {
//                    super.onAdFailedToLoad(errorCode);
//                    Log.e("onAdFailedToLoad",""+errorCode);
//
//                }
//            });
            // Toast.makeText(Path.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
        }

        tv_duaa_title=findViewById(R.id.tv_duaa_title);
        tv_duaa_main=findViewById(R.id.tv_duaa_main);

        // data to populate the RecyclerView with
        SharedPreferences SpType =getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Reading from SharedPreferences
        name = SpType.getString("TypeD", "");
        value_ID = SpType.getString("Type_IDD", "");
        discription = SpType.getString("Type_nativeD", "");
        tv_duaa_title.setText(name);
        tv_duaa_main.setText(discription);

    }
    @Override
    protected void onStop() {
        if(BuildConfig.FLAVOR.equals("free")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }
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
        myItem = menu.findItem(R.id.menu_counter);
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
                String shareBodyText = "Check it out.\n\n"+" https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.blackout.free";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;
            case R.id.menu_back:

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
