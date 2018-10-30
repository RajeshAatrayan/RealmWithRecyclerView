package com.ibrickedlabs.realmwithrecyclerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private static final int GALLERY_CODE = 123;
    //EditText
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    //Buttons
    Button uploadButton;
    Button displayButton;

    //Realm
    private Realm myRealm;
    private byte[] profileArray;

    //Llayout
    LinearLayout toplayout;

    private CircleImageView contactImage;


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

        //Imageview
        contactImage=(CircleImageView) findViewById(R.id.contactPicture);
        contactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(intent,GALLERY_CODE);
            }
        });



        //Get the default instance of Realm
        myRealm = Realm.getDefaultInstance();

        //Click on upload button
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fnameStr = firstName.getText().toString();
                final String lnameStr = lastName.getText().toString();
                final String phnStr = phoneNumber.getText().toString();

                if (!TextUtils.isEmpty(fnameStr) && !TextUtils.isEmpty(lnameStr) && !TextUtils.isEmpty(phnStr) && profileArray.length>0) {

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
                            contact.setProfileImageArray(profileArray);
                            Snackbar.make(toplayout, "Saved successfully!", Snackbar.LENGTH_SHORT).show();
                            firstName.setText("");
                            lastName.setText("");
                            phoneNumber.setText("");
                            //Set the image back to previous one
                            contactImage.setImageResource(R.drawable.cp);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK){
            //Get the uri of the selected image
            Uri uri=data.getData();
            Bitmap bitmap = null;
            try {
                //Convert uri into bitmap
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                //set the image to imageview for reference
                contactImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //convert bitmap to byte[],inorder to store into realm
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //Fetching out the desire format
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            //Assign to gloabl var
            profileArray = stream.toByteArray();


        }



    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }
}
