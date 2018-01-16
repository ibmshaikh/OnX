package com.example.ibrahim.onx;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

public class Buyers_chat extends AppCompatActivity {
    private TextView name,price,description;
    private String userid,mix;
    private long Phone_Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyers_chat);

        final BannerSlider bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);
        name=(TextView)findViewById(R.id.textView2) ;
        price=(TextView)findViewById(R.id.textView4);
        description=(TextView)findViewById(R.id.textView5);
        final List<Banner> banners=new ArrayList<>();
        String Name=getIntent().getStringExtra("name");
        String  img1 =getIntent().getStringExtra("image1");
        String  img2 =getIntent().getStringExtra("image2");
        String  img3 =getIntent().getStringExtra("image3");
        String  img4 =getIntent().getStringExtra("image4");
        String  img5 =getIntent().getStringExtra("image5");
        String Product_Description=getIntent().getStringExtra("Description");
        int Price=getIntent().getIntExtra("Price",0);
        final long order=getIntent().getLongExtra("order",0);
        userid=getIntent().getStringExtra("ownerId");
        Phone_Number=getIntent().getLongExtra("Phone_Number",0);
        Toast.makeText(Buyers_chat.this,String.valueOf(Phone_Number),Toast.LENGTH_SHORT).show();
        description.setText(Product_Description);

        final String a=userid+order;
        price.setText(Integer.toString(Price));
        name.setText(Name);

        banners.add(new RemoteBanner(img1));
        banners.add(new RemoteBanner(img2));
        banners.add(new RemoteBanner(img3));
        banners.add(new RemoteBanner(img4));
        banners.add(new RemoteBanner(img5));
        bannerSlider.setBanners(banners);

        // banners.add(new RemoteBanner(a5));
        final ArrayList<String> list=new ArrayList<String>();

        list.add(img1);
        list.add(img2);
        list.add(img3);
        list.add(img4);
        list.add(img5);




        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {

                new ImageViewer.Builder(Buyers_chat.this, list)
                        .setStartPosition(position)
                        .show();


            }
        });


        BottomNavigationView b=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        b.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.chat:

                        Intent ii=new Intent(Buyers_chat.this,Chatting.class);
                        ii.putExtra("mix",a);
                        ii.putExtra("ownerid",userid);
                        startActivity(ii);
                        break;
                    case R.id.ph_no:

                        Intent i=new Intent(Intent.ACTION_DIAL);
                       // i.setData(Uri.parse("tel:5254125632"));
                        i.setData(Uri.parse(String.valueOf("tel:"+Phone_Number)));
                        startActivity(i);
                        break;
                }

                return true;
            }
        });




    }
}
