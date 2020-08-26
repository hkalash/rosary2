package com.smartsoftwaresolutions.rosary2.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

//import com.facebook.stetho.Stetho;
import com.smartsoftwaresolutions.rosary2.BuildConfig;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.MainActivity;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Setting;
import com.smartsoftwaresolutions.rosary2.shared_Menu;

import java.util.Locale;

public class Notefication_Activity extends AppCompatActivity {
    public PendingIntent pendingIntent;
    Spinner spinner1;
    SharedPreferences SpType ;
    int Sp_time_interval,time;
    Button save_time;

    public static final String PREFS_NAME = "Saved_Type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notefication_activity);

     //   Stetho.initializeWithDefaults(this);


        SpType=getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = SpType.edit();
        // read from SP
        Sp_time_interval=SpType.getInt("interval",4);// 4 hours

        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        spinner1 =findViewById(R.id.spinner1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
 time = Integer.parseInt(adapterView.getItemAtPosition(pos).toString());

                Toast.makeText(Notefication_Activity.this,
                        getString(R.string.we_will_notify) + adapterView.getItemAtPosition(pos).toString()+getString(R.string.hour),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        save_time=findViewById(R.id.save_time);
        save_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // write to SP
                editor.putInt("interval",  time);
                editor.apply();
                // if the user change the time intervel the running service shoud be stoped
                // then the servivece will be start with new intervel
                cancel();
                start();
            }
        });

        findViewById(R.id.startAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        findViewById(R.id.stopAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

//        findViewById(R.id.stopAlarmAt10).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startAt10();
//            }
//        });
    }

    public void start() {
        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
      int  time_interval=SpType.getInt("interval",4);
        //int interval = 8000;
        int interval = time_interval*3600000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, R.string.alarm_set, Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(this, R.string.alarm_cancel, Toast.LENGTH_SHORT).show();
    }

//    public void startAt10() {
//        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        //20 is the mint
//        //60 is the secound in one mint
//        //1000 is the mili secound in one secound
//        int interval = 1000 * 60 * 20;
//
//        /* Set the alarm to start at 10:30 AM */
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 10);
//        calendar.set(Calendar.MINUTE, 30);
//
//        /* Repeating on every 20 minutes interval */
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 20, pendingIntent);
//    }
// Action Bar actions
@Override
public boolean onNavigateUp(){
    finish();
    return true;
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);

        MenuItem english=menu.findItem(R.id.action_English);
        MenuItem arabic=menu.findItem(R.id.action_Arabic);
        english.setVisible(false);
        arabic.setVisible(false);

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
                String flaver = BuildConfig.FLAVOR;
                String shareBodyText =sharedMenu.flavor(flaver);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;
            case R.id.action_Arabic:
                setLocale("ar");

                Toast.makeText(this, R.string.la, Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_English:
                setLocale("en_US");

                Toast.makeText(this, R.string.le, Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(Notefication_Activity.this, Help_Activity.class);
                startActivity(intent2);
                return true;
//            case R.id.action_reminder:
//                Intent intent3=new Intent(Notefication_Activity.this, Notefication_Activity.class);
//                startActivity(intent3);
//                return true;
            case R.id.menu_setting:
                Intent intent=new Intent(Notefication_Activity.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.menu_back:

                finish();
                return true;

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

