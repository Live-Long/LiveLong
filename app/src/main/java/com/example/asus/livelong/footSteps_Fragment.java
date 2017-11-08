
package com.example.asus.livelong;



import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/*
*count steps when you walk... u can set a goal that u will walk this number of steps.. */

public class footSteps_Fragment extends Fragment implements SensorEventListener {

    SensorManager sensorManager;
    boolean running= false ;

    TextView steps,stepgoalTexView;
    Button restartButton, saveButton;
    EditText stepgoalEditText;


    private int stepsInSensor = 0;
    private int stepsAtReset;
    String stepgoalString;
    double stepgoalNumber=0;
    String stepinTextView;
    double stepsnow=0;

    TextToSpeech goalachievement;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.footsteps_fragment,container,false);

        steps = (TextView) view.findViewById(R.id.step_count);  //display your every step count
        restartButton = (Button) view.findViewById(R.id.restart_button);  //restart counting
        stepgoalTexView = (TextView) view.findViewById(R.id.stepgoal_textView); //show your goal
        stepgoalEditText = (EditText) view.findViewById(R.id.stepgoal_editText); //take input of your number for steps you want to acheive
        saveButton = (Button) view.findViewById(R.id.save_button);   // save your goal

        final SharedPreferences prefs =  getActivity().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);


        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE); // get sense of your movement



        stepsAtReset = prefs.getInt("stepsAtReset", 0);


        final SharedPreferences.Editor editor= prefs.edit();   // save your goal in shared preference
        stepinTextView = prefs.getString("Goal","0");
        stepgoalTexView.setText("Your Goal is: "+stepinTextView);
        stepgoalNumber =  Double.parseDouble(stepinTextView);




        goalachievement = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(status!=TextToSpeech.ERROR){
                    goalachievement.setLanguage(Locale.UK);
                }
            }
        });



        //save the goal..

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getGoalString = stepgoalEditText.getText().toString().trim();


                if(!TextUtils.isEmpty(getGoalString)){

                    editor.putString("Goal", getGoalString);
                    editor.commit();


                    stepgoalTexView.setText("Your Goal is :"+getGoalString);

                    stepgoalNumber =  Double.parseDouble(getGoalString);




                }else{

                    Toast.makeText(getActivity(),"Input Goal",Toast.LENGTH_SHORT).show();


                }




            }
        });



        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stepsAtReset = stepsInSensor;

                editor.putInt("stepsAtReset", stepsAtReset);
                editor.commit();

                steps.setText(String.valueOf(0));

            }
        });

        return view;

    }

    public void onResume(){

        super.onResume();
        running =true ;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(countSensor!=null){
            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
        }else {
            Toast.makeText(getActivity(),"Sensor Not Found", Toast.LENGTH_SHORT).show();
        }

    }


    public  void onPause(){
        super.onPause();
        running =false;
    }


    // it looks down to your steps and identify if you have reached to goal...

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        if(running){
            stepsInSensor = Integer.valueOf((int) sensorEvent.values[0]);
            int stepsSinceReset = stepsInSensor - stepsAtReset;



            steps.setText(String.valueOf(stepsSinceReset));




            //stepgoalTexView.setText("Your Goal is :"+stepinTextView);
            String presentSteps = steps.getText().toString().trim();
            stepsnow = Double.parseDouble(presentSteps);


            if(stepsnow == stepgoalNumber){

                Toast.makeText(getActivity(),"Congratulations! You have achieved your goal",Toast.LENGTH_SHORT).show();
                goalachievement.speak("Congratulations! You have achieved your goal. Be healthy and take care", TextToSpeech.QUEUE_FLUSH, null);
            }


        }else{
            sensorEvent.values[0] = 0;

            String presentSteps = steps.getText().toString().trim();
            stepsnow = Double.parseDouble(presentSteps);


            if(stepsnow == stepgoalNumber){

                Toast.makeText(getActivity(),"Congratulations! You have achieved your goal",Toast.LENGTH_SHORT).show();
                goalachievement.speak("Congratulations! You have achieved your goal. Be healthy and take care", TextToSpeech.QUEUE_FLUSH, null);
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}