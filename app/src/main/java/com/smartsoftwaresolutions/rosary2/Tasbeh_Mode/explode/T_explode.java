package com.smartsoftwaresolutions.rosary2.Tasbeh_Mode.explode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Explode;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

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
import android.graphics.Rect;
import android.os.AsyncTask;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.rosary2.BuildConfig;
import com.smartsoftwaresolutions.rosary2.Clasic.Clasic;
import com.smartsoftwaresolutions.rosary2.Congratulation.Congratulation;
import com.smartsoftwaresolutions.rosary2.DataBase.DataHelper;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.MainActivity;
import com.smartsoftwaresolutions.rosary2.Path.Path;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Setting;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;
import com.smartsoftwaresolutions.rosary2.shared_Menu;
//import com.smartsoftwaresolutions.rosary2.Wave.Wave;
//import com.transitionseverywhere.Explode;
//import com.transitionseverywhere.Fade;
//import com.transitionseverywhere.Transition;
//import com.transitionseverywhere.TransitionManager;
//import com.transitionseverywhere.TransitionSet;

import java.util.Arrays;
import java.util.Locale;


public class T_explode extends AppCompatActivity {
    RecyclerView rvList;
    explode_Adapter adapter;
    DataHelper AndPOS_dbh;
    SQLiteDatabase AndPOS_db;
    int t_counter=0;
    int Grand_Total=0;
    String z="";
    String Zeker_counter_SQL;
   String value,value_ID;
   View view;
    MenuItem myItem;
  public static int set_counter;
   // String [] data = new String[33];
    String [] data;
    public static final String PREFS_NAME = "Saved_Type";
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    Button tv_counter_5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_explode);

        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
        //if(BuildConfig.FLAVOR.equals("Free")){
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


            mAdView = findViewById(R.id.adView12);


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
          //  Toast.makeText(T_explode.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
     //   }


        rvList = findViewById(R.id.recycler_explode);
        int numberOfColumns = 4;
        rvList.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        // we create an instance of data base helper  database this will create the database
        AndPOS_dbh = new DataHelper(getApplicationContext());
        // and we connect the data to the data base helper and specify its use as a writable
        AndPOS_db = AndPOS_dbh.getWritableDatabase();


        // data to populate the RecyclerView with
        SharedPreferences SpType =getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Reading from SharedPreferences
           value = SpType.getString("Type", "");
           value_ID = SpType.getString("Type_ID", "");
           set_counter=SpType.getInt("set_counter",33);
        data= new String[set_counter];
        // create an array

        // fill the array with data
        Arrays.fill(data,value);
        Get_Counter get_counter=new Get_Counter();
        get_counter.execute("");
        Button imgBack_explode=findViewById(R.id.imgBack_explode);
        tv_counter_5=findViewById(R.id.tv_counter_5);
        tv_counter_5.setText("0");
// at the and add the counter to the grand counter and save it in sql
        imgBack_explode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update_Counter update_counter=new Update_Counter();
                update_counter.execute("");
               // letsExplodeIt(view);
                fill_Adapter();

            }
        });





        // set up the RecyclerView

