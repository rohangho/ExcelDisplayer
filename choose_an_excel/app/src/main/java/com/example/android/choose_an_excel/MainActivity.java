package com.example.android.choose_an_excel;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView print_detail;
    Button abc;
    ArrayList<CustomClass> mylist=new ArrayList<CustomClass>();
    public static final String Filename="myattendace";
    String data;
    RecyclerView namedisplayer;
    Adapter adapter;
    String rollconcat;
    RecyclerView.LayoutManager layoutManager;
    String nameconcat;
    Button save;

    static final int READ_REQUEST_CODE=22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        print_detail=(TextView)findViewById(R.id.detail);
        save=(Button)findViewById(R.id.save);

        abc=(Button)findViewById(R.id.file_selector);
        abc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                showFile();
            }//end of else part
        });//end of onclick


    }

    public void SaveMe(View view) throws IOException {
        ArrayList<CustomClass> myUpdatedList=new ArrayList<>();
        int k=0;
        Log.e("hiiiiii",Boolean.toString(adapter.tracker[2]));
        List<String> list = new ArrayList<String>(Arrays.asList(data.split(",")));
        for (int i=0;i<list.size()-2;i=i+3) {
            CustomClass addition1 = new CustomClass(list.get(i), list.get(i+1),Boolean.toString(adapter.tracker[k]));
            myUpdatedList.add(addition1);
            k++;

        }
  //
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                write_file(myUpdatedList);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        }
        else
            write_file(myUpdatedList);
    }

    public void write_file(ArrayList<CustomClass> myUpdatedList)
    {
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(Filename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int j=0;j<myUpdatedList.size();j++)
        {
            try {

                outputStream.write(myUpdatedList.get(j).getRoll().getBytes());
                outputStream.write(",".getBytes());
                outputStream.write(myUpdatedList.get(j).getName().getBytes());
                outputStream.write(",".getBytes());
                outputStream.write(myUpdatedList.get(j).getBool().getBytes());
                outputStream.write(",".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void ClearEveryThing(View view)
    {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private  void showFile()
    {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
              //  Toast.makeText(this,"hiiiiiiiii",Toast.LENGTH_LONG).show();
                getFile();

            }
            else {
               ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        }
        else {
           // Toast.makeText(this,"hiiiiiiiii",Toast.LENGTH_LONG).show();
           getFile();

        }

    }

    private void getFile()
    {
        Intent intent= new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri=resultData.getData();
                String message = null;
                try {
                    message=readTextFromUri(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //ContentResolver resolver = this.getContentResolver();
               // Cursor cursor=resolver.query(uri,null,null,null,null);
                data=message.toString();

                addtoArraylist_Using1function(data);

            }
        }

    }











    //different function in which the list is seperated using only one function

    public void addtoArraylist_Using1function(String q)
    {
        List<String> list = new ArrayList<String>(Arrays.asList(q.split(",")));
       for (int i=0;i<list.size()-2;i=i+3) {
           CustomClass addition = new CustomClass(list.get(i), list.get(i+1),list.get(i+2));
           mylist.add(addition);

       }
        namedisplayer=(RecyclerView)findViewById(R.id.rec);
        layoutManager=new LinearLayoutManager(this);
        namedisplayer.setLayoutManager(layoutManager);
        namedisplayer.setHasFixedSize(true);
        adapter=new Adapter(mylist,this,(list.size()/3));
        namedisplayer.setAdapter(adapter);


    }









    private String readTextFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        inputStream.close();
        return stringBuilder.toString();
    }




}
