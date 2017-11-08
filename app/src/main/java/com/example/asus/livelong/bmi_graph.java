package com.example.asus.livelong;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Math.pow;

public class bmi_graph extends AppCompatActivity {


    LineGraphSeries<DataPoint> series; //take points to draw the graph

    GraphView graphTemparature;


    Button clearData;


    //x and y poin in graph
    Double x,y;

    int numberOfPoints,intdex=0;
    double heightArray[],weightArray[];

    bmi_database BMI_database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_graph);


        BMI_database = new bmi_database(this);

        showData();




        graphTemparature = (GraphView) findViewById(R.id.bmi_graphView);
        series = new LineGraphSeries<DataPoint>();

        DrawBMIGraph();


    }


    //initialize the points of

    public void DrawBMIGraph(){
        x=0.0; //x and y poin in graph
        y=0.0;


        for(int i=0 ;i <numberOfPoints;i++){
            x = x + 1;
            double z =Math.pow(heightArray[i],2);

            y = weightArray[i]/z ;

            series.appendData(new DataPoint(x,y),true,numberOfPoints);
        }
        graphTemparature.addSeries(series) ;
        series.setDrawDataPoints(true);


    }


    // take data in array from sqlLite databasae

    void showData() {

        Cursor res = BMI_database.getAllData();
        //Toast.makeText(this,"No Values",Toast.LENGTH_SHORT);

        numberOfPoints = res.getCount();


        if(numberOfPoints==0){
            Toast.makeText(this,"No Values",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            heightArray =  new double[numberOfPoints];
            weightArray = new double[numberOfPoints];

            intdex = 0;
            // StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()){

                heightArray[intdex] = Double.parseDouble(res.getString(2));
                weightArray[intdex] = Double.parseDouble(res.getString(3));

                intdex++ ;



            }


        }







    }





}