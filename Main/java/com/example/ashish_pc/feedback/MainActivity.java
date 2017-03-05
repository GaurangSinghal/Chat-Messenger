package com.example.ashish_pc.feedback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends Activity {

    EditText username;
    EditText password;
    String str;
    String[] mydata;
    private ProgressDialog pDialog;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Status = "statusKey";
    public static final String Email = "emailKey";
    public static final String Pass = "passKey";
    public static final String Ss = "hide";
    public static final String Id = "idKey";
    public static final String Dob = "dobKey";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.editTextusername);
        password = (EditText) findViewById(R.id.editTextuserpassword);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
       /* String channel = (sharedpreferences.getString(Email,null));
        if(!channel.equals(null))
        {
            Intent i = new Intent("com.example.ashish_pc.feedback.friendlist");
            startActivity(i);
        }
       /* else
            Toast.makeText(MainActivity.this, "Login to enter.", Toast.LENGTH_SHORT).show();*/

    }


    public void companylogin(View v)
    {


        new CreateNewProduct().execute(username.getText().toString(),password.getText().toString());

    }

    public void register(View v)
    {
         Intent i = new Intent("com.example.ashish_pc.feedback.createaccount");


        startActivity(i);

    }


    class CreateNewProduct extends AsyncTask<String, Void, String>
    {


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Wait few seconds...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected String doInBackground(String... arg0)
        {
        try{
            String username = (String)arg0[0];
            String password = (String)arg0[1];

            String link="http://192.168.68.47/loginpost.php";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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

            }
            str = sb.toString();
            mydata = str.split("\\+");

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
            if(!result.equals(" "))
            {
                pDialog.dismiss();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Name, mydata[1]);
                editor.putString(Id, mydata[0]);
                editor.putString(Status, mydata[4]);
                editor.putString(Dob, mydata[3]);
                editor.putString(Email, mydata[2]);
                editor.putString(Pass, mydata[5]);
                editor.putString(Ss, "00");
                editor.commit();
                finish();
                Intent i = new Intent("com.example.ashish_pc.feedback.friendlist");
                startActivity(i);
            }
            else {
                pDialog.dismiss();
                Toast.makeText(MainActivity.this,"Fail.", Toast.LENGTH_LONG).show();
            }
        }

        //
    }

}
