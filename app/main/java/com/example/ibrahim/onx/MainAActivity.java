package com.example.ibrahim.onx;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ibrahim.onx.fregments.Ads;
import com.example.ibrahim.onx.fregments.Chats;
import com.example.ibrahim.onx.fregments.UserAds;


public class MainAActivity extends AppCompatActivity {

    private static final int INTENT_REQUEST_GET_IMAGES = 13;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_a);

        toolbar=(Toolbar)findViewById(R.id.toolbar);

        Ads adss=new Ads();
        FragmentManager managercc=getSupportFragmentManager();
        managercc.beginTransaction().replace(R.id.content,adss,adss.getTag()).commit();


        BottomNavigationView b=(BottomNavigationView)findViewById(R.id.bottom_navigation);

        b.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.chat:

                        Chats chats=new Chats();
                        FragmentManager manager=getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.content,chats,chats.getTag()).commit();

                       // Intent i=new Intent(MainAActivity.this,UserChat.class);
                      //  startActivity(i);
                        break;
                    case R.id.myads:

                        UserAds userAds=new UserAds();
                        FragmentManager manager2=getSupportFragmentManager();
                        manager2.beginTransaction().replace(R.id.content,userAds,userAds.getTag()).commit();

                        //Intent ii=new Intent(MainAActivity.this,MyADs.class);
                        //startActivity(ii);
                        break;
                    case R.id.ph_no :

                        Ads ads=new Ads();
                        FragmentManager manager3=getSupportFragmentManager();
                        manager3.beginTransaction().replace(R.id.content,ads,ads.getTag()).commit();
                        break;




                }

                return true;
            }
        });


    }


    public void buys(View v){
        Intent i=new Intent(MainAActivity.this,SellersActivity.class);
        startActivity(i);
    }

    public void sels(View v){
        Intent j=new Intent(MainAActivity.this,BuyersActivity.class);
        startActivity(j);
    }


    public void cheacking(View view){

        Intent ii=new Intent(MainAActivity.this,Test.class);
        startActivity(ii);
    }


}
