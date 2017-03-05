package com.example.ashish_pc.feedback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class createaccount extends Activity {

    EditText name;
    EditText password;
    EditText email;
    EditText dob;
    EditText status;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        name = (EditText) findViewById(R.id.editTextname);
        password = (EditText) findViewById(R.id.editTextpass);
        email = (EditText) findViewById(R.id.editTexemail);
        dob = (EditText) findViewById(R.id.editTexttype);
        status = (EditText) findViewById(R.id.editTextweb);


    }

    public void addcontact(View v)
    {
        //Intent i = new Intent("com.example.ashish_pc.feedback.friendlist");
        //startActivity(i);
        new registerinfo().execute(name.getText().toString(),password.getText().toString()
                ,email.getText().toString(),dob.getText().toString(),status.getText().toString());


    }




    class registerinfo extends AsyncTask<String, Void, String>
    {


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(createaccount.this);
            pDialog.setMessage("Wait few seconds...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected String doInBackground(String... arg0)
        {
            try{
                String name = (String)arg0[0];
                String password = (String)arg0[1];
                String email = (String)arg0[2];
                String dob = (String)arg0[3];
                String status = (String)arg0[4];

                String link="http://192.168.68.47/register.php";

                String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(dob, "UTF-8");
                data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }

                return sb.toString();
            }
            catch(Exception e)
            {
                return new String("Exception: " + e.getMessage());
            }

        }



        //

        protected void onPostExecute(String result)
        {
            if(result.equals("success"))
            {
                pDialog.dismiss();

                Intent i = new Intent(createaccount.this,MainActivity.class);
                startActivity(i);
            }
            else {
                pDialog.dismiss();
                Toast.makeText(createaccount.this, result, Toast.LENGTH_LONG).show();
            }
        }

        //
    }




}
