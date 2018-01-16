package com.example.ibrahim.onx.fregments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.onx.R;
import com.example.ibrahim.onx.UserChat;
import com.example.ibrahim.onx.UserChatting;
import com.example.ibrahim.onx.chatClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chats extends Fragment {

    private FirebaseRecyclerAdapter mdapter;
    private String user;
    private RecyclerView mview;
    public View v;


    public Chats() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.chats, container, false);



        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("chatA").child(user);

        mview = (RecyclerView)v. findViewById(R.id.chate);

        mview.setHasFixedSize(true);

        LinearLayoutManager lm = new LinearLayoutManager(v.getContext());

        mview.setLayoutManager(lm);



        mdapter = new FirebaseRecyclerAdapter<chatClass, UserChat.UserViewHolder>(chatClass.class, R.layout.dialog_list, UserChat.UserViewHolder.class, mref)

        {
            @Override
            protected void populateViewHolder(UserChat.UserViewHolder viewHolder, chatClass model, int position) {

                final String user = model.getBuyerId();

                final String fullName = mdapter.getRef(position).getKey();


                viewHolder.setUser(fullName);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent i = new Intent(v.getContext(), UserChatting.class);
                        i.putExtra("mix", fullName);
                        startActivity(i);

                    }
                });


            }
        };
        mview.setAdapter(mdapter);

        return v;

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
