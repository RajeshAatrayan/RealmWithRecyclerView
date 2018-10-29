package com.ibrickedlabs.realmwithrecyclerview;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * We must write this class and extends Application class because it will the class which is the first one to launc when the user opens the app..So, we are configuring our realm db here
 * <p>
 * Dont forget to mention it in manifestfile
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("demo.realm").build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
