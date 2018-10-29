package com.ibrickedlabs.realmwithrecyclerview;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Contact extends RealmObject {
    @PrimaryKey
    private String id;

    public String getId() {
        return id;
    }

    public Contact() {
    }

    public void setId(String id) {
        this.id = id;
    }

    private  String firstname;
    private  String lastname;
    private  String phoneNumber;

    public Contact(String firstname, String lastname, String phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
