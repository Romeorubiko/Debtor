package com.apkproject.debtor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.apkproject.debtor.R;
import com.apkproject.debtor.dataStructure.person.User;
import com.apkproject.debtor.dataStructure.tools.Storage;
import com.apkproject.debtor.dataStructure.tools.Tool;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;

public class AccountEditActivity extends AppCompatActivity {

    public static final int GET_FROM_GALLERY = 3;

    private AutoCompleteTextView mEmail;
    private EditText mName;
    private Spinner mCurrency;
    private TextView mSymbol;
    private Button mApply;
    private CircularImageView mPhoto;

    private User me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            me = Tool.getCurrentUser(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        mPhoto = findViewById(R.id.account_edit_avatar);
        mPhoto.setImageBitmap(me.getPhoto());

        mEmail = findViewById(R.id.account_edit_email);
        mEmail.setText(me.getEmail());
        mName = findViewById(R.id.account_edit_name);
        mName.setText(me.getName());
        mCurrency = findViewById(R.id.account_edit_spinner_currency);
        //todo mCurrency.setText(me.getCurrency().toString());
        mSymbol = findViewById(R.id.account_edit_symbol);
        mSymbol.setText(me.getCurrency().getSymbol());

        mApply = findViewById(R.id.account_edit_apply);
        mApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                me.setEmail(mEmail.getText().toString());
                me.setName(mName.getText().toString());

                //todo set currency and set photo
                //todo Storage.writeFirebase(Storage.GROUPUSERS, me.getEmail(),me);
                try {
                    Storage.writeLocalStorage(AccountEditActivity.this, Storage.ME, me);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Tool.toastMessage(AccountEditActivity.this, "Changes applied");
                Intent intent = new Intent(AccountEditActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

    }

}
