package com.example.asus.livelong;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class emergency extends AppCompatActivity {

    Button makeTheCall, saveButton;


    EditText nameEditText;
    EditText phoneNumberEditText;

    TextView nameTextView, phoneNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        getSupportActionBar().setTitle("Emergency");

        makeTheCall = (Button) findViewById(R.id.emergencycallbutton);
        saveButton = (Button) findViewById(R.id.savebutton);
        nameEditText = (EditText) findViewById(R.id.name_editText);
        phoneNumberEditText = (EditText) findViewById(R.id.phnnumber_editText);
        nameTextView = (TextView) findViewById(R.id.name_textView);
        phoneNumberTextView = (TextView) findViewById(R.id.phnnumber_textView);


        final SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();


        String nameString = prefs.getString("Name", null);
        final String phoneNumber = prefs.getString("Phone_number", null);




        nameTextView.setText(nameString);
        phoneNumberTextView.setText(phoneNumber);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameEditText.getText().toString().trim();
                String phone_number = phoneNumberEditText.getText().toString().trim();

                nameTextView.setText(name);
                phoneNumberTextView.setText(phone_number);

                editor.putString("Name", name);
                editor.putString("Phone_number", phone_number);
                editor.commit();


                Toast.makeText(emergency.this,"Saved",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());



            }
        });


        final String finalPhoneNumber = phoneNumber;
        makeTheCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phoneNumber));
                if (ActivityCompat.checkSelfPermission(emergency.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });

    }
}
