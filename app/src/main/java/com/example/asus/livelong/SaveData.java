package com.example.asus.livelong;

public class SaveData {


String name, age, bloodgroup, bpspinner;
    Boolean Allergies_Checkbox, Asthma_Checkbox, Diabetes_Checkbox;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getBpspinner()
    {
        return bpspinner;
    }

    public boolean getCheckbox1()
    {

        return Allergies_Checkbox;
    }

    public boolean getCheckbox2()
    {
        return Asthma_Checkbox;
    }
    public boolean getCheckbox3()
    {
        return Diabetes_Checkbox;
    }

    public SaveData(String name, String age, String bloodgroup, String bpspinner, Boolean cb1, Boolean cb2, Boolean cb3) {
        this.name = name;
        this.age = age;
        this.bloodgroup = bloodgroup;
        this.bpspinner = bpspinner;
        this.Allergies_Checkbox = cb1;
        this.Asthma_Checkbox = cb2;
        this.Diabetes_Checkbox = cb3;
    }
}
