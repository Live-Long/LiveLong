package com.example.asus.livelong;


        import android.database.Cursor;
        import android.graphics.Color;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.jjoe64.graphview.GraphView;
        import com.jjoe64.graphview.series.DataPoint;
        import com.jjoe64.graphview.series.LineGraphSeries;

        import java.util.ArrayList;

public class BP_Graph extends AppCompatActivity {

    LineGraphSeries<DataPoint> BP_Systolic_series;
    LineGraphSeries<DataPoint> BP_Diastolic_series;

    GraphView graphTemparature;


    Button clearData;

    double x,y,a,b ;
    String temparatureString;

    int numberOfPoints,intdex=0;
    double BP_Systolic_Array[],BP_Diastolic_Array[];

    BP_Database bp_database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp__graph);
        getSupportActionBar().setTitle("Blood Pressure Statistics");
        //Toast.makeText(this,"Unknown",Toast.LENGTH_LONG).show();


        bp_database = new BP_Database(this);

        showData();


        clearData = (Button)findViewById(R.id.Clear_BP);


        graphTemparature = (GraphView) findViewById(R.id.BP_Graph);
        BP_Systolic_series = new LineGraphSeries<DataPoint>();
        BP_Diastolic_series = new LineGraphSeries<DataPoint>();
        //temparatureArraySize = TemparatureArray.size();

        DrawTemparatureGraph();

        clearData.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                bp_database.deleteTable();
                Toast.makeText(BP_Graph.this,"Cleared",Toast.LENGTH_SHORT).show();

                startActivity(getIntent());

            }
        });
    }

    public void DrawTemparatureGraph(){
        x=0;

        for(int i=0 ;i <numberOfPoints;i++){
            x = x + 2;
            y = BP_Systolic_Array[i];
            b = BP_Diastolic_Array[i];

            //Toast.makeText(this,y,Toast.LENGTH_SHORT).show();


            BP_Systolic_series.appendData(new DataPoint(x,y),true,numberOfPoints);
            BP_Diastolic_series.appendData(new DataPoint(x,b),true,numberOfPoints);
        }
        graphTemparature.addSeries(BP_Systolic_series) ;
        graphTemparature.addSeries(BP_Diastolic_series) ;
        //graphTemparature.setBackgroundColor(Color.GRAY);

        BP_Systolic_series.setDrawDataPoints(true);
        BP_Diastolic_series.setDrawDataPoints(true);
        BP_Diastolic_series.setColor(Color.RED);


    }


    void showData(){

        Cursor res = bp_database.getAllData();

        numberOfPoints = res.getCount();

        if(numberOfPoints==0){
            Toast.makeText(this,"No Values",Toast.LENGTH_SHORT).show();

            viewAll("Error","Not found");
            return;
        }
        else {

            BP_Systolic_Array =  new double[numberOfPoints];
            BP_Diastolic_Array =  new double[numberOfPoints];

        }

        intdex = 0;
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            BP_Systolic_Array[intdex] = Double.parseDouble(res.getString(3));
            BP_Diastolic_Array[intdex] = Double.parseDouble(res.getString(4));
            intdex++;

//            buffer.append(" ID :"+ res.getString(0)+"\n");
//            buffer.append(" Date :"+ res.getString(1)+"\n");
//            buffer.append(" Time :"+ res.getString(2)+"\n");
//            buffer.append(" Temparature :"+ res.getString(3)+"\n");

        }

        //viewAll("Data",buffer.toString());



    }

    public void viewAll(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
    }
}
