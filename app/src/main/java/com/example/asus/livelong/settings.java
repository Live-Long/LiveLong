package com.example.asus.livelong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class settings extends AppCompatActivity {
    Button signout, aboutus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        signout = (Button) findViewById(R.id.LogOutbutton);
        aboutus = (Button) findViewById(R.id.AboutUsbutton);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                Intent s = new Intent(settings.this, firstscreen.class);
                startActivity(s);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent s = new Intent(settings.this, aboutUs.class);
                startActivity(s);
            }
        });
    }

    private void signOut()
    {
        FirebaseAuth.getInstance().signOut();
    }

}