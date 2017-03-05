package com.example.ashish_pc.feedback;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class companydetail extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fm;
    ImageView iv;
    int d=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companydetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_frame,new mainfragment()).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.companydetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addquestion) {

            fm.beginTransaction().replace(R.id.fragment_frame,new addquestionfragment()).commit();
        } else if (id == R.id.nav_feedback) {
            fm.beginTransaction().replace(R.id.fragment_frame,new feedbackpagefragment()).commit();

        } else if (id == R.id.nav_reviews) {

            fm.beginTransaction().replace(R.id.fragment_frame,new viewreview()).commit();

        } else if (id == R.id.nav_edit) {
            fm.beginTransaction().replace(R.id.fragment_frame,new fragmentofedit()).commit();

        } else if (id == R.id.nav_help) {
            fm.beginTransaction().replace(R.id.fragment_frame,new helpfragment()).commit();
        } else if (id == R.id.nav_contact) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   /* public  void run()
    {
        try {
            for(int i=0;i<3;++i) {
                if (d == 0)
                {
                    iv = (ImageView)findViewById(R.id.imageViewreviewmainpic)
                }
                else if (d == 1)

                else {

                    d = 0;
                }
                ++d;
                Thread.sleep(2000);
            }
        }
        catch (Exception ex)
        {

        }
    }*/

}
