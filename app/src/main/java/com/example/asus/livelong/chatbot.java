package com.example.asus.livelong;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.asus.livelong.Adapter.CustomAdapter;
import com.example.asus.livelong.Models.chatModel;
import com.example.asus.livelong.Models.userModel;
import com.example.asus.livelong.helper.HttpDataHandler;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class chatbot extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<userModel> list_chat = new ArrayList<>();
    FloatingActionButton btn_send_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        getSupportActionBar().setTitle("ChatBot");


        listView = (ListView)findViewById(R.id.list_of_message);
        editText = (EditText)findViewById(R.id.user_message);
        btn_send_message = (FloatingActionButton)findViewById(R.id.fab);

        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                userModel model = new userModel(text,true);  // user sends message
                list_chat.add(model);
                new SimsimiAPI().execute(list_chat);

                //remove user message
                editText.setText("");
            }
        });
    }

    private class SimsimiAPI extends AsyncTask<List<userModel>,Void,String>
    {
        String stream = null;
        List<userModel> models;
        String text = editText.getText().toString();

        @Override
        protected String doInBackground(List<userModel>... params) {
            String url = String.format("http://sandbox.api.simsimi.com/request.p?key=%s&lc=en&ft=1.0&text=%s",getString(R.string.simsimi_api),text);
            models = params[0];
            HttpDataHandler httpDataHandler = new HttpDataHandler();
            stream = httpDataHandler.GetHttpData(url);
            return stream;
        }

        @Override
        protected void onPostExecute(String s)
        {
            Gson gson = new Gson();
            chatModel response = gson.fromJson(s,chatModel.class);

            userModel userModel = new userModel(response.getResponse(),false); //get response from bot

            models.add(userModel);
            CustomAdapter adapter = new CustomAdapter(models,getApplicationContext());
            listView.setAdapter(adapter);
        }
    }
}
