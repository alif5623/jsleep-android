package com.LaodeAlifJsleepFN.jsleep_android.model;

import com.LaodeAlifJsleepFN.Account;
import com.LaodeAlifJsleepFN.Renter;
import com.LaodeAlifJsleepFN.Serializable;

/**
 * Write a description of class Invoice here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Invoice extends Serializable
{
    public int buyerId;
    public int renterId;
    public PaymentStatus status;
    public RoomRating rating;

    public enum RoomRating{
        NONE, BAD, NEUTRAL, GOOD
    }
    public enum PaymentStatus{
        FAILED, WAITING, SUCCESS
    }
    protected Invoice(int buyerId, int renterId)
    {
        //super(id);
     //   this.id = id;
        this.buyerId = buyerId;
        this.renterId = renterId;
        //this.rating = RoomRating.NONE;
        //this.status = PaymentStatus.WAITING;
    }
    public Invoice(Account buyer, Renter renter){
      //  super(id);
       // this.id = id;
        this.buyerId = buyer.id;
        this.renterId = renter.id;
      //  this.rating = RoomRating.NONE;
      //  this.status = PaymentStatus.WAITING;
    }

    public String print(){
        return "\nID: " + this.id + "\nBuyer: " + this.buyerId + "\nRenter: " + this.renterId;
    }
}
