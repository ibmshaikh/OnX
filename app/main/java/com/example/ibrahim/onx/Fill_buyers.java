package com.example.ibrahim.onx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Fill_buyers extends AppCompatActivity {

    public ArrayList<Uri> mResults = new ArrayList<Uri>();
    private EditText Rupee,Description,name,ph_number;
    public ImageView a1,a2,a3,a4,a5;
    public DatabaseReference mref;
    public List<String> uploadedImages = new ArrayList<>();
    public long ala,b;
    public int totalItemSelected;
    public ProgressDialog mdi;
    private String mUser;
    private MaterialSpinner spinner;
    private String cateogry;
    public static final int RESULT_LOAD_IMAGE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_buyers);


        name=(EditText) findViewById(R.id.name);
        mdi = new ProgressDialog(Fill_buyers.this);



        ph_number=(EditText)findViewById(R.id.phno);

        final ArrayList<Uri> arrayList = (ArrayList<Uri>) getIntent().getSerializableExtra("arraylist");

        StringBuffer sb = new StringBuffer();

        for(Uri ss : arrayList) {
            sb.append(ss).append("\n");
        }

        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("None", "Mobile Phones", "Mobile Accessories", "Electronics", "Bikes","Cars");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });


        // textView.setText((CharSequence) arrayList.get(2));

        BottomNavigationView b=(BottomNavigationView)findViewById(R.id.botto);
        b.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.save:

                        save(arrayList);
                        break;


                }
                return true;
            }
        });

    }

    private void save(final ArrayList<Uri> mResults) {

        mdi.setTitle("Processing");
        mdi.setMessage("Working On Your Add...");
        mdi.setCanceledOnTouchOutside(false);
        mdi.show();
        Rupee=(EditText)findViewById(R.id.rupee);
        Description=(EditText)findViewById(R.id.des);
        final String nname=name.getText().toString();

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

            }
        });


        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, final Object item) {
                cateogry=item.toString();
            }
        });



        final String Descripp=Description.getText().toString();
        final int Paisa=Integer.parseInt(Rupee.getText().toString());
        final Long Phone_number=Long.parseLong(ph_number.getText().toString());

        final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        totalItemSelected = mResults.size();
        for (int i = 0; i < totalItemSelected; i++) {

            String fileName = getFileName(mResults.get(i));

            StorageReference fileToUpload = FirebaseStorage.getInstance().getReference().child("Images").child(fileName);

            final int finalI = i;
            fileToUpload.putFile(mResults.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    uploadedImages.add(downloadUrl);
                    if (finalI == 4) {

                        Query q = FirebaseDatabase.getInstance().getReference().child("Buyers").limitToLast(1);
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                                    HashMap<String, Object> hashmap = (HashMap) childSnapShot.getValue();
                                    long a = (long) hashmap.get("ordering");
                                    final long b = a - 1;


                                    HashMap h = new HashMap();
                                    h.put("Description", Descripp);
                                    h.put("Price", Paisa);
                                    h.put("USERID", user);
                                    h.put("ordering", b);
                                    h.put("Name", nname);
                                    h.put("PhoneNumber",Phone_number);
                                    h.put("category",cateogry);
                                    String i1 = uploadedImages.get(0);
                                    String i2 = uploadedImages.get(1);
                                    String i3 = uploadedImages.get(2);
                                    String i4 = uploadedImages.get(3);
                                    String i5 = uploadedImages.get(4);
                                    h.put("Image1", i1);
                                    h.put("Image2", i2);
                                    h.put("Image3", i3);
                                    h.put("Image4", i4);
                                    h.put("Image5", i5);
                                    h.put("TimeStamp", ServerValue.TIMESTAMP);
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Buyers");
                                    db.push().setValue(h).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {


                                            DatabaseReference mm=FirebaseDatabase.getInstance().getReference().child("User_Seller").child(user);
                                            HashMap c=new HashMap();
                                            c.put("waah",user+b);
                                            mm.push().setValue(c);
                                            Toast.makeText(Fill_buyers.this, "Successfull", Toast.LENGTH_SHORT).show();
                                            mdi.dismiss();

                                          /*  DatabaseReference ff=FirebaseDatabase.getInstance().getReference().child("buyeUser").child(user);
                                            HashMap c=new HashMap();
                                            c.put("waah",user+b);
                                            ff.push().setValue(c);
                                            Toast.makeText(Fill_buyers.this, "Successfull", Toast.LENGTH_SHORT).show();
                                            mdi.dismiss();*/
                                        }
                                    });

                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                }
            });


        }




    }




    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }





}





