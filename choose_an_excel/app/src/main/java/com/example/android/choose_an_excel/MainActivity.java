package com.example.android.choose_an_excel;

import android.Manifest;
import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView print_detail;
    Button abc;
    ArrayList<CustomClass> mylist=new ArrayList<CustomClass>();
    String data;
    RecyclerView namedisplayer;
    Adapter adapter;
    String rollconcat;
    RecyclerView.LayoutManager layoutManager;
    String nameconcat;

    static final int READ_REQUEST_CODE=22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        print_detail=(TextView)findViewById(R.id.detail);

        abc=(Button)findViewById(R.id.file_selector);
        abc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                showFile();
            }//end of else part
        });//end of onclick


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

                addToArraylist(data);

            }
        }

    }
    public void addToArraylist(String q)
    {

        int comma_counter=0;
        for(int i=1;i<q.length();i++) {
            String comma;
            comma = q.toCharArray()[i] + "";
            if (comma.equalsIgnoreCase(",")) {
                comma_counter++;
            } else
            {
              if(comma_counter%2==0)
              {
                  rollconcat=rollconcat+comma;
              }
              else
              {
                  nameconcat=nameconcat+comma;
              }
              if(rollconcat!=null&& nameconcat!=null)
              {
                  CustomClass addition=new CustomClass(rollconcat,nameconcat);
                  mylist.add(addition);
                  nameconcat=null;
                  rollconcat=null;
              }
            }

            //print_detail.setText(comma);
        }

        namedisplayer=(RecyclerView)findViewById(R.id.rec);
        layoutManager=new LinearLayoutManager(this);
        namedisplayer.setLayoutManager(layoutManager);
        namedisplayer.setHasFixedSize(true);
        adapter=new Adapter(mylist,this);
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
