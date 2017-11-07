package com.example.asus.livelong.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.asus.livelong.Models.userModel;
import com.example.asus.livelong.R;
import com.github.library.bubbleview.BubbleTextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

   private List<userModel> list_user_models;
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomAdapter(List<userModel> list_user_models, Context context) {
        this.list_user_models = list_user_models;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list_user_models.size();
    }

    @Override
    public Object getItem(int position) {
        return list_user_models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        View view = convertview;
        if(view == null)
        {
            if(list_user_models.get(position).isSend)
                view = layoutInflater.inflate(R.layout.list_item_message_send,null);
            else
                view = layoutInflater.inflate(R.layout.list_item_message_recv,null);

            BubbleTextView text_message = (BubbleTextView)view.findViewById(R.id.text_message);
            text_message.setText(list_user_models.get(position).message);
        }
        return view;
    }
}
