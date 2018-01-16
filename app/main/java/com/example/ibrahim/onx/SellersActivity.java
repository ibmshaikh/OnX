package com.example.ibrahim.onx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
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
import com.developers.imagezipper.ImageZipper;
import com.example.ibrahim.onx.fregments.SellerClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;

public class SellersActivity extends AppCompatActivity {



    private static final int REQUEST_CODE = 123;
    private ArrayList<String> mResults = new ArrayList<>();
    private ImageView aaa;
    private RecyclerView mUserlist;
    private SwipyRefreshLayout mRefreshLayout;
    private FirebaseRecyclerAdapter madapter;
    private ProgressDialog load;
    private ProgressBar mload;
    private GridLayoutManager gm;
    private static final int TOTAL_ITEMS_TO_LOAD=10;
    private int mCurrentpage=1;
    public static final int RESULT_IMAGE=123;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellers);

        mload=(ProgressBar)findViewById(R.id.progressBar3);


        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED  ) {

            mUserlist = (RecyclerView) findViewById(R.id.rcview);
            mRefreshLayout=(SwipyRefreshLayout)findViewById(R.id.refresh);



            mUserlist.setHasFixedSize(true);
            //b.setVisibility(View.GONE);
            gm = new GridLayoutManager(this, 2);
            gm.setOrientation(LinearLayoutManager.VERTICAL);
            mUserlist.setLayoutManager(gm);
            mUserlist.setAdapter(madapter);
            load(mCurrentpage);

             /*  mRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh(SwipyRefreshLayoutDirection direction) {
                    mCurrentpage++;
                    load(mCurrentpage);
                }
            });*/

            mUserlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(dy > 0){

                        visibleItemCount = gm.getChildCount();
                        totalItemCount = gm.getItemCount();
                        pastVisiblesItems = gm.findFirstVisibleItemPosition();
                        if (loading){

                            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                            {
                                loading = false;
                                mCurrentpage++;
                                load(mCurrentpage);
                                //Do pagination.. i.e. fetch new data
                            }

                        }


                    }
                }
            });



            //------------------------------For Image Selecting Intent------------------------//
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SellersActivity.this, AlbumSelectActivity.class);
                    intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT,5); // set limit for image selection
                    startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);
                }
            });



        }

        else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
           // b.setVisibility(View.VISIBLE);
            Toast.makeText(SellersActivity.this, "It seems u don't have intenet connection", Toast.LENGTH_LONG).show();

        }
    }



    //----------------------------------USER_VIEWHOLDER-------------------------------------//

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

    public void load(int yy){


        final DatabaseReference mref= FirebaseDatabase.getInstance().getReference().child("Seller");
        Query q = FirebaseDatabase.getInstance().getReference().child("Seller").orderByChild("ordering").limitToFirst(yy*TOTAL_ITEMS_TO_LOAD);


        madapter = new FirebaseRecyclerAdapter<SellerClass, SellersActivity.UserViewHolder>(SellerClass.class, R.layout.buyers_single, SellersActivity.UserViewHolder.class, q) {
            @Override
            protected void populateViewHolder(final SellersActivity.UserViewHolder viewHolder, final SellerClass model, int position) {

                String Des = model.getName();

                String fullName = madapter.getRef(position).getKey();

                // Toast.makeText(BuyersActivity.this,fullName,Toast.LENGTH_SHORT).show();

                viewHolder.setName(Des);
                viewHolder.setprice(model.getPrice());
                viewHolder.setImage(model.getImage1(), getApplicationContext());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i=new Intent(SellersActivity.this,Buyers_chat.class);

                        i.putExtra("image1",model.getImage1());
                        i.putExtra("image2",model.getImage2());
                        i.putExtra("image3",model.getImage3());
                        i.putExtra("image4",model.getImage4());
                        i.putExtra("image5",model.getImage5());
                        i.putExtra("Price",model.getPrice());
                        i.putExtra("Phone_Number",model.getPhoneNumber());
                        i.putExtra("Description",model.getDescription());
                        i.putExtra("name",model.getName());
                        i.putExtra("order",model.getOrdering());
                        i.putExtra("ownerId",model.getUSERID());
                        // i.putExtra("ID",model.getOrdering());
                        startActivity(i);


                    }
                });


            }

        };
        if (yy>1){
            mUserlist.scrollToPosition((yy*TOTAL_ITEMS_TO_LOAD)-TOTAL_ITEMS_TO_LOAD);

        }
        // mUserlist.scrollToPosition((yy*TOTAL_ITEMS_TO_LOAD)-TOTAL_ITEMS_TO_LOAD);
        mUserlist.setAdapter(madapter);
        madapter.notifyDataSetChanged();
        //gm.scrollToPositionWithOffset((yy*TOTAL_ITEMS_TO_LOAD)-TOTAL_ITEMS_TO_LOAD,0);
        // mUserlist.getLayoutManager().scrollToPosition((yy*TOTAL_ITEMS_TO_LOAD)-TOTAL_ITEMS_TO_LOAD);
        // mUserlist.scrollToPosition((yy*TOTAL_ITEMS_TO_LOAD)-TOTAL_ITEMS_TO_LOAD);

        mload.setVisibility(ProgressBar.VISIBLE);
        madapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                mload.setVisibility(ProgressBar.GONE);
                madapter.unregisterAdapterDataObserver(this);

            }
        });


    }
    public void buu(View view){
        Intent i=new Intent(SellersActivity.this,Fill_buyers.class);
        startActivity(i);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            ArrayList<File> ff=new ArrayList<File>();


            ArrayList<Uri> uriArrayList=new ArrayList<Uri>();
            for (int i = 0; i < images.size(); i++) {



                Uri uri = Uri.fromFile(new File(images.get(i).path));


                File aa= new File(uri.getPath());

                try {
                    File imageZipperFile=new ImageZipper(this).compressToFile(aa);
                    Uri dd= Uri.fromFile(new File(imageZipperFile.toURI()));
                    uriArrayList.add(dd);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            Intent d=new Intent(SellersActivity.this,Fill_Seller.class);

            d.putExtra("arraylist",uriArrayList);

            startActivity(d);
        }
    }
}
