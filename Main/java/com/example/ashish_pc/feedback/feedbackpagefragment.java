package com.example.ashish_pc.feedback;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class feedbackpagefragment extends Fragment {

    View rootview;
    TextView tv;
    View viewuser,vv;
    ImageView im1;
    private List<feedbackquestiontouser> data = new ArrayList<feedbackquestiontouser>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_feedbackpagefragment, container, false);

        populatelist();
        populatelistview();


        return  rootview;
    }



    private void populatelist()
    {
        data.add(new feedbackquestiontouser("How much are you satisfied with our shippment?",15468));
        data.add(new feedbackquestiontouser("How much are you satisfied with our shippment?",1254));
        data.add(new feedbackquestiontouser("How much are you satisfied with our shippment?",458));
        data.add(new feedbackquestiontouser("How much are you satisfied with our shippment?",4569));
        data.add(new feedbackquestiontouser("How much are you satisfied with our shippment?",4587));
        data.add(new feedbackquestiontouser("How much are you satisfied with our shippment?",25698));
        data.add(new feedbackquestiontouser("How much are you satisfied with our shippment?",14587));
        data.add(new feedbackquestiontouser("How much are you satisfied with our shippment?",14587));
        data.add(new feedbackquestiontouser("How much are you satisfied with our shippment?",14587));
        data.add(new feedbackquestiontouser("How much are you satisfied with our shippment?",14587));
    }

    private void populatelistview()
    {
        ArrayAdapter<feedbackquestiontouser> adapter = new Mylistadapter();
        ListView lv = (ListView)rootview.findViewById(R.id.listViewuserfeedbackpage);
        lv.setAdapter(adapter);
    }


    private class Mylistadapter extends ArrayAdapter<feedbackquestiontouser>
    {
        public Mylistadapter()
        {
            super(getActivity(),R.layout.feedbackpagedemo,data);
        }

        public View getView(int position,View convertView,ViewGroup parent)
        {
            View itemview = convertView;
            if(itemview==null)
            {

                itemview=((Activity)getContext()).getLayoutInflater().inflate(R.layout.feedbackpagedemo,parent,false);
            }
            feedbackquestiontouser currentquestion = data.get(position);

            tv= (TextView)itemview.findViewById(R.id.textViewquestion1);
            tv.setText(""+currentquestion.getquestion());
            /*tv= (TextView)itemview.findViewById(R.id.textViewreviewcount);
            tv.setText(""+currentquestion.reviewcount());*/
            return itemview;
        }




    }




}
