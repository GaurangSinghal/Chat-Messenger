package com.example.ashish_pc.feedback;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class friendlist extends AppCompatActivity {
    private List<populatefrnd> offerdata = new ArrayList<populatefrnd>();
    ImageView im;
    String s,peremail;
    TextView tv1,tv2,tv3,tv4,tvfinduser ;
    Button btn;
    StringBuilder sb;
    String[] frnddatalist;
    String[] listrefreshdata;
    ArrayAdapter<populatefrnd> adapter;
    SharedPreferences sharedpreferences;
    private ProgressDialog pDialog;
    ImageButton uadd,urov;
    Dialog dialog,dialog2;
    View vs;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 vs = (LayoutInflater.from(friendlist.this)).inflate(R.layout.frndadd, null);
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(friendlist.this);
                alertbuilder.setView(vs);
                alertbuilder.setCancelable(true);
                dialog = alertbuilder.create();
                dialog.show();


            }
        });

        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        peremail = (sharedpreferences.getString("emailKey",null));


        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                finish();
                Intent i = new Intent(friendlist.this, MainActivity.class);
                startActivity(i);

            }
        });
       // populatelist();
        populatelistview();
        new mytask().execute();
        clickcall();
    }



    private void populatelist()
    {
         //offerdata.add(new populatefrnd("ashish","jai ho"));
        //adapter.add(new populatefrnd(listrefreshdata[0],listrefreshdata[1]));

    }

   private void populatelistview()
    {
         adapter = new Mylistadapter();
        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);


    }
    public void clickcall()
    {
        ListView lv=(ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent("com.example.ashish_pc.feedback.messageact");
                i.putExtra("name", offerdata.get(position).getname());
                i.putExtra("email",offerdata.get(position).getemail());
                i.putExtra("dob",offerdata.get(position).getdob());
                i.putExtra("status",offerdata.get(position).getstatus());
                startActivity(i);
            }
        });
    }



    private class Mylistadapter extends ArrayAdapter<populatefrnd>
    {
        public Mylistadapter()
        {
            super(friendlist.this, R.layout.frndslist, offerdata);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View itemview = convertView;
            if (itemview == null)
            {
                itemview = getLayoutInflater().inflate(R.layout.frndslist, parent, false);
            }

            populatefrnd currentquestion = offerdata.get(position);

            TextView tv = (TextView) itemview.findViewById(R.id.textstatus);
            tv.setText(""+currentquestion.getstatus());
            TextView tv1 = (TextView) itemview.findViewById(R.id.textView8);
            tv1.setText("" + currentquestion.getname());
            return itemview;
        }
    }


    public void check(View v)
    {
        EditText checkname = (EditText)vs.findViewById(R.id.editText);
        s = "00";
        new friendfind().execute(s,checkname.getText().toString());

        //Toast.makeText(friendlist.this,"ashish",Toast.LENGTH_LONG).show();
        /*tvfinduser = (TextView) vs.findViewById(R.id.textView9);
        uadd = (ImageButton) vs.findViewById(R.id.imageButton2);
        urov = (ImageButton) vs.findViewById(R.id.imageButton);
        tvfinduser.setVisibility(vs.VISIBLE);
        uadd.setVisibility(vs.VISIBLE);
        urov.setVisibility(vs.VISIBLE);*/
    }

    public void useradd(View v)
    {
       EditText checkname = (EditText) vs.findViewById(R.id.editText);
        s = "11";
        new friendfind().execute(s,checkname.getText().toString());
        populatelistview();
        new mytask().execute();
    }

    public void userrov(View v)
    {
        EditText checkname = (EditText) vs.findViewById(R.id.editText);
        s = "22";
        new friendfind().execute(checkname.getText().toString());
    }


    class friendfind extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(friendlist.this);
            pDialog.setMessage("Wait few seconds...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... arg0)
        {
            try {
                String cp = (String) arg0[0];
                String res;
                String str;
                if (cp.equals("00")) {
                    String email = (String) arg0[1];

                    String link = "http://192.168.68.47/friendsearch.php";
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    sb = new StringBuilder();
                    String line = null;
                    StringBuilder sbbb = new StringBuilder();
                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                       sbbb.append(line);

                    }
                    str = sbbb.toString();
                    if(!str.equals("false")) {
                        frnddatalist = str.split("\\+");
                        sb.append("ture");

                    }
                    else
                        sb.append("false");
                }

                if (cp.equals("11")) {
                    String id = (String) frnddatalist[0];
                    String name = (String) frnddatalist[1];
                    String email = (String) frnddatalist[2];
                    String dob = (String) frnddatalist[3];
                    String status = (String) frnddatalist[4];
                    String myemail = peremail;
                    String mid = (String)(sharedpreferences.getString("idKey",null));
                    String mname = (String) (sharedpreferences.getString("nameKey",null));
                    String mdob = (String) (sharedpreferences.getString("dobKey",null));
                    String mstatus = (String) (sharedpreferences.getString("statusKey",null));


                    String link = "http://192.168.68.47/addfrnddata.php";
                    String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                    data += "&" + URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(dob, "UTF-8");
                    data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");
                    data += "&" + URLEncoder.encode("myemail", "UTF-8") + "=" + URLEncoder.encode(myemail, "UTF-8");
                    data += "&" + URLEncoder.encode("mid", "UTF-8") + "=" + URLEncoder.encode(mid, "UTF-8");
                    data += "&" + URLEncoder.encode("mname", "UTF-8") + "=" + URLEncoder.encode(mname, "UTF-8");
                    data += "&" + URLEncoder.encode("mdob", "UTF-8") + "=" + URLEncoder.encode(mdob, "UTF-8");
                    data += "&" + URLEncoder.encode("mstatus", "UTF-8") + "=" + URLEncoder.encode(mstatus, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);

                    }


                }


                  return sb.toString();

                }
                catch(Exception e)
                {
                    return new String("Exception: " + e.getMessage());
                }


        }

        protected void onPostExecute(String result)
        {
            if(result.equals("success"))
            {
                pDialog.dismiss();
                Toast.makeText(friendlist.this, "Friendlist Successfully Updated.", Toast.LENGTH_LONG).show();


                //populatelistview();
            }
            else
            if(result.equals("fail"))
            {
                pDialog.dismiss();
                Toast.makeText(friendlist.this,"Oops! error.", Toast.LENGTH_LONG).show();
            }
            else
            if(result.equals("ture"))
            {
                pDialog.dismiss();
                tvfinduser = (TextView) vs.findViewById(R.id.textView9);
                uadd = (ImageButton) vs.findViewById(R.id.imageButton2);
                urov = (ImageButton) vs.findViewById(R.id.imageButton);
                tvfinduser.setVisibility(vs.VISIBLE);
                uadd.setVisibility(vs.VISIBLE);
                urov.setVisibility(vs.VISIBLE);
                tvfinduser.setText(frnddatalist[4]);


            }
            else
            if(result.equals("false")){
                pDialog.dismiss();
                tvfinduser = (TextView) vs.findViewById(R.id.textView9);
                tvfinduser.setVisibility(vs.VISIBLE);
                tvfinduser.setText("Oops! no one is there.");

            }
        }


    }


