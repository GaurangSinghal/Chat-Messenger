package com.example.ashish_pc.feedback;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.example.ashish_pc.feedback.R.id.timePicker;

public class messageact extends Activity {

    private EditText messageET;
    AlertDialog.Builder alertbuilder;
    TextView tvf;
    int len=0;
    String[] listrefreshdata;
    private List<populatefrnd> offerdata = new ArrayList<populatefrnd>();
    private ListView messagesContainer;
    private Button sendBtn,submit;
    String fname,n;
    TimePicker alarmTimePicker;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    String femail;
    String fdob,pass;
    StringBuilder sb;
    String fstatus;
    String peremail,fetchemail;
    SharedPreferences sharedPreferences1,sharedpreferences;
    Bundle extras;
    ListView lvc;
    Dialog dialog2;
    View vvv;
    // private ChatAdapter adapter;
    ArrayAdapter<ChatMessage> adapter;
    private ArrayList<ChatMessage> chatHistory= new ArrayList<ChatMessage>();
    private ArrayList<ChatMessage> obj= new ArrayList<ChatMessage>();
    private static messageact inst;

    public static messageact instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageact);


        alarmTimePicker = (TimePicker) findViewById(timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        tvf = (TextView) findViewById(R.id.frndname);
       extras = getIntent().getExtras();
        fname = extras.getString("name");
        femail = extras.getString("email");
        fdob = extras.getString("dob");

        fstatus = extras.getString("status");
        if(fname.length()>6)
        {
            n = fname.substring(0,4);
            n=n+"..";
        }
        tvf.setText(n);
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        sharedPreferences1 = getSharedPreferences("MySchedPref",Context.MODE_APPEND);
        String channel = (sharedpreferences.getString("emailKey",null));
        pass = (sharedpreferences.getString("passKey",null));
        peremail=channel+femail;
        fetchemail=femail+channel;
        String ch = (sharedpreferences.getString(femail,"00"));
        Button bt = (Button) findViewById(R.id.profg);
        if(ch.equals("00"))
        bt.setText("hide");
        else
        bt.setText("show");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setUserId(1256);
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(false);

                messageET.setText("");

                //displayMessage(chatMessage);
                new mytask().execute(chatMessage);

            }
        });


        initControls();


       // new mytasktofetch().execute();



    }


    private void initControls()
    {
       // messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        Button btn = (Button) findViewById(R.id.profg);
        //TextView meLabel = (TextView) findViewById(R.id.meLbl);
        //  TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        // companionLabel.setText("My Buddy");// Hard Coded
        loadDummyHistory();


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = messageET.getText().toString();
                messageET.setText("");

                final View view = (LayoutInflater.from(messageact.this)).inflate(R.layout.alarm_activity, null);
                alertbuilder = new AlertDialog.Builder(messageact.this);
                alertbuilder.setView(view);
                alertbuilder.setCancelable(true);
                final Dialog dialog = alertbuilder.create();
                dialog.show();
               submit = (Button) view.findViewById(R.id.time_button);
                alarmTimePicker = (TimePicker) view.findViewById(timePicker);
                alarmTimePicker.setIs24HourView(true);
               submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                        calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
                        String hour = String.valueOf(alarmTimePicker.getCurrentHour());
                        String min = String.valueOf(alarmTimePicker.getCurrentMinute());
                        if(Integer.parseInt(hour)<10)
                            hour = "0"+hour;
                        if(Integer.parseInt(min)<10)
                            min = "0"+min;
                        String time = hour+":"+min;
                        String a = femail+":"+message;
                        SharedPreferences.Editor editor = sharedPreferences1.edit();
                        editor.putString(time, a);
                        editor.commit();
                        Log.d("message sent","true");
                       Toast.makeText(messageact.this,"Message Scheduled",Toast.LENGTH_SHORT).show();
                        view.setVisibility(view.GONE);
                        dialog.dismiss();

                        Intent myIntent = new Intent(messageact.this, AlarmReceiver.class);
                        //myIntent.putExtra("Message",getMessage());
                        final int _id = (int) System.currentTimeMillis();
                        pendingIntent = PendingIntent.getBroadcast(messageact.this,_id, myIntent, PendingIntent.FLAG_ONE_SHOT);
                        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(),pendingIntent );
                        System.out.println(sharedPreferences1.getString(time,null));
                        System.out.println(time);


                 }});



            }
        });
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
              /* Intent i = new Intent("com.example.ashish_pc.feedback.profscroll");
                i.putExtra("name",fname);
                i.putExtra("status",fstatus);
                i.putExtra("dob",fdob);
                startActivity(i);*/
                sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
               // String cha = (sharedpreferences.getString("hide", null));
                String cha = (sharedpreferences.getString(femail,"00"));
                if (cha.equals("11")) {
                    /*chatHistory.clear();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("hide", "11");
                    editor.commit();
                    Button bt = (Button) findViewById(R.id.profg);
                    bt.setText("show");
                    chatHistory.clear();
                    ArrayAdapter<ChatMessage> adapter = new Mylistadapter();
                    messagesContainer.setAdapter(adapter);*/
                    vvv = (LayoutInflater.from(messageact.this)).inflate(R.layout.passcheck, null);
                    AlertDialog.Builder alertbuilder = new AlertDialog.Builder(messageact.this);
                    alertbuilder.setView(vvv);
                    alertbuilder.setCancelable(true);
                    dialog2 = alertbuilder.create();
                    dialog2.show();


                } else {
                   /* vvv = (LayoutInflater.from(messageact.this)).inflate(R.layout.passcheck, null);
                    AlertDialog.Builder alertbuilder = new AlertDialog.Builder(messageact.this);
                    alertbuilder.setView(vvv);
                    alertbuilder.setCancelable(true);
                    dialog2 = alertbuilder.create();
                    dialog2.show();*/
                   /* Button b = (Button) vvv.findViewById(R.id.button2);
                    EditText e = (EditText)vvv.findViewById(R.id.editText2);
                    String s = e.getText().toString();
                    Button bt = (Button) findViewById(R.id.profg);
                    bt.setText("hide");
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("hide", "00");
                    editor.commit();*/

                    //chatHistory.clear();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    //editor.putString("hide", "11");
                    editor.putString(femail, "11");
                    editor.commit();
                    Button bt = (Button) findViewById(R.id.profg);
                    bt.setText("show");
                    chatHistory.clear();
                    ArrayAdapter<ChatMessage> adapter = new Mylistadapter();
                    messagesContainer.setAdapter(adapter);
                }

            }
        });


    }

    public void cc(View v)
    {
        EditText e = (EditText)vvv.findViewById(R.id.editText2);
        String s = e.getText().toString();
        if(s.equals(pass))
        {
            Button bt = (Button) findViewById(R.id.profg);
            bt.setText("hide");
            SharedPreferences.Editor editor = sharedpreferences.edit();
           // editor.putString("hide", "00");
            editor.putString(femail, "00");
            editor.commit();
        }
        else {
            Toast.makeText(messageact.this, "enter right password.", Toast.LENGTH_LONG).show();
            chatHistory.clear();
        }
        dialog2.dismiss();

    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
    }

    private void loadDummyHistory() {



       /* ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(true);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(true);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);*/
        adapter = new Mylistadapter();
        messagesContainer.setAdapter(adapter);

        new mytasktofetch().execute();



    }








    private class Mylistadapter extends ArrayAdapter<ChatMessage> {
        public Mylistadapter() {
            super(messageact.this, R.layout.list_item_chat_message, chatHistory);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View itemview = convertView;
            if (itemview == null) {
                itemview = getLayoutInflater().inflate(R.layout.list_item_chat_message, parent, false);

            }
            ChatMessage currentquestion = chatHistory.get(position);
            LinearLayout ln = (LinearLayout) itemview.findViewById(R.id.contentWithBackground);
            if (currentquestion.getIsme() == false) {
                ln.setBackgroundResource(R.drawable.out_message_bg);
                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) ln.getLayoutParams();
                layoutParams.gravity = Gravity.LEFT;
                ln.setLayoutParams(layoutParams);
            }
            else
            {
                ln.setBackgroundResource(R.drawable.in_message_bg);
                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) ln.getLayoutParams();
                layoutParams.gravity = Gravity.RIGHT;
                ln.setLayoutParams(layoutParams);
            }

            TextView tv = (TextView) itemview.findViewById(R.id.txtInfo);
            tv.setText("" + currentquestion.getDate());
            TextView tv1 = (TextView) itemview.findViewById(R.id.txtMessage);
            tv1.setText("" + currentquestion.getMessage());
            return itemview;
        }


    }







    class mytask extends AsyncTask<ChatMessage,ChatMessage,String>
    {
        private ArrayAdapter<ChatMessage> adapter;

        @Override

        protected void onPreExecute()
        {
            adapter = (ArrayAdapter<ChatMessage> )messagesContainer.getAdapter();

        }

        protected String doInBackground(ChatMessage... params) {
            String chat = params[0].getMessage();
            String datem = params[0].getDate();
            String sender = "false";
            String str ="";
            try
            {
                String email = peremail;

                String link = "http://192.168.68.47/msgstore.php";
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("chat", "UTF-8") + "=" + URLEncoder.encode(chat, "UTF-8");
                data += "&" + URLEncoder.encode("datem", "UTF-8") + "=" + URLEncoder.encode(datem, "UTF-8");
                data += "&" + URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(sender, "UTF-8");
                data += "&" + URLEncoder.encode("fetchemail", "UTF-8") + "=" + URLEncoder.encode(fetchemail, "UTF-8");


                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                sb = new StringBuilder();
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


    //to fetch data



    class mytasktofetch extends AsyncTask<Void,ChatMessage,String>
    {
        private ArrayAdapter<ChatMessage> adapter;

        @Override

        protected void onPreExecute()
        {
            adapter = (ArrayAdapter<ChatMessage> )messagesContainer.getAdapter();
          //  messagesContainer.setAdapter(adapter);

        }

        protected String doInBackground(Void... params) {

            String str ="";
            sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String channel = (sharedpreferences.getString(femail,"00"));
            if(channel.equals("00")) {
                try {
                    // String email = peremail;

                    String link = "http://192.168.68.47/msgfetch.php";
                    String data = URLEncoder.encode("peremail", "UTF-8") + "=" + URLEncoder.encode(peremail, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    sb = new StringBuilder();
                    String line = null;
                    String s;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        str = sb.toString();
                        String[] listrefreshdata = str.split("\\*");
                        if (chatHistory.size() < listrefreshdata.length) {
                            chatHistory.clear();
                            for (int i = 0; i < listrefreshdata.length; ++i) {
                                if (listrefreshdata[i].equals(" "))
                                    continue;
                                else {
                                    String[] listrefreshdataaa = listrefreshdata[i].split("\\+");
                                    ChatMessage cm = new ChatMessage();
                                    if (listrefreshdataaa[2].equals("true"))
                                        cm.setMe(true);
                                    else
                                        cm.setMe(false);
                                    cm.setDate(listrefreshdataaa[1]);
                                    cm.setMessage(listrefreshdataaa[0]);
                                    cm.setId(12);
                                    cm.setUserId(145);
                                    chatHistory.add(cm);
                                    //obj.add(cm);
                                }
                            }
                        }

                        //chatHistory.clear();
                        // adapter.add(cm);
                    }


                } catch (Exception e) {

                }
            }
           return str;
        }

        protected void onProgressUpdate(ChatMessage... values) {
       /*   adapter = (ArrayAdapter<ChatMessage> )messagesContainer.getAdapter();*/
           // adapter.add(values[0]);
           // messagesContainer.setAdapter(adapter);
        }
        protected void onPostExecute(String result)
        {
            Runnable timedTask = new Runnable(){

                             public void run() {

                               new mytasktofetch().execute();

                              }};
                            new Thread(timedTask).start();
                           try {

                                 Thread.sleep(10);
                                 }
                              catch(Exception e)
                                {

                                  }
        }



    }





}