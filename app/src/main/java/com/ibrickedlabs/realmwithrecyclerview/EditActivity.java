package com.ibrickedlabs.realmwithrecyclerview;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditActivity extends AppCompatActivity {
    //EditText
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;

    //Buttons
    Button updateButton;
    Button cancelButton;

    //Realm
    private Realm myRealm;
    //Real Model Class
    private Contact contact;
    //To store realm resuts
    private RealmResults<Contact> realmResults;
    //To get extra elements from previous activity
    private Bundle bundle;
    int pos;


    //Top layout for snackbar
    LinearLayout topLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //To enable uparrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Extract the intent extra
        bundle = getIntent().getExtras();
        if (bundle != null)
            pos = bundle.getInt("POSITION");

        //Intialise the real obj
        myRealm = Realm.getDefaultInstance();
        //Get all the obj's of Contact class here
        realmResults = myRealm.where(Contact.class).findAll();
        //Refer to a specifc obj
        contact = realmResults.get(pos);

        //EditText intz
        firstName = (EditText) findViewById(R.id.fnameField);
        lastName = (EditText) findViewById(R.id.lnameField);
        phoneNumber = (EditText) findViewById(R.id.phnField);

        //Top Layout intz
        topLayout = (LinearLayout) findViewById(R.id.topLayout);
        /**
         * Fill the views with the respective data
         */
        setUpTheViews();


        //Buttons Intz
        updateButton = (Button) findViewById(R.id.updateButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        /**
         * Always remember Update & Delete operations must be written between begin &           commit Transactions or in execute method
         */
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        contact.setFirstname(firstName.getText().toString());
                        contact.setLastname(lastName.getText().toString());
                        contact.setPhoneNumber(phoneNumber.getText().toString());
                        Snackbar.make(topLayout, "Updated successfully!", Snackbar.LENGTH_SHORT).show();


                    }
                });
            }
        });

        /**
         * take user to pervious activity
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditActivity.this, DisplayActivity.class));
                finish();
            }
        });


    }

    /**
     * Populating with respective element
     */
    private void setUpTheViews() {
        firstName.setText(contact.getFirstname());
        lastName.setText(contact.getLastname());
        phoneNumber.setText(contact.getPhoneNumber());
    }

    @Override
    protected void onStop() {
        super.onStop();
        myRealm.close();
    }
}
