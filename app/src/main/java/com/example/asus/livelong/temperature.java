package com.example.asus.livelong;

        import android.app.DatePickerDialog;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.text.DateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;


        import java.text.DateFormat;
        import java.util.Calendar;

public class temperature extends AppCompatActivity {

    TextView date,time,Temparature,dot,dateText,am_pm_text;
    EditText hour,minute,systolic,diastolic,temp ;
    Spinner am_pm;
    Button saveTemparature,recordTemparature,setDate;

    String dateString,timeString,HH,MIN,T,am_pmString;


    int dayInt,monthInt,yearInt,hourInt,minuteInt;
    double temparatureValue;
    boolean got=false;

    Calendar cal= Calendar.getInstance();
    DateFormat formatDate = DateFormat.getDateInstance();


    DatabaseReference databaseTemparature;
    Intent temparature_graph_class;

    Temperature_Database temparature_database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        getSupportActionBar().setTitle("Temperature");

        databaseTemparature = FirebaseDatabase.getInstance().getReference("Temparature_history");


        date = (TextView) findViewById(R.id.Date);
        setDate = (Button) findViewById(R.id.setDate);
        dateText = (TextView) findViewById(R.id.Date_editText);


        time = (TextView)findViewById(R.id.Time);
        hour = (EditText)findViewById(R.id.hour);
        dot = (TextView)findViewById(R.id.dot);
        minute = (EditText)findViewById(R.id.minute);
        am_pm = (Spinner)findViewById(R.id.am_pm);


        Temparature = (TextView) findViewById(R.id.Temparature);
        temp = (EditText) findViewById(R.id.Temp);

        saveTemparature = (Button) findViewById(R.id.saveTemparature);
        recordTemparature = (Button) findViewById(R.id.recordTemparature);

        temparature_graph_class = new Intent(temperature.this, Temperature_Graph.class);
        temparature_database = new Temperature_Database(this);


        got=false;

        setDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                getDate();
            }
        });

        saveTemparature.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                getTimeAndTemparature();

            }
        });

        recordTemparature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(temparature_graph_class);
            }


        });

    }

    public void getDate(){

        new DatePickerDialog(this,d, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayofMonth){

            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,monthofYear);
            cal.set(Calendar.DAY_OF_MONTH,dayofMonth);

            updateDate();

        }
    };

    public void updateDate(){
        dateText.setText(formatDate.format(cal.getTime()));

        dateString = dateText.getText().toString();
    }

    public void getTimeAndTemparature(){

        HH = hour.getText().toString().trim();
        MIN = minute.getText().toString().trim();
        am_pmString = am_pm.getSelectedItem().toString();
        T = temp.getText().toString().trim();

        if(!TextUtils.isEmpty(HH)&&!TextUtils.isEmpty(MIN) && !TextUtils.isEmpty(T) && !TextUtils.isEmpty(dateString) ){

            temparatureValue = Double.parseDouble(T);

            setValues();
            timeString = HH +":"+MIN+" "+am_pmString;
            insertDatabase();


            boolean isinserted=temparature_database.insertData(dateString,timeString,T);

            if(isinserted==true){
                Toast.makeText(this,"Temparature inserted into database",Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this,"not inserted",Toast.LENGTH_SHORT).show();

            }

            Toast.makeText(this,"Temparature Updated",Toast.LENGTH_SHORT).show();

            finish();
            startActivity(getIntent());


        }
        else {

            Toast.makeText(this,"FILL UP ALL REQUIRED FIELD ",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        }
    }


    public void setValues(){

        hourInt = Integer.parseInt(HH);
        minuteInt = Integer.parseInt(MIN);

        if(hourInt>12){
            hourInt=12;
            HH ="12";
        }
        else if(hourInt<1){
            hourInt=1;
            HH="1";
        }

        if(minuteInt>59){
            minuteInt=59;
            MIN="59";
        }
        else if(minuteInt<0){
            minuteInt=0;
            MIN="0";
        }
    }


    public void insertDatabase(){

        String id = databaseTemparature.push().getKey();

        Temperature_Update  addTemparature= new Temperature_Update(dateString,timeString,T);

        databaseTemparature.child(id).setValue(addTemparature);
    }

}
