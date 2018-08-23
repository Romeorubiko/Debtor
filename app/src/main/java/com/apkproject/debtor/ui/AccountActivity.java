package com.apkproject.debtor.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apkproject.debtor.R;
import com.apkproject.debtor.dataStructure.person.User;
import com.apkproject.debtor.dataStructure.tools.Tool;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;

public class AccountActivity extends AppCompatActivity {
    CircularImageView mPhoto;
    TextView mMail, mName, mCurrency, mSymbol;
    FloatingActionButton mFloatButtonEdit;

    User me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

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


    }
}
