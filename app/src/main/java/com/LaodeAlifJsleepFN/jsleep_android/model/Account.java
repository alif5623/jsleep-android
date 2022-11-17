package com.LaodeAlifJsleepFN;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account extends Serializable
{
    public String name;
    public String email;
    public String password;
    public Renter renter;
    public static final String REGEX_EMAIL = "^[A-Za-z0-9]+@(.+)$";
    public static final String REGEX_PASSWORD = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)*[a-zA-Z\\d]{8,}$";
    public double balance;
    public Account(String name, String email, String password)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = 0;
    }

    public String toString(){
        return "\nID: " + this.id + "\nName: " + this.name + "\nEmail: " + this.email + "\nPassword: " + this.password;
    }
    public Object write(){
        return null;
    }
    public Boolean read(String a){
        return true;
    }

    public boolean validate(){
        Pattern patternEmail = Pattern.compile(REGEX_EMAIL);
        Matcher matcherEmail = patternEmail.matcher(email);
        boolean matchEmail = matcherEmail.find();
        Pattern patternPassword = Pattern.compile(REGEX_PASSWORD);
        Matcher matcherPassword = patternPassword.matcher(password);
        boolean matchPassword = matcherPassword.find();
        return matchEmail && matchPassword;
    }
}
