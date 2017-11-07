package com.example.asus.livelong;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class history extends AppCompatActivity {

    Button medicines,BP,temperature,prescription, notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        medicines = (Button) findViewById(R.id.previous_medicines);
        BP = (Button) findViewById(R.id.BP_statistics);
        temperature = (Button) findViewById(R.id.temperature_statistics);
        prescription = (Button) findViewById(R.id.previous_prescription);
        notify = (Button) findViewById(R.id.notify);


        medicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent med = new Intent(history.this, profile.class);
                startActivity(med);
            }


        });


        BP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bp = new Intent(history.this, BP_Graph.class);
                startActivity(bp);
            }


        });

        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temperature_graph_class = new Intent(history.this, Temperature_Graph.class);
                startActivity(temperature_graph_class);
            }


        });

        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent p = new Intent(history.this, abcd.class);
//                startActivity(p);
            }


        });
    }


}
