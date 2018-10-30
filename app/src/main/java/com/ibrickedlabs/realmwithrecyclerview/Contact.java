package com.ibrickedlabs.realmwithrecyclerview;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Contact extends RealmObject {
    @PrimaryKey
    private String id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    public byte[] profileImageArray;


    public Contact() {
    }

    public Contact(String firstname, String lastname, String phoneNumber, byte[] profileImageArray) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.profileImageArray = profileImageArray;
    }

    /**
     * Getters and settters
     *
     */
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public byte[] getProfileImageArray() {
        return profileImageArray;
    }

    public void setProfileImageArray(byte[] profileImageArray) {
        this.profileImageArray = profileImageArray;
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
