package com.example.asus.livelong;


        import android.content.Intent;
        import android.database.Cursor;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.jjoe64.graphview.GraphView;
        import com.jjoe64.graphview.series.DataPoint;
        import com.jjoe64.graphview.series.LineGraphSeries;

        import java.nio.Buffer;
        import java.util.ArrayList;
        import java.util.Iterator;

public class Temperature_Graph extends AppCompatActivity {


    LineGraphSeries<DataPoint> series;
    ArrayList<Double>TemparatureArray;
    GraphView graphTemparature;


    Button clearData;

    double x,y, temparatureValue ;
    String temparatureString;

    int numberOfPoints,intdex=0;
    double temparatureArray[];

    Temperature_Database temparature_database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temparature_graph);
        getSupportActionBar().setTitle("Temperature Statistics");
        temparature_database = new Temperature_Database(this);


        graphTemparature = (GraphView) findViewById(R.id.temparature);
        clearData = (Button)findViewById(R.id.Clear_temparature);
        series = new LineGraphSeries<DataPoint>();

        DrawTemparatureGraph();

        //clear data in database
        clearData.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                temparature_database.deleteTable();
                //DrawTemparatureGraph();
                //Intent temp_graph = new Intent(Temperature_Graph.this, temperature.class);
                Toast.makeText(Temperature_Graph.this,"Cleared",Toast.LENGTH_SHORT).show();

                startActivity(getIntent());


//                AlertDialog.Builder dialog_box = new AlertDialog.Builder(Temperature_Graph.this);
//                View dialog_view = getLayoutInflater().inflate(R.layout.delete_values,null);
//
//                Button yes_button = (Button) findViewById(R.id.yes_button);
//                Button no_button = (Button) findViewById(R.id.no_button);
//
//
//                Toast.makeText(Temperature_Graph.this,"yyyyyyyyyyy",Toast.LENGTH_SHORT);
//                yes_button.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        //temparature_database.deleteTable();
//                        //DrawTemparatureGraph();
//
//                    }
//                });


//                yes_button.setOnClickListener(new View.OnClickListener(){
//                    public void onClick(View v){
//
//
//                        Toast.makeText(Temperature_Graph.this,"Profile Updated",Toast.LENGTH_SHORT).show();
//                    }
//                });


//
//                no_button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DrawTemparatureGraph();
//                    }
//                });
//

//                dialog_box.setView(dialog_view);
//                AlertDialog pofile_dilog_box = dialog_box.create();
//                pofile_dilog_box.show();


            }
        });


    }

    //draw graph
    public void DrawTemparatureGraph(){

        getAllData();

        x=0;
        for(int i=0 ;i <numberOfPoints;i++){
            x = x + 1;
            y = temparatureArray[i];

            series.appendData(new DataPoint(x,y),true,numberOfPoints);
        }
        graphTemparature.addSeries(series) ;
        series.setDrawDataPoints(true);


    }

    //get all data in an array
    void getAllData(){

        Cursor res = temparature_database.getAllData();


        numberOfPoints = res.getCount();


        if(numberOfPoints==0){
            Toast.makeText(this,"No Values",Toast.LENGTH_SHORT).show();

            return;
        }
        else {
            Toast.makeText(this,"Values",Toast.LENGTH_SHORT).show();
            temparatureArray =  new double[numberOfPoints];

        }

        intdex = 0;
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            temparatureArray[intdex++] = Double.parseDouble(res.getString(3));

        }

    }

}

