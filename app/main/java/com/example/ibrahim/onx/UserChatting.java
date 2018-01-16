package com.example.ibrahim.onx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserChatting extends AppCompatActivity {

    private String a,b,myId;
    private FirebaseRecyclerAdapter madapter;
    private RecyclerView mRecyclerview;
    private ImageButton msend;
    private EditText mtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chatting);


        msend=(ImageButton)findViewById(R.id.chat_send_btn);

        mtext=(EditText) findViewById(R.id.chat_message_view);



        mRecyclerview=(RecyclerView)findViewById(R.id.messages_list);

        mRecyclerview.setHasFixedSize(true);

        LinearLayoutManager lm=new LinearLayoutManager(this);

        lm.setStackFromEnd(true);

        mRecyclerview.setLayoutManager(lm);

        mRecyclerview.setAdapter(madapter);
        a=getIntent().getStringExtra("mix");
      //  b=getIntent().getStringExtra("buyerId");
       // Toast.makeText(this, b, Toast.LENGTH_SHORT).show();

        myId= FirebaseAuth.getInstance().getUid().toString();

        messageLoad();

        final DatabaseReference mref= FirebaseDatabase.getInstance().getReference().child("chatA").child(myId).child(a);


        madapter=new FirebaseRecyclerAdapter<chatClass,UserChatting.UserViewHolder>(chatClass.class,R.layout.chat_single,UserChatting.UserViewHolder.class,mref) {
            @Override
            protected void populateViewHolder(final UserViewHolder viewHolder, chatClass model, int position) {

                viewHolder.setMessage(model.getMessage());

               /* mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                            HashMap<String, Object> hashmap = (HashMap) childSnapShot.getValue();

                            String message= (String) hashmap.get("message");

                            viewHolder.setMessage(message);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

            }
        };

        mRecyclerview.setAdapter(madapter);
       // DatabaseReference


    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        View mview;

        public UserViewHolder(View itemView) {
            super(itemView);
            mview = itemView;

        }

        public void setMessage(String Message) {

            TextView sdd=(TextView)mview.findViewById(R.id.message_text_layout);
            sdd.setText(Message);

        }


    }


    public void  messageLoad(){



       final DatabaseReference mref= FirebaseDatabase.getInstance().getReference().child("chatA").child(myId).child(a);

        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message=mtext.getText().toString();

                if (!TextUtils.isEmpty(message)){

                    chat_class h=new chat_class();
                    h.setMessage(message);
                    mref.push().setValue(h);
                    mtext.setText("");


                }


            }
        });


    }



}
