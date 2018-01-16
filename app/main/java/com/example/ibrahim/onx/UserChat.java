package com.example.ibrahim.onx;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserChat extends AppCompatActivity {

    private FirebaseRecyclerAdapter mdapter;
    private String user;
    private RecyclerView mview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("chatA").child(user);

        mview = (RecyclerView) findViewById(R.id.chate);

        mview.setHasFixedSize(true);

        LinearLayoutManager lm = new LinearLayoutManager(this);

        mview.setLayoutManager(lm);




        mdapter = new FirebaseRecyclerAdapter<chatClass, UserChat.UserViewHolder>(chatClass.class, R.layout.dialog_list, UserChat.UserViewHolder.class, mref)

        {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, chatClass model, int position) {

                final String user = model.getBuyerId();

                final String fullName = mdapter.getRef(position).getKey();

                Toast.makeText(UserChat.this,fullName,Toast.LENGTH_SHORT).show();

                viewHolder.setUser(fullName);

                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(UserChat.this, UserChatting.class);
                        i.putExtra("mix", fullName);
                        startActivity(i);


                    }
                });

            }
        };
        mview.setAdapter(mdapter);

    }



       /* mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String child = ds.getKey();
                    Toast.makeText(UserChat.this,child,Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        View mview;


        public UserViewHolder(View itemView) {
            super(itemView);
            mview = itemView;

        }

        public void setUser(String Message) {

            TextView sdd = (TextView) mview.findViewById(R.id.dialogName);
            sdd.setText(Message);

        }
    }

}