//        adapter = new explode_Adapter(this, data);
//        rvList.setAdapter(adapter);
        fill_Adapter();
        rvList.addOnItemTouchListener(new CustomRVItemTouchListener(this, rvList, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
               // String p= String.valueOf(position);
                //Toast.makeText(T_explode.this,"selected position is "+ p,Toast.LENGTH_LONG).show();
               // view.setBackgroundColor(Color.GREEN);
              //  view.setBackgroundResource(R.drawable.alah_akbar);
                view.setBackgroundColor(Color.parseColor("#4E7BA7"));
              //  view.setTextColor(Color.parseColor("#D28F1B"));
                view.setSoundEffectsEnabled(true);
                adapter.Ispressed_array[position]=true;
              // view.setTextColor(Color.parseColor("#4E7BA7"));
                // holder.myButton.setBackgroundColor(Color.WHITE);
               // holder.myButton.setBackgroundColor(Color.parseColor("#D28F1B"));
                //view.setTextColor(Color.parseColor("#4E7BA7");

                didTapButton(view);
//                t_counter++;
//                myItem.setTitle("count  " + t_counter);
                if (t_counter<=set_counter) {
                    //  pStatus += 1;
                    t_counter++;
                   // myItem.setTitle(getString(R.string.count)+"  " + t_counter);
                    tv_counter_5.setText(String.valueOf(t_counter));


                    if( t_counter==set_counter){

                        Update_Counter update_counter=new Update_Counter();
                        update_counter.execute("");
//                        waterlevel =0.1f;
//                        waveView.setWaterLevelRatio(waterlevel);
                        Intent intent=new Intent(T_explode.this, Congratulation.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
     //   adapter.setClickListener((explode_Adapter.ItemClickListener) this);

    }

    private void fill_Adapter() {
        adapter = new explode_Adapter(this, data);
        rvList.setAdapter(adapter);
    }

    public void didTapButton(View view) {
       // Button button = (Button)findViewById(R.id.button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        view.startAnimation(myAnim);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
    }
    public void letsExplodeIt(View clickedView) {
        // save rect of view in screen coordinated
        final Rect viewRect = new Rect();
        clickedView.getGlobalVisibleRect(viewRect);

        final Explode explode = new Explode();
        explode.setEpicenterCallback(new Transition.EpicenterCallback() {
            @Override
            public Rect onGetEpicenter(Transition transition) {
                return viewRect;
            }
        });
        explode.excludeTarget(clickedView, true);
        TransitionSet set = new TransitionSet()
                .addTransition(explode)
                .addTransition(new Fade().addTarget(clickedView));
//                .addListener(new Transition.TransitionListenerAdapter() {
//                    @Override
//                    public void onTransitionEnd(Transition transition) {
//                        transition.removeListener(this);
//                        T_explode.super.onBackPressed();
//                    }
              //  });
        TransitionManager.beginDelayedTransition(rvList, set);

        // remove all views from Recycler View
        rvList.setAdapter(null);
    }

    public class Get_Counter extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {

         //  Toast.makeText(T_explode.this,""+ z,Toast.LENGTH_LONG).show();
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
           // myItem.setTitle(getString(R.string.count)+t_counter);
            tv_counter_5.setText(String.valueOf(t_counter));
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    myItem.setTitle("count  "+t_counter);
//                }
//            }, 5000);
            Get_Counter get_counter=new Get_Counter();
            get_counter.execute("");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            t_counter=0;
         //   myItem.setTitle(getString(R.string.count)+"  "+t_counter);
            tv_counter_5.setText(String.valueOf(t_counter));
            super.onPostExecute(s);
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


    @Override
    protected void onStop() {


        Update_Counter update_counter=new Update_Counter();
        update_counter.execute("");
//        view=findViewById(R.id.menu_back);
//        letsExplodeIt(view);
     //   if(BuildConfig.FLAVOR.equals("free")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
    //    }
        super.onStop();
    }

    /******************************display counter***************************/
@Override
public boolean onNavigateUp(){
    //finish();
   // view=findViewById(R.id.menu_back);
   // letsExplodeIt(view);
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
       // myItem.setTitle(getString(R.string.count)+t_counter);
        tv_counter_5.setText(String.valueOf(t_counter));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
               // String shareBodyText = "Check it out.\n\n"+" https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.free  ";

                shared_Menu sharedMenu=new shared_Menu();
                String flaver =BuildConfig.FLAVOR;
                String shareBodyText =sharedMenu.flavor(flaver);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;
            case R.id.menu_back:
//view=findViewById(R.id.menu_back);
//letsExplodeIt(view);
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
                Intent intent=new Intent(T_explode.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(T_explode.this, Help_Activity.class);
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
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem myItem = menu.findItem(R.id.menu_counter);
//        myItem.setTitle("Total counter="+Zeker_counter_SQL);
//        return true;
//    }
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
