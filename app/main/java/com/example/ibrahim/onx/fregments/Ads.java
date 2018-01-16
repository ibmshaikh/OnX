package com.example.ibrahim.onx.fregments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibrahim.onx.BuyersActivity;
import com.example.ibrahim.onx.MainAActivity;
import com.example.ibrahim.onx.R;
import com.example.ibrahim.onx.SellersActivity;
import com.example.ibrahim.onx.Test;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ads extends Fragment {


    public Ads() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ads, container, false);





    }


    public void buys(View v){
        Intent i=new Intent(getActivity(),SellersActivity.class);
        startActivity(i);
    }

    public void sels(View v){
        Intent j=new Intent(getActivity(),BuyersActivity.class);
        startActivity(j);
    }


    public void cheacking(View view){

        Intent ii=new Intent(getActivity(),Test.class);
        startActivity(ii);
    }


}
