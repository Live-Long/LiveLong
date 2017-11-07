package com.example.asus.livelong;

/**
 * Created by nowrin on 10/21/17.
 */

public class BP_Update {

    private String date, time, id;
    //String temparature;
    String systolic,diastolic;

    public BP_Update(String id, String date, String time, String systolic, String diastolic) {


        this.id = id;
        this.date = date;
        this.time = time;
        //this.temparature=temparature;
        this.systolic = systolic;
        this.diastolic = diastolic;

    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSystolic() {
        return systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }
}
