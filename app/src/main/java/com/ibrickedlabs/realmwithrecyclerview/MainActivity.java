package com.ibrickedlabs.realmwithrecyclerview;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.UUID;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    //EditText
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    //Buttons
    Button uploadButton;
    Button displayButton;

    //Realm
    private Realm myRealm;

    //Llayout
    LinearLayout toplayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //EditText Intz
        firstName = (EditText) findViewById(R.id.fnameField);
        lastName = (EditText) findViewById(R.id.lnameField);
        phoneNumber = (EditText) findViewById(R.id.phnField);
        //ButtonIntz
        uploadButton = (Button) findViewById(R.id.uploadButton);
        displayButton = (Button) findViewById(R.id.displayButton);
        //Top linear layout
        toplayout = (LinearLayout) findViewById(R.id.topLayout);

        //Get the default instance of Realm
        myRealm = Realm.getDefaultInstance();

        //Click on upload button
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fnameStr = firstName.getText().toString();
                final String lnameStr = lastName.getText().toString();
                final String phnStr = phoneNumber.getText().toString();

                if (!TextUtils.isEmpty(fnameStr) && !TextUtils.isEmpty(lnameStr) && !TextUtils.isEmpty(phnStr)) {

                    //we are adding synchronously
                    // UUID --> genereates random and unique id since we have id as PK                                  in our model class we are using it
                    myRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            //This block just save the contact into realm db
                            String id = UUID.randomUUID().toString();
                            Contact contact = realm.createObject(Contact.class, id);
                            contact.setFirstname(fnameStr);
                            contact.setLastname(lnameStr);
                            contact.setPhoneNumber(phnStr);
                            Snackbar.make(toplayout, "Saved successfully!", Snackbar.LENGTH_SHORT).show();
                            firstName.setText("");
                            lastName.setText("");
                            phoneNumber.setText("");

                        }
                    });

                }
            }
        });

        //DisplayButton
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DisplayActivity.class));

                //Dont finish the acitivty cuz we have displayHomeUp button in the next Activity
            }
        });


    }
}
