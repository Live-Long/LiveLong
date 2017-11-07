package com.example.asus.livelong;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.ImageButton;
        import android.view.View;


public class dock extends AppCompatActivity {

    ImageButton bloodPressure,profile,reminder,settings,tempereture,nearBy,checkup,history, emergency, chatbot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dock);
        bloodPressure = (ImageButton) findViewById(R.id.BloodPressureButton);
        tempereture = (ImageButton) findViewById(R.id.TemperatureButton);
        profile = (ImageButton) findViewById(R.id.ProfileButton);
        reminder = (ImageButton) findViewById(R.id.ReminderButton);
        nearBy = (ImageButton) findViewById(R.id.NearbyButton);
        checkup = (ImageButton) findViewById(R.id.CheckupButton);
        history = (ImageButton) findViewById(R.id.HistoryButton);
        emergency = (ImageButton) findViewById(R.id.EmergencyButton);
        chatbot = (ImageButton) findViewById(R.id.chatbot_button);

        settings=(ImageButton)findViewById(R.id.Settings);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(dock.this, profile.class);
                startActivity(profile);
            }


        });
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reminder = new Intent(dock.this, reminder.class);
                startActivity(reminder);
            }


        });

        bloodPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bp = new Intent(dock.this, bloodPressure.class);
                startActivity(bp);
            }


        });

        nearBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(dock.this, nearBy.class);
                startActivity(map);
            }


        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(dock.this, emergency.class);
                startActivity(map);
            }


        });

        checkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chk = new Intent(dock.this, profile.class);
                startActivity(chk);
            }


        });

        tempereture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chk = new Intent(dock.this, temperature.class);
                startActivity(chk);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chk = new Intent(dock.this, history.class);
                startActivity(chk);
            }


        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chk = new Intent(dock.this, settings.class);
                startActivity(chk);
            }


        });

        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chk = new Intent(dock.this, chatbot.class);
                startActivity(chk);
            }


        });


        checkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chk = new Intent(dock.this, checkUp.class);
                startActivity(chk);
            }


        });

    }
}




