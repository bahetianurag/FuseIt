package com.example.anuragbaheti.fuseit;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class alarm extends AppCompatActivity {
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        alarm a;
        public void setalact(alarm a)
        {
            this.a=a;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            a.ontimesel(hourOfDay,minute);
            // getDialog().cancel();

        }
    }
    PendingIntent pi;String alt;
    public void cancelAlarm(View view){
        SharedPreferences sp = this.getSharedPreferences("alarmfuseit",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        alt=sp.getString("alms","");
        Log.w("cancel",alt);

        //Toast.makeText(getBaseContext(),"ALARM CANCELLED",Toast.LENGTH_SHORT).show();
        if(alt.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(),"NO ALARM SET",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.w("cancel","else part");
            Toast.makeText(getBaseContext(),"ALARM CANCELLED",Toast.LENGTH_SHORT).show();
            //alt="";
            e.putString("alms","").commit();
            tv.setText("No Alarm Set");
            am.cancel(pi);
        }

    }


    public void ontimesel(int h,int min)
    {
        Calendar c= Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,h);
        c.set(Calendar.MINUTE,min);
        // c.set(Calendar.DATE,(c.get(Calendar.DATE)+1));

        long time = c.getTimeInMillis();
        if(time-System.currentTimeMillis()<-300000)
        {
            c.set(Calendar.DATE,(c.get(Calendar.DATE)+1));
        }
        time = c.getTimeInMillis();
        Log.w("date",time+" "+System.currentTimeMillis());
        Toast.makeText(this,"ALARM SET",Toast.LENGTH_SHORT).show();
        long intv = 24*3600000;
        Intent i=new Intent();
        i.setClass(this.getBaseContext(),alrmreceiver.class);
        pi=  PendingIntent.getBroadcast(this,25406901,i,PendingIntent.FLAG_CANCEL_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP,time,intv,pi);
        //am.setExact(AlarmManager.RTC_WAKEUP,time,pi);
        SharedPreferences sp = this.getSharedPreferences("alarmfuseit",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        String s="";

        if(h<10 && min<10)
            s="0"+h+":0"+min;
        else if(h<10)
            s="0"+h+":"+min;
        else if(min<10)
            s=h+":0"+min;
        else
            s=h+":"+min;
        e.putString("alms",s);
        e.commit();
        Log.w("setting alarm",s);
        tv.setText("Alarm 1 - "+s);
    }

    AlarmManager am;TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm);
        SharedPreferences sp = this.getSharedPreferences("alarmfuseit",Context.MODE_PRIVATE);
        alt= sp.getString("alms","");           // store the displaying string
        tv = (TextView) findViewById(R.id.almview);
        if(alt.equalsIgnoreCase(""))
        {
            tv.setText("No Alarm Set");
        }
        else
        {
            tv.setText("Alarm 1 - "+alt);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ALARM");
        am = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

    }





    public void showTimePickerDialog() {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setalact(this);
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.alarmadd) {
            showTimePickerDialog();
            return true;

        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

