package com.smartsoftwaresolutions.rosary2.Duaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

//import com.facebook.stetho.Stetho;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.rosary2.BuildConfig;
import com.smartsoftwaresolutions.rosary2.DataBase.DataHelper;
import com.smartsoftwaresolutions.rosary2.Duaa_CRUD.Duaa_item;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.MainActivity;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Setting;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;
import com.smartsoftwaresolutions.rosary2.shared_Menu;
import com.smartsoftwaresolutions.rosary2.video.Video_Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Duaa extends AppCompatActivity {
    public static final String PREFS_NAME = "Saved_Type";
    DataHelper AndPOS_dbh;
    SQLiteDatabase AndPOS_db;
    Duaa_Adapter ADA;
    ListView Lv_Duaa;
    MenuItem myItem;
    private AdView mAdView;
    Button btn_add_edit_dua,btn_video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duaa);
        btn_add_edit_dua=findViewById(R.id.btn_add_edit_dua);
        btn_video=findViewById(R.id.btn_video);
        btn_add_edit_dua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Duaa.this, Duaa_item.class);
                startActivity(intent);
            }
        });
        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Duaa.this, Video_Activity.class);
                startActivity(intent);
            }
        });
        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
      //  if(BuildConfig.FLAVOR.equals("Free")){
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });


            mAdView = findViewById(R.id.adView13);


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
       // }

     //   Stetho.initializeWithDefaults(this);
        // we create an instance of data base helper  database this will create the database
        AndPOS_dbh = new DataHelper(getApplicationContext());
        // and we connect the data to the data base helper and specify its use as a writable
        AndPOS_db = AndPOS_dbh.getWritableDatabase();
        Lv_Duaa=findViewById(R.id.CL_D);
        FillList fillList=new FillList();
        fillList.execute();
        Lv_Duaa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Duaa_info Selected_Tasbeh;
                Selected_Tasbeh = ADA.getItem( position );

                String Tasbeh_value_name = null;
                String Tasbeh_value_Dis = null;
                String Tasbeh_ID=null;

                if (Selected_Tasbeh != null) {
                    Tasbeh_value_name = Selected_Tasbeh.getU_nameList();
                    Tasbeh_value_Dis = Selected_Tasbeh.getU_Diss_List();
                    Tasbeh_ID=Selected_Tasbeh.getU_id_List();
                    // Writing data to SharedPreferences
                    SharedPreferences SpType = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = SpType.edit();
                    editor.putString("TypeD", Tasbeh_value_name);
                    editor.putString("Type_nativeD", Tasbeh_value_Dis);
                    editor.putString("Type_IDD", Tasbeh_ID);
                    // editor.putString("start","1");
                    editor.commit();
                }
                Intent intent=new Intent(Duaa.this, Show_Duaa.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        FillList fillList=new FillList();
        fillList.execute();
    }

    public class FillList extends AsyncTask<String, String, String> {

        String z = "";

        // List<Map<String, String>> Custlist  = new ArrayList<Map<String, String>>();
        List<Duaa_info> Tasbeh_list = new ArrayList<Duaa_info>();
        // List<Map<String ,String >> CustListSQL=new ArrayList<Map<String,String>>;

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onPostExecute(String r) {


            Toast.makeText( Duaa.this, r, Toast.LENGTH_SHORT ).show();
            // reading from the keys that we will use to raed from the customer list they must be the same as a hash map keys


            // the ada is binding all together
            // ADA = new SimpleAdapter(SearchCustomer.this, Custlist, R.layout.customer_element, from, views);
            ADA = new Duaa_Adapter(Tasbeh_list, Duaa.this );
            // put them in the list view



            Lv_Duaa.setAdapter( ADA );


        }

        @Override
        protected String doInBackground(String... params) {

            // we specify the database helper and the database and set the database to readable
            // then we write a select statment
            //   String query_SQL = "select * from Family_Tbl ";
            String query_SQL = "select * from Duaa_Tbl ";
            // we run this select statment by creating a cursor and execute it
            Cursor cursor = AndPOS_db.rawQuery( query_SQL, null );
            // if the result of the execute statment have data then the if statment is true and we move the cursor to the first row
            if (cursor.moveToFirst()) {
                do {
                    // we create a hash map with string for the key and string for the data
                    HashMap<String, String> map = new HashMap<String, String>();
                    String Zeker_Id, Zeker_Name_Arabic;
                    String Zeker_dis;
                 //   String Zeker_counter;
                    // we use put method to save the data in that map and we use the column name as the key
                    Zeker_Id = cursor.getString( cursor.getColumnIndex( "Duaa_Id" ) );
                    Zeker_Name_Arabic = cursor.getString( cursor.getColumnIndex( "Duaa_Name" ) );
                    Zeker_dis=cursor.getString(cursor.getColumnIndex("Duaa_Dis"));
                   // Zeker_counter= cursor.getString(cursor.getColumnIndex("Zeker_count"));

                    Duaa_info Tasbeh_Buton=new Duaa_info(Zeker_Id,Zeker_Name_Arabic,Zeker_dis);
                    // the other two empty string is for the text color and box color
                    //  Tasbeh_info Tasbeh_Buton = new Tasbeh_info( Zeker_Id, Zeker_Name_Arabic );


                    // we add the saved map to the list one by one
                    this.Tasbeh_list.add( Tasbeh_Buton );
                    // this.CustListSQL.add(map);

                } while (cursor.moveToNext());
                cursor.close();
                z = "Success";
            } else {
                z = "Error retrieving data from table";
            }


            return z;
        }


    }

    //menu
    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
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
               // String shareBodyText = "Check it out.\n\n"+" https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.free";
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
                Intent intent=new Intent(Duaa.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(Duaa.this, Help_Activity.class);
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

