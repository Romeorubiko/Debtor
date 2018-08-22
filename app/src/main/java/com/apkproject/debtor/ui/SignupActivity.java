package com.apkproject.debtor.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apkproject.debtor.Manifest;
import com.apkproject.debtor.R;
import com.apkproject.debtor.dataStructure.person.User;
import com.apkproject.debtor.dataStructure.tools.Currency;
import com.apkproject.debtor.dataStructure.tools.SerialBitmap;
import com.apkproject.debtor.dataStructure.tools.Storage;
import com.apkproject.debtor.dataStructure.tools.Tool;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.IOException;

public class SignupActivity extends AppCompatActivity {

    public static final int GET_FROM_GALLERY = 3;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView, mNameView;
    private Spinner mSpinnerView;
    private Button mEmailSignInButton;
    private CircularImageView mphoto;
    //String text = mySpinner.getSelectedItem().toString();

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private FirebaseAuth mAuth = null;
    private FirebaseUser currentUser = null;
    private SerialBitmap photoUploaded = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // todo Set up the login form. (name, surname, currency preference)
        //TODO: add image during signup
        mEmailView = (AutoCompleteTextView) findViewById(R.id.signup_email);
        mPasswordView = (EditText) findViewById(R.id.signup_password);
        mNameView = (EditText) findViewById(R.id.signup_name);
        mSpinnerView = (Spinner) findViewById(R.id.signup_currency_spinner);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_up_button);
        mphoto = (CircularImageView) findViewById(R.id.signup_add_image);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) { //if non focus and written
                    attemptSignup();
                    return true;
                }
                return false;
            }
        });


        String name = mNameView.getText().toString();
        String currency = mSpinnerView.getSelectedItem().toString();

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignup();
                mAuth.createUserWithEmailAndPassword(mEmailView.getText().toString(), mPasswordView.getText().toString())
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("AuthSuccess", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                    String email = mEmailView.getText().toString();
                                    String name = mNameView.getText().toString();
                                    //todo Currency currency = (Currency) mSpinnerView.getSelectedItem();
                                    Currency currency = Currency.EUR;
                                    User newUser = new User(name, mEmailView.getText().toString(), currency);
                                    if (photoUploaded != null) newUser.setPhoto(photoUploaded);
                                    Storage.writeFirebase(Storage.GROUPUSERS, mAuth.getUid(), newUser);
                                    try {
                                        Storage.writeLocalStorage(SignupActivity.this, Storage.ME, newUser);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent = new Intent(SignupActivity.this, MyListsActivity.class);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Authfailure", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
        });

        mphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check permission
                int permissionCheck = ContextCompat.checkSelfPermission(SignupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SignupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }


    private void updateUI(FirebaseUser currentUser) {
        //todo uptade ui if the user already exists
    }

    private void attemptSignup(){
        if (mAuth != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Check if user is signed in (non-null) and update UI accordingly.
            currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == SignupActivity.RESULT_OK) {
            Uri selectedImage = data.getData();
            //setup photo and resize
                photoUploaded = new SerialBitmap(new File(getPath(selectedImage)));
                mphoto.setImageBitmap(photoUploaded.getBitmap());
        }
    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Tool.toastMessage(this,"Permission denied");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
