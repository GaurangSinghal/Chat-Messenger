package com.example.ashish_pc.feedback;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class addquestionfragment extends Fragment {

    View rootview;
    TextView tv;
    ListView lv;
    EditText e;
    Button b1;
    View viewuser,vv,v;
    int pos=0;
    ImageView im1,im2,im3,im4,im5;
    private List<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_addquestionfragment, container, false);
        im1=(ImageButton)rootview.findViewById(R.id.imageButtonadd);


        im1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                e = (EditText)rootview.findViewById(R.id.editTextquestion);
                data.add(e.getText().toString());
                e.setText(null);
                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data);
                lv = (ListView)rootview.findViewById(R.id.listViewuserfeedbackquestion);
                lv.setAdapter(adapter);
            }
        });

        im2=(ImageView)rootview.findViewById(R.id.imageViewhappyse);
        im3=(ImageView)rootview.findViewById(R.id.imageViewstarse);
        im4=(ImageView)rootview.findViewById(R.id.imageViewehandse);
        im5=(ImageView)rootview.findViewById(R.id.imageViewemheartse);
        im2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


               // data.remove(pos);
                //adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data);
                //lv = (ListView)rootview.findViewById(R.id.listViewuserfeedbackquestion);
                //lv.setAdapter(adapter);

                int width = 160;
                int height = 160;
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                im2.setLayoutParams(parms);
                LinearLayout.LayoutParams parmss = new LinearLayout.LayoutParams(100,100);
                im3.setLayoutParams(parmss);
                im4.setLayoutParams(parmss);
                im5.setLayoutParams(parmss);

            }
        });

        im3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int width = 160;
                int height = 160;
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                im3.setLayoutParams(parms);
                LinearLayout.LayoutParams parmss = new LinearLayout.LayoutParams(100,100);
                im2.setLayoutParams(parmss);
                im4.setLayoutParams(parmss);
                im5.setLayoutParams(parmss);

            }
        });

        im4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int width = 160;
                int height = 160;
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                im4.setLayoutParams(parms);
                LinearLayout.LayoutParams parmss = new LinearLayout.LayoutParams(100,100);
                im2.setLayoutParams(parmss);
                im3.setLayoutParams(parmss);
                im5.setLayoutParams(parmss);

            }
        });

        im5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int width = 160;
                int height = 160;
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                im5.setLayoutParams(parms);
                LinearLayout.LayoutParams parmss = new LinearLayout.LayoutParams(100,100);
                im2.setLayoutParams(parmss);
                im3.setLayoutParams(parmss);
                im4.setLayoutParams(parmss);
            }
        });


          clickcall();

        return rootview;
    }

    private void clickcall() {
        ListView lv = (ListView) rootview.findViewById(R.id.listViewuserfeedbackquestion);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  pos=position;
                v = (LayoutInflater.from(getActivity())).inflate(R.layout.deletetyped, null);
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(getActivity());
                alertbuilder.setView(v);
                alertbuilder.setCancelable(true);
                Dialog dialog = alertbuilder.create();
                dialog.show();*/


                data.remove(pos);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,data);
                ListView lv = (ListView)rootview.findViewById(R.id.listViewuserfeedbackquestion);
                lv.setAdapter(adapter);


            }
        });
    }







}
