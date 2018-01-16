package com.example.ibrahim.onx;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyADs extends AppCompatActivity {

    private RecyclerView mview;
    private ProgressBar mload;
    private FirebaseRecyclerAdapter madapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);

        mview = (RecyclerView) findViewById(R.id.ads);
        mload = (ProgressBar) findViewById(R.id.progressBar3);

        mview.setHasFixedSize(true);
        LinearLayoutManager gm = new GridLayoutManager(this, 2);
        gm.setOrientation(LinearLayoutManager.VERTICAL);
        mview.setLayoutManager(gm);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        String myuid = FirebaseAuth.getInstance().getUid().toString();
        Toast.makeText(this, myuid, Toast.LENGTH_SHORT).show();
        final DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("Buyers");
        Query q = FirebaseDatabase.getInstance().getReference().child("Buyers").orderByChild("USERID").equalTo(myuid);
        q.keepSynced(true);
        madapter = new FirebaseRecyclerAdapter<buyersclass, MyADs.UserViewHolder>(buyersclass.class, R.layout.buyers_single, MyADs.UserViewHolder.class, q) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, buyersclass model, int position) {
                String Des = model.getName();

                String fullName = madapter.getRef(position).getKey();

                // Toast.makeText(BuyersActivity.this,fullName,Toast.LENGTH_SHORT).show();

                viewHolder.setName(Des);
                viewHolder.setprice(model.getPrice());
                viewHolder.setImage(model.getImage1(), getApplicationContext());
            }

        };

        mview.setAdapter(madapter);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        View mview;
        Context ctx;

        public UserViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }
        public void setprice(int price) {
            TextView pasia=(TextView)mview.findViewById(R.id.price);
            pasia.setText(Integer.toString(price));
        }
        public void setName(String Desc) {

            TextView sdd=(TextView)mview.findViewById(R.id.dess);
            sdd.setText(Desc);

        }
        public void setImage(String image, Context applicationContext){
            ImageView m=(ImageView)mview.findViewById(R.id.imgg);
            Glide.with(applicationContext).load(image).into(m);
        }
    }
}
