package com.example.asus.livelong;


        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.v7.app.ActionBarActivity;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.Display;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import static com.example.asus.livelong.R.drawable.e_profile;

public class profile extends AppCompatActivity {

    Button editprofilebutton;

    private EditText profile_name, profile_age, profile_bloodgroup;
    private Button save;

    DatabaseReference databaseReference;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");

        profile_name = (EditText) findViewById(R.id.profile_name);
        profile_age = (EditText) findViewById(R.id.profile_age);
        profile_bloodgroup = (EditText) findViewById(R.id.profile_bloodgroup);
        save = (Button) findViewById(R.id.save_profile_Button);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

       // nameText = (TextView) findViewById(R.id.user_name_TextView);
        //ageText = (TextView) findViewById(R.id.user_age_TextView);
    //  bloodGroupText =(TextView) findViewById(R.id.user_bloodgroup_TextView);

        //editprofilebutton = (Button) findViewById(R.id.edit_imageButton);
/////////////////////////////////////////////////////////////////////////////

        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                AddData();
                Toast.makeText(profile.this,"Saved",Toast.LENGTH_SHORT).show();
            }


        });





/*
        editprofilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog_box = new AlertDialog.Builder(profile.this);
                View dialog_view = getLayoutInflater().inflate(R.layout.edit_pofile_dialog_box,null);
                final EditText name_editText = (EditText) dialog_view.findViewById(R.id.Name_editText);
                final EditText age_editText = (EditText) dialog_view.findViewById(R.id.age_editText);
                final Spinner blood_group_spinner = (Spinner) dialog_view.findViewById(R.id.Blood_group_spinner);
                Button Save_Profile = (Button) dialog_view.findViewById(R.id.Save_Profile);


                Save_Profile.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){

                        name = name_editText.getText().toString();
                        ageString = age_editText.getText().toString();
                        bloodgroup = blood_group_spinner.getSelectedItem().toString();


                        if(!TextUtils.isEmpty(name)){
                            nameText.setText(name);
                        }
                        if(!TextUtils.isEmpty(ageString)){
                            ageText.setText(ageString);
                        }
                        if(!TextUtils.isEmpty(bloodgroup)){
                            bloodGroupText.setText(bloodgroup);
                        }

                        Toast.makeText(profile.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                    }
                });


                dialog_box.setView(dialog_view);
                AlertDialog pofile_dilog_box = dialog_box.create();
                pofile_dilog_box.show();
            }
        });

*/
    }

//    public void displayData(View view){
//        SharedPreferences sharepref = getSharedPreferences("userInfo",MODE_PRIVATE);
//        name = sharepref.getString("username",DEFAULT);
//        age = sharepref.getInt("userage",0);
//        bloodgroup = sharepref.getString("userBloodGroup",DEFAULT);
//
//
//
//        nameText.setText(name);
//        ageText.setText(age);
//        bloodGroupText.setText(bloodgroup);
//
//
//
//    }

public void AddData()
{
String Name = profile_name.getText().toString().trim();
String Age = profile_age.getText().toString().trim();
String BloodGroup = profile_bloodgroup.getText().toString().trim();

SaveData saveData = new SaveData(Name,Age,BloodGroup);

    databaseReference.setValue(saveData);
}

}
