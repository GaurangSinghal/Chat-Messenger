package com.example.ashish_pc.feedback;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class feedbackquestionsforuser extends Activity {

    TextView tv;
    View viewuser,vv;
    ImageView im1;
    ListView listuserquestion;
    private List<feedbackquestiontouser> data = new ArrayList<feedbackquestiontouser>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackquestionsforuser);
        populatelist();
        populatelistview();
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
        ListView lv = (ListView)findViewById(R.id.listViewuserfeedback);
        lv.setAdapter(adapter);
    }


    private class Mylistadapter extends ArrayAdapter<feedbackquestiontouser>
    {
        public Mylistadapter()
        {
            super(feedbackquestionsforuser.this,R.layout.feedbackdatauser,data);
        }

        public View getView(int position,View convertView,ViewGroup parent)
        {
            View itemview = convertView;
            if(itemview==null)
            {
                itemview=getLayoutInflater().inflate(R.layout.feedbackdatauser,parent,false);

            }
            feedbackquestiontouser currentquestion = data.get(position);

            tv= (TextView)itemview.findViewById(R.id.textViewquestion);
            tv.setText(""+currentquestion.getquestion());
            tv= (TextView)itemview.findViewById(R.id.textViewreviewcount);
            tv.setText(""+currentquestion.reviewcount());
            return itemview;
        }




    }






}
