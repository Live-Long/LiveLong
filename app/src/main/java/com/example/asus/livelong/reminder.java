package com.example.asus.livelong;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.NOTIFICATION_SERVICE;

public class reminder extends AppCompatActivity {

    private Alarm currentAlarm;

    private ToggleButton toggleButton1;
    private EditText editNotes1;
    private EditText dateText1;
    private ToggleButton toggleButton2;
    private EditText editNotes2;
    private EditText dateText2;
    private ToggleButton toggleButton3;
    private EditText editNotes3;
    private EditText dateText3;

    private PendingIntent pi;
    private BroadcastReceiver br;
    private AlarmManager am;
    TimePickerDialog timePicker;
    private Alarm[] alarms = new Alarm[10];
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reminder );
        getSupportActionBar().setTitle("Reminder");
        initializeApp();
    }
    public void toastIt( String msg ) {
        Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_SHORT ).show();
    }
    private void initializeApp() {
        am = (AlarmManager)( this.getSystemService( Context.ALARM_SERVICE ) );
        toggleButton1 = (ToggleButton)findViewById( R.id.toggleButton1 );
        editNotes1 = (EditText)findViewById( R.id.editNotes1 );
        dateText1 = (EditText)findViewById( R.id.editText1 );
        toggleButton2 = (ToggleButton)findViewById( R.id.toggleButton2 );
        editNotes2 = (EditText)findViewById( R.id.editNotes2 );
        dateText2 = (EditText)findViewById( R.id.editText2 );
        toggleButton3 = (ToggleButton)findViewById( R.id.toggleButton3);
        editNotes3 = (EditText)findViewById( R.id.editNotes3 );
        dateText3 = (EditText)findViewById( R.id.editText3 );



        // BROADCAST RECEIVER **********************************************
        br = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent ) {
                String notes = "";
                Bundle extras = intent.getExtras();
                if( extras != null ) {
                    notes = extras.getString( "notes" );
                }
                toastIt("Wake Up!");
                // Reschedule a new alarm if this is recurring
                createNotification( notes );
            }
        };
        // Register the receiver and create the intents for passing information
        registerReceiver( br, new IntentFilter("com.example.asus.livelong"));

        // Create all of my Alarm Objects
        // TODO: Set date for each calendar from file
        alarms[0] = new Alarm( this, editNotes1, dateText1, toggleButton1, 0, Calendar.getInstance() );
        alarms[0].setTags();

        alarms[1] = new Alarm( this, editNotes2, dateText2, toggleButton2, 1, Calendar.getInstance() );
        alarms[1].setTags();

        alarms[2] = new Alarm( this, editNotes3, dateText3, toggleButton3, 2, Calendar.getInstance() );
        alarms[2].setTags();
    }
        int which=0;

    public void toggleAlarm( View v ) {
        if( v.getId() == toggleButton1.getId() ) {
            if( toggleButton1.isChecked() ) {
                alarms[0].setNotes( editNotes1.getText().toString() );
                am.set( AlarmManager.RTC, c.getTimeInMillis(), alarms[0].pi );

                toastIt( "Alarm On");
                which=1;

            } else {

                toastIt( "Alarm Off");
                which=0;
            }
        } else if( v.getId() == toggleButton2.getId() ) {
            if( toggleButton2.isChecked() ) {
                alarms[1].setNotes( editNotes2.getText().toString() );
                am.set( AlarmManager.RTC, c.getTimeInMillis(), alarms[1].pi );

                toastIt( "Alarm On");
                which=2;
            } else {

                toastIt( "Alarm Off");
                which=0;
            }
        }
        else if( v.getId() == toggleButton3.getId() ) {
            if( toggleButton3.isChecked() ) {
                alarms[2].setNotes( editNotes3.getText().toString() );
                am.set( AlarmManager.RTC, c.getTimeInMillis(), alarms[2].pi );

                toastIt( "Alarm On");
                which=3;
            } else {

                toastIt( "Alarm Off");
                which=0;
            }
        }

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
            Alarm am = (Alarm)currentAlarm;

            am.cal.set( Calendar.YEAR, year );
            am.cal.set( Calendar.MONTH, monthOfYear );
            am.cal.set( Calendar.DAY_OF_MONTH, dayOfMonth );

            timePicker.show();  // Launches the TimePicker right after the DatePicker closes
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet( TimePicker view, int hour, int minute ) {
            Alarm am = (Alarm)currentAlarm;
            am.cal.set( Calendar.HOUR, hour );
            am.cal.set( Calendar.MINUTE, minute );
            am.updateDateTime();
        }
    };

    public void dateOnClick( View view ) {
        Alarm am = (Alarm)view.getTag();
        currentAlarm = am;
        timePicker = new TimePickerDialog( reminder.this, time,
                am.cal.get( Calendar.HOUR ),
                am.cal.get( Calendar.MINUTE ), false );
        new DatePickerDialog( reminder.this, date,
                am.cal.get( Calendar.YEAR ),
                am.cal.get( Calendar.MONTH ),
                am.cal.get( Calendar.DAY_OF_MONTH ) ).show();
    }

    private void createNotification( String notes ) {
        // prepare intent which is triggered if the notification is selected
        Intent intent = new Intent( this, reminder.class );
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String whichMed = "";


        if(which==1){
            whichMed=editNotes1.getText().toString();
        }
        else if(which==2){
            whichMed=editNotes2.getText().toString();
        }
        else if (which==3){
            whichMed=editNotes3.getText().toString();
        }

        Notification n = new Notification.Builder(this)
                .setContentTitle( "Take Medicine: " + whichMed)
                .setContentText( notes )
                .setSmallIcon( R.drawable.reminder )
                .setContentIntent( pIntent )
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager =
                (NotificationManager)getSystemService( NOTIFICATION_SERVICE );
        notificationManager.notify( 0, n );
        ////////Play Sound//////
        MediaPlayer mp = MediaPlayer.create(reminder.this, R.raw.reminder);
        mp.start();
        ////////////
    }

    @Override
    protected void onDestroy() {
        am.cancel( pi );
        unregisterReceiver( br );
        super.onDestroy();
    }
}