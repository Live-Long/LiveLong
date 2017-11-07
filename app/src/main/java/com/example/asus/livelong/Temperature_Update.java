package com.example.asus.livelong;

public class Temperature_Update {

    private String date,time,id;
    String temparature;


    public Temperature_Update(String date,String time,String temparature){


        this.date=date;
        this.time=time;
        this.temparature=temparature;

    }



    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTemparature() {
        return temparature;
    }

}