//new asynctask for listview





    class mytask extends AsyncTask<Void,populatefrnd,Void>
    {
        private ArrayAdapter<populatefrnd> adapter;
        @Override

        protected void onPreExecute()
        {
            adapter = (ArrayAdapter<populatefrnd> )lv.getAdapter();
           // dialog2 = new ProgressDialog(friendlist.this);
           // dialog2.setTitle("Fetching data");
           // dialog2.show();
        }

        protected Void doInBackground(Void... params) {

             String str;
            try
            {
            String email = peremail;

            String link = "http://192.168.68.47/listrefresh.php";
            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");


            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            sb = new StringBuilder();
            String line = " ";

            // Read Server Response
            while ((line = reader.readLine())!= null) {

                StringBuilder sbb = new StringBuilder();
                sbb.append(line);
                str = sbb.toString();
                String[]  listrefreshdata = str.split("\\*");
                offerdata.clear();
                for (int i = 0; i < listrefreshdata.length; ++i) {
                    if (listrefreshdata[i].equals(" "))
                        continue;
                    else {
                        String[] listrefreshdataaa = listrefreshdata[i].split("\\+");

                        offerdata.add(new populatefrnd(listrefreshdataaa[0],listrefreshdataaa[1],listrefreshdataaa[3],listrefreshdataaa[2]));
                    }
                }




           }


    }
    catch(Exception e)
    {

    }


           /* for(populatefrnd data:offerdata)
            {
                publishProgress(data);
                 try {
                    Thread.sleep(200);
                }
                catch (Exception ex)
                {

                }
            }*/
            return null;
        }

        protected void onProgressUpdate(populatefrnd... values) {
          //  adapter.add(values[0]);

        }


        protected void onPostExecute(Void result)
        {
            //dialog2.dismiss();
        }


    }




}
