package com.ibrickedlabs.realmwithrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;

public class DisplayActivity extends AppCompatActivity {

    private Realm myRealm;
    private RecyclerView recyclerView;
    private ContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        myRealm = Realm.getDefaultInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpAdapter();
    }

    private void setUpAdapter() {
        RealmResults<Contact> realmResults = myRealm.where(Contact.class).findAll();
        mAdapter = new ContactAdapter(this, realmResults, myRealm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);


    }


    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
    }
}
