package com.apkproject.debtor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.apkproject.debtor.R;
import com.apkproject.debtor.dataStructure.person.User;
import com.apkproject.debtor.dataStructure.tools.Tool;
import com.apkproject.debtor.recyclerView.ContactAdapter;
import com.apkproject.debtor.test.Test;

import java.io.IOException;
import java.util.Currency;

public class MyListsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    User me;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists);

        try {
            Test.createUser(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //get my user
        try {
            me = Tool.getCurrentUser(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        // Lookup the recyclerview in activity layout
        recyclerView = (RecyclerView) findViewById(R.id.mylists_recyclerview);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(new ContactAdapter(this, Test.createMyLists()));

        //set header
        TextView iOwe = (TextView) findViewById(R.id.header_contact_adapter_you_owe);
        iOwe.setText(Tool.toCurrencyAndSymbol(me.getCurrentIOwe(),me.getCurrency()));
        TextView iAreOwed = (TextView) findViewById(R.id.header_contact_adapter_you_are_owed);
        iAreOwed.setText(Tool.toCurrencyAndSymbol(me.getCurrentIAreOwed(),me.getCurrency()));

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
        getMenuInflater().inflate(R.menu.my_lists, menu);
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

        Intent intent = null;
        boolean pressed = false;
        //new activity related to the button pressed


        if (id == R.id.my_debts_drawer) {
           //do nothing, we're already in this Activity
        }  else if (id == R.id.history_drawer) {
            //todo intent = new Intent(MyListsActivity.this, HistorytActivity.class);
        } else if (id == R.id.contacts_drawer) {
            //todo intent = new Intent(MyListsActivity.this, ContactListActivity.class);
        } else if (id == R.id.sync_drawer) {
            //todo intent = new Intent(MyListsActivity.this, SyncActivity.class);
        } else if (id == R.id.settings_drawer) {
            intent = new Intent(MyListsActivity.this, AccountActivity.class);
            pressed = true;
        }

        if (pressed) {
            startActivity(intent);
        } else{
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
