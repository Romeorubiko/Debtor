package com.apkproject.debtor.ui;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.apkproject.debtor.R;
import com.apkproject.debtor.dataStructure.person.User;
import com.apkproject.debtor.dataStructure.tools.Tool;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;

public class AccountActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    CircularImageView mPhoto;
    TextView mMail, mName, mCurrency, mSymbol;
    FloatingActionButton mFloatButtonEdit;
    User me;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        try {
            me = Tool.getCurrentUser(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        mPhoto = findViewById(R.id.account_avatar);
        mPhoto.setImageBitmap(me.getPhoto());

        mMail = findViewById(R.id.account_email);
        mMail.setText(me.getEmail());
        mName = findViewById(R.id.account_name);
        mName.setText(me.getName());
        mCurrency = findViewById(R.id.account_currency);
        mCurrency.setText(me.getCurrency().toString());
        mSymbol = findViewById(R.id.account_currency_symbol);
        mSymbol.setText(me.getCurrency().getSymbol());

        mFloatButtonEdit = findViewById(R.id.account_floating_button_edit);
        mFloatButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, AccountEditActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_account_toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        boolean pressed = false;
        //new activity related to the button pressed


        if (id == R.id.my_debts_drawer) {
            intent = new Intent(AccountActivity.this, MyListsActivity.class);
            pressed = true;
        }  else if (id == R.id.history_drawer) {
            intent = new Intent(AccountActivity.this, HistoryActivity.class);
            pressed = true;
        } else if (id == R.id.sync_drawer) {
            //todo intent = new Intent(MyListsActivity.this, SyncActivity.class);
        } else if (id == R.id.contacts_drawer) {
            intent = new Intent(AccountActivity.this, ContactListActivity.class);
            pressed = true;
        } else if (id == R.id.notifications_drawer) {
            intent = new Intent(AccountActivity.this, NotificationsActivity.class);
            pressed = true;
        }

        if (pressed) {
            startActivity(intent);
        } else{
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}

