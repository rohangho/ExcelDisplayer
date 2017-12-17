package com.example.android.choose_an_excel;

/**
 * Created by ROHAN on 16-12-2017.
 */

public class CustomClass  {

    private String roll;
    private String name;

    public CustomClass(String roll_number,String original_name)
    {
        this.roll=roll_number;
        this.name=original_name;
    }

    public String getRoll()
    {
        return roll;
    }
    public String getName()
    {
        return name;
    }

}
