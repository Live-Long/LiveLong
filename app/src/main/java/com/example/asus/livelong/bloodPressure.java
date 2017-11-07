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
        import android.widget.ToggleButton;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;


        import java.text.DateFormat;
        import java.util.Calendar;

public class bloodPressure extends AppCompatActivity  {

    TextView date,time,BP,dot,dateText,am_pm_text;
    EditText hour,minute,systolic,diastolic,temp ;
    Spinner am_pm;
    Button saveBP,recordBP,setDate;

    String dateString,timeString,HH,MIN,T,am_pmString,systolicString, diastolicString;
    boolean got;

    int dayInt,monthInt,yearInt,hourInt,minuteInt;
    double systolicValue, diastolicValue;

    double hd,md,sd,dd;

    Calendar cal= Calendar.getInstance();
    DateFormat formatDate = DateFormat.getDateInstance();



    DatabaseReference databaseBP;
    Intent BP_graph_class;

    BP_Database bp_database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        getSupportActionBar().setTitle("Blood Pressure");
        databaseBP = FirebaseDatabase.getInstance().getReference("BP_history");


        date = (TextView) findViewById(R.id.Date);
        setDate = (Button) findViewById(R.id.setDate);
        dateText = (TextView) findViewById(R.id.Date_editText);


        time = (TextView)findViewById(R.id.Time);
        hour = (EditText)findViewById(R.id.hour);
        dot = (TextView)findViewById(R.id.dot);
        minute = (EditText)findViewById(R.id.minute);
        am_pm = (Spinner)findViewById(R.id.am_pm);


        BP = (TextView) findViewById(R.id.BP);
        systolic = (EditText) findViewById(R.id.Systolic);
        diastolic = (EditText) findViewById(R.id.Diastolic);

        saveBP = (Button) findViewById(R.id.saveBP);
        recordBP = (Button) findViewById(R.id.recordBP);

       BP_graph_class = new Intent(bloodPressure.this, BP_Graph.class);
        bp_database = new BP_Database(this);


        setDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                getDate();
            }
        });



        saveBP.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                getTimeAndBP();


            }
        });
        recordBP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  startActivity(BP_graph_class);

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



    public void getTimeAndBP(){

        HH = hour.getText().toString().trim();

        MIN = minute.getText().toString().trim();


        am_pmString = am_pm.getSelectedItem().toString();
        systolicString = systolic.getText().toString().trim();
        diastolicString = diastolic.getText().toString().trim();


        if(!TextUtils.isEmpty(HH)&&!TextUtils.isEmpty(MIN) && !TextUtils.isEmpty(dateString) && !TextUtils.isEmpty(systolicString) && !TextUtils.isEmpty(diastolicString) ){

            BPcheck();

            got=true;
            timeString = HH +":"+MIN+" "+am_pmString;
            insertBPDatabase();
            Toast.makeText(this,"Blood Pressure Updated",Toast.LENGTH_SHORT).show();

            boolean isinserted=bp_database.insertData(dateString,timeString,systolicString,diastolicString);

            if(isinserted==true){
                Toast.makeText(this,"Inserted",Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this,"Not Inserted",Toast.LENGTH_SHORT).show();

            }

            finish();
            startActivity(getIntent());




        }
        else {

            got = false;
            Toast toast = Toast.makeText(this,"FILL UP ALL REQUIRED FIELD ",Toast.LENGTH_SHORT);
            toast.show();
            finish();
            startActivity(getIntent());
        }
    }

    public void BPcheck(){
        systolicValue = Integer.parseInt(systolicString);
        diastolicValue = Integer.parseInt(diastolicString);

        if(systolicValue< diastolicValue){
            Toast t1 =Toast.makeText(this,"Invalid! give Input again",Toast.LENGTH_SHORT);
            t1.show();
            finish();
            startActivity(getIntent());
        }

        if((systolicValue >= 80 && systolicValue<=120) && (diastolicValue>=60 && diastolicValue<=80)){

            Toast t2= Toast.makeText(this,"Normal!",Toast.LENGTH_SHORT);
            t2.show();
        }
        else if((systolicValue>=120 && systolicValue<140)||(diastolicValue>=80 && diastolicValue<=89)){
            Toast t3 = Toast.makeText(this,"PreHypertension",Toast.LENGTH_SHORT);
            t3.show();
        }
        else if((systolicValue>=140 && systolicValue<160)||(diastolicValue>=90 && diastolicValue<=99)){
            Toast t4=Toast.makeText(this,"High Blood Pressure(Hypertension stage-I)",Toast.LENGTH_SHORT);
            t4.show();
        }
        else if((systolicValue>=160 && systolicValue<150)||(diastolicValue>=100 && diastolicValue<=110)){
            Toast t5 = Toast.makeText(this,"High Blood Pressure(Hypertension stage-II)",Toast.LENGTH_SHORT);
            t5.show();
        }

        else if((systolicValue>=180 )||(diastolicValue>=110)){
            Toast t5 =Toast.makeText(this,"Seek Imergency Cure",Toast.LENGTH_SHORT);
            t5.show();
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
        hour.setText(HH);
        minute.setText(MIN);

    }



    public void insertBPDatabase(){

        String id = databaseBP.push().getKey();


        BP_Update updateinDatabase = new BP_Update(id,dateString,timeString,systolicString,diastolicString);

        databaseBP.child(id).setValue(updateinDatabase);

    }


}
