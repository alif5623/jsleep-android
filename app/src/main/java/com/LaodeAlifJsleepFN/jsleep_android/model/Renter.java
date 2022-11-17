package com.LaodeAlifJsleepFN;

import java.util.regex.*;

/**
 * Write a description of class Renter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Renter extends Serializable
{
    public String phoneNumber;
    public String address = "";
    public String username ;
    public static final String REGEX_NAME = "^[A-Z][A-Za-z0-9_]{4,20}$";
    public static final String REGEX_PHONE = "^[0-9]{9,12}$";
    /*
    public Renter(int id, String username)
    {
        //super(id);
       // this.id = id;
        this.username = username;
    }
    public Renter(int id, String username, String address){
       // super(id);
       // this.id = id;
        this.username = username;
        this.address = address;
    }
    public Renter(int id, String username, int phoneNumber){
       // super(id);
      //  this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }
    public Renter(int id, String username, int phoneNumber, String address){
      //  super(id);
       // this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }*/
    public Renter(String username, String phoneNumber, String address){
       this.username = username;
       this.phoneNumber = phoneNumber;
       this.address = address;
    }
    public boolean validate(){
        Pattern patternPhone = Pattern.compile(REGEX_PHONE);
        Matcher matcherPhone = patternPhone.matcher(phoneNumber);
        boolean matchPhone = matcherPhone.find();
        Pattern patternName = Pattern.compile(REGEX_NAME);
        Matcher matcherName = patternName.matcher(username);
        boolean matchName = matcherName.find();
        return matchName && matchPhone;
    }

}
