package com.example.asus.livelong;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class Navigation_Drawer extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton bloodPressure,profile,reminder,tempereture,nearBy,checkup,history, emergency;

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    Button signout, aboutus,chatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setTitle("LiveLong");
        setContentView(R.layout.activity_navigation__drawer);
        bloodPressure = (ImageButton) findViewById(R.id.BloodPressureButton);
        tempereture = (ImageButton) findViewById(R.id.TemperatureButton);
        profile = (ImageButton) findViewById(R.id.ProfileButton);
        reminder = (ImageButton) findViewById(R.id.ReminderButton);
        nearBy = (ImageButton) findViewById(R.id.NearbyButton);
        checkup = (ImageButton) findViewById(R.id.CheckupButton);
        history = (ImageButton) findViewById(R.id.HistoryButton);
        emergency = (ImageButton) findViewById(R.id.EmergencyButton);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(Navigation_Drawer.this, profile.class);
                startActivity(profile);
            }


        });
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reminder = new Intent(Navigation_Drawer.this, reminder.class);
                startActivity(reminder);
            }


        });

        bloodPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bp = new Intent(Navigation_Drawer.this, bloodPressure.class);
                startActivity(bp);
            }


        });

        nearBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(Navigation_Drawer.this, nearBy.class);
                startActivity(map);
            }


        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(Navigation_Drawer.this, emergency.class);
                startActivity(map);
            }


        });

        tempereture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chk = new Intent(Navigation_Drawer.this, temperature.class);
                startActivity(chk);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chk = new Intent(Navigation_Drawer.this, history.class);
                startActivity(chk);
            }


        });


        checkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chk = new Intent(Navigation_Drawer.this, checkUp.class);
                startActivity(chk);
            }


        });
        //getSupportActionBar().setTitle("Live Long");

        //Set the fragment initially
        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_contrainer,fragment);
        fragmentTransaction.commit();


        signout = (Button) findViewById(R.id.LogOutbutton);
        aboutus = (Button) findViewById(R.id.AboutUsbutton);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chatbot) {


            Intent chk = new Intent(Navigation_Drawer.this, chatbot.class);
            startActivity(chk);


        } else if (id == R.id.nav_aboutUs) {
            Intent s = new Intent(Navigation_Drawer.this, aboutUs.class);
            startActivity(s);


        } else if (id == R.id.nav_signout) {
                    signOut();
                    Intent s = new Intent(Navigation_Drawer.this, firstscreen.class);
                    startActivity(s);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void signOut()
    {
        FirebaseAuth.getInstance().signOut();
    }
}
