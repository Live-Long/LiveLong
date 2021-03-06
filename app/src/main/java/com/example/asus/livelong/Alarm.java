package com.example.asus.livelong;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Alarm {
    Intent alarmIntent;
    PendingIntent pi;
    EditText notesText, dateText;
    ToggleButton tb;
    Integer alarmID;
    Context context;
    Calendar cal;

    public Alarm( Context context,
                  EditText notesText,
                  EditText dateText,
                  ToggleButton tb,
                  Integer alarmID,
                  Calendar cal ) {
        this.alarmID = alarmID;
        this.context = context;
        this.notesText = notesText;
        this.dateText = dateText;
        this.tb = tb;
        this.cal = cal;

        this.alarmIntent = new Intent( "com.example.asus.livelong" );
        alarmIntent.putExtra( "notes", this.notesText.getText().toString() );
        this.pi = PendingIntent.getBroadcast( context, this.alarmID,
                this.alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT );
        updateDateTime();
    }

    // Used to get the various edit fields and buttons associated with this alarm.
    public void setTags() {
        notesText.setTag( this );
        dateText.setTag( this );
        tb.setTag( this );
    }

    public void setNotes( String notes ) {
        notesText.setText( notes );
        alarmIntent.putExtra( "notes", notesText.getText().toString() );
    }

    public void updateDateTime() {
        String myFormat = "MM-dd-yy hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat( myFormat, Locale.US );
        dateText.setText( sdf.format( cal.getTime() ) );
    }

}