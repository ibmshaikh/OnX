package com.example.ibrahim.onx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Chatting extends AppCompatActivity {

    private String mix,UserID,main_message,ownerid;
    private EditText message;
    private ImageButton send;
    private DatabaseReference mref;
    private RecyclerView mRecyclerview;
    private FirebaseRecyclerAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        mix=getIntent().getStringExtra("mix");
        UserID= FirebaseAuth.getInstance().getUid().toString( );

        ownerid=getIntent().getStringExtra("ownerid");

        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Chat_User").child(ownerid).child(mix);


        message=(EditText)findViewById(R.id.chat_message_view);

        send=(ImageButton)findViewById(R.id.chat_send_btn);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_message=message.getText().toString();

                chat_class h=new chat_class();

                h.setMessage(main_message);

                message.setText("");

                mref= FirebaseDatabase.getInstance().getReference().child("chatA").child(ownerid).child(UserID);

               // DatabaseReference mm=FirebaseDatabase.getInstance().getReference().child()

                mref.push().setValue(h);



            }
        });

        mRecyclerview=(RecyclerView)findViewById(R.id.messages_list);

        mRecyclerview.setHasFixedSize(true);

        LinearLayoutManager lm=new LinearLayoutManager(this);

        lm.setStackFromEnd(true);

        mRecyclerview.setLayoutManager(lm);

        mref= FirebaseDatabase.getInstance().getReference().child("chatA").child(ownerid).child(UserID);


        madapter=new FirebaseRecyclerAdapter<chat_class,Chatting.UserViewHolder>(chat_class.class,R.layout.chat_single,Chatting.UserViewHolder.class,mref) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, chat_class model, int position) {

                viewHolder.setMessage(model.getMessage());


            }

        };

        mRecyclerview.setAdapter(madapter);
        madapter.notifyDataSetChanged();
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

}
