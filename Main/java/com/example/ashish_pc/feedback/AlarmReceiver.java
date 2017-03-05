package com.example.ashish_pc.feedback;

/**
 * Created by Gaurang on 28-09-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class AlarmReceiver extends WakefulBroadcastReceiver {
    String str;
    public String messageText;
    public String address,date;
    public static  String Name = "nameKey";
    public static  String Email = "emailKey";
    @Override
    public void onReceive(final Context context, Intent intent) {
        messageact inst = messageact.instance();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySchedPref",Context.MODE_APPEND);
        SharedPreferences sharedPreferences1 = context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);

        //System.out.println("Inside Time Receiver");
        Name = sharedPreferences1.getString("nameKey",null);
        Email = sharedPreferences1.getString("emailKey",null);
        Calendar c = Calendar.getInstance();
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String min =String.valueOf(c.get(Calendar.MINUTE));
        if(Integer.parseInt(hour)<10)
            hour = "0"+hour;
        if(Integer.parseInt(min)<10)
            min = "0"+min;
        String time = hour+":"+min;

       // System.out.println(time);
        //System.out.println(sharedPreferences.getString(time,null));
        String message = sharedPreferences.getString(time,null);
        //System.out.println(message);
        // Toast.makeText(context,message,Toast.LENGTH_LONG);
        sharedPreferences.edit().remove(time).commit();
        if(!message.equals("-1"))
        {

            address =  message.substring(0,message.indexOf(":") );
            messageText = message.substring(message.indexOf(":") + 1);
            System.out.println(messageText);
            System.out.println(address);

            if (TextUtils.isEmpty(messageText)) {
                return;
            }
            new mytask().execute();
        }
        setResultCode(Activity.RESULT_OK);
    }    //send data to database
    class mytask extends AsyncTask<Void,Void,String>
    {
        // private ArrayAdapter<ChatMessage> adapter;
        @Override
        protected void onPreExecute()
        {
            //  adapter = (ArrayAdapter<ChatMessage> )messagesContainer.getAdapter();

        }
        protected String doInBackground(Void... params) {
            // String chat = params[0].getMessage();
            // String datem = params[0].getDate();
            Calendar c = Calendar.getInstance();
            //SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy HH:MM aaa");
            String formattedDate = DateFormat.getDateTimeInstance().format(new Date());
            String sender = "false";
            String str ="";
            try
            {
                // String email = peremail;

                String link = "http://192.168.68.47/msgstore.php";
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(Email+address, "UTF-8");
                data += "&" + URLEncoder.encode("chat", "UTF-8") + "=" + URLEncoder.encode(messageText, "UTF-8");
                data += "&" + URLEncoder.encode("datem", "UTF-8") + "=" + URLEncoder.encode(formattedDate, "UTF-8");
                data += "&" + URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(sender, "UTF-8");
                data += "&" + URLEncoder.encode("fetchemail", "UTF-8") + "=" + URLEncoder.encode(address+Email, "UTF-8");


                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    str = sb.toString();
                }
            }
            catch(Exception e)
            {

            }
            return str;
        }
        protected void onProgressUpdate(populatefrnd... values) {
        }
        protected void onPostExecute(String result)
        {
            //Toast.makeText(messageact.this,result,Toast.LENGTH_LONG).show();
        }
    }



    //



}

