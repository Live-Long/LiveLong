package com.example.asus.livelong;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.icu.util.Calendar.*;


/**
 * Created by User on 2/28/2017.
 */

public class bmi_Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private Button setDateButton, updateBmiButton, bmiStatisticsButton, clearButton;

    TextView dateTextView, bmiTextView;
    EditText heightEditText, weightEditText;
    ListView dateListView, bmiNumberListView;


    bmi_database BMIdatabase;

    String heightString, weightString, bmiString;
    String dateString;
    double bmi;

    private ArrayList<String> dateArrayList;
    private ArrayList<String> bmiNumberArrayList;

    private ArrayAdapter<String> dateAdapter;
    private ArrayAdapter<String> bmiNumberAdapter;


    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bmi_fragment, container, false);
        super.onCreate(savedInstanceState);


        setDateButton = (Button) view.findViewById(R.id.date_button);            //setDate
        dateTextView = (TextView) view.findViewById(R.id.date_TextView);        //show the date you have selected
        dateTextView.setPaintFlags(dateTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // to draw underline under date

        updateBmiButton = (Button) view.findViewById(R.id.setBMI_button);       //take input of your hieght and weight bmi
        bmiTextView = (TextView) view.findViewById(R.id.bmitextView);            // display the bmi number
        bmiTextView.setPaintFlags(dateTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        dateListView = (ListView) view.findViewById(R.id.date_listView);                //list view of dates showed bottom of the screen
        bmiNumberListView = (ListView) view.findViewById(R.id.bmiNumber_listView);      //list view of bmi numbers showed bottom of the screen


        bmiStatisticsButton = (Button) view.findViewById(R.id.bmi_statistics_button);   // show the graph screen when clicked
        clearButton = (Button) view.findViewById(R.id.clear_button);              // delete all data from table of bmi


        dateArrayList = new ArrayList<String>();            // arrayList of dates shown in listView;
        bmiNumberArrayList = new ArrayList<String>();           //arrayList of bmi numbers shown in listView;


        BMIdatabase = new bmi_database(getActivity()); // instance of sqllite database made for bmiNumber

        dateAdapter = new ArrayAdapter<String>(getActivity(), R.layout.bmi_list_adapter_view, R.id.bmi_adapter_TextView, dateArrayList);  //adapter fot list view of date
        bmiNumberAdapter = new ArrayAdapter<String>(getActivity(), R.layout.bmi_list_adapter_view, R.id.bmi_adapter_TextView, bmiNumberArrayList);  //adapter fot list view of bmi


        dateListView.setAdapter(dateAdapter);
        bmiNumberListView.setAdapter(bmiNumberAdapter);


        showInList();  // show the lsit view of dates and bmi number

        updateBmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder dialog_box = new AlertDialog.Builder(getActivity());
                View dialog_view = getActivity().getLayoutInflater().inflate(R.layout.bmi_dialog_box, null);
                final EditText height_editText = (EditText) dialog_view.findViewById(R.id.Height_editText);
                final EditText weight_editText = (EditText) dialog_view.findViewById(R.id.Weight_editText);
                Button Save_BMI = (Button) dialog_view.findViewById(R.id.Save_BMI);


                Save_BMI.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        dateString = dateTextView.getText().toString().trim();
                        heightString = height_editText.getText().toString().trim();
                        weightString = weight_editText.getText().toString().trim();

                        if (!TextUtils.isEmpty(dateString) && !TextUtils.isEmpty(heightString) && !TextUtils.isEmpty(weightString)) {

                            bmiString = setBMI(heightString, weightString);


                            boolean isinserted = BMIdatabase.insertData(dateString, heightString, weightString);

                            if (isinserted == true) {
                                Toast.makeText(getActivity(), "BMI UPDATED", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "not inserted", Toast.LENGTH_SHORT).show();

                            }


                            bmiTextView.setText(String.format("%.2f", bmi));
                            showInList();
                        } else {
                            Toast.makeText(getActivity(), "Fill up required fields", Toast.LENGTH_SHORT).show();

                        }


                    }
                });

                dialog_box.setView(dialog_view);
                AlertDialog dilog_box = dialog_box.create();
                dilog_box.show();


            }
        });


        setDateButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                showDatePicker();

            }

        });

        bmiStatisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bmiGraphClass = new Intent(getActivity(), bmi_graph.class);

                startActivity(bmiGraphClass);
            }
        });


        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dateArrayList.clear();
                bmiNumberArrayList.clear();
                showInList();

                BMIdatabase.deleteTable();
                startActivity(getActivity().getIntent());
            }
        });


        return view;
    }


    void showInList() {


        //dateArrayList.add(dateString);
        getInList();


        dateAdapter.notifyDataSetChanged();
        bmiNumberAdapter.notifyDataSetChanged();

        //Toast.makeText(getActivity(),"after listView",Toast.LENGTH_SHORT).show();

        // new bmi_Fragment();


    }


    public String setBMI(String mheight, String mweight) {


        double height = (Double.parseDouble(mheight)) / 100;
        double weight = Double.parseDouble(mweight);

        bmi = weight / Math.pow(height, 2);
        String bmiValueString = Double.toString(bmi);

        return bmiValueString;


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            dateTextView.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));
        }
    };


    //fetch data from bmi sqlLite database
    void getInList() {

        Cursor res = BMIdatabase.getAllData();

        int numberOfPoints = res.getCount();


        if (numberOfPoints == 0) {
            Toast.makeText(getActivity(), "No Values", Toast.LENGTH_SHORT).show();

            ;
        } else {
            Toast.makeText(getActivity(), "Values", Toast.LENGTH_SHORT).show();
            dateArrayList.clear();
            bmiNumberArrayList.clear();

            while (res.moveToNext()) {

                String date = res.getString(1);
                double retrived_height = Double.parseDouble(res.getString(2));
                double retrived_weight = Double.parseDouble(res.getString(3));
                double bmiNumber = retrived_weight / retrived_height;
                String bmiNumberString = String.format("%.2f", bmiNumber);
                dateArrayList.add(date);
                bmiNumberArrayList.add(bmiNumberString);
            }


        }


    }


}




