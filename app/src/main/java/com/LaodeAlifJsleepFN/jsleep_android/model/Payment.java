package com.LaodeAlifJsleepFN.jsleep_android.model;

import com.LaodeAlifJsleepFN.Account;
import com.LaodeAlifJsleepFN.Renter;
import com.LaodeAlifJsleepFN.Room;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Payment extends Invoice {

    public Date to;
    public Date from;
    private int roomId;
    SimpleDateFormat SDFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Payment(int buyerId, int renterId, int roomId, Date from, Date to) {
        super(buyerId, renterId);
        //  this.id = id;
        this.buyerId = buyerId;
        this.renterId = renterId;
        //this.time = time
        this.roomId = roomId;
        /*this.from = Calendar.getInstance();
        this.to = Calendar.getInstance();
        this.from.getTime();
        this.to.add(Calendar.DATE, 2);*/
        //  this.from = new Date();
        //this.to = new Date();
        this.from = from;
        this.to = to;
    }

    public Payment(Account buyer, Renter renter, int roomId, Date from, Date to) {
        super(buyer.id, renter.id);
        //this.id = id;
      //  this.buyerId = buyer.id;
      //  this.renterId = renter.id;
        /*
        this.from = Calendar.getInstance();
        this.to = Calendar.getInstance();
        this.from.getTime();
        this.to.add(Calendar.DATE, 2);*/
      //  this.from = new Date();
      //  this.to = new Date();
        this.roomId = roomId;
        this.from = from;
        this.to = to;
    }

    public String print() {
        return "\nId: " + this.id + "\nBuyer: " + this.buyerId + "\nRenter: " + this.renterId +
                "\nRoom ID: " + this.roomId + "\nFrom: " + this.from + "\nTo: " + to;
    }

    public int getRoomId() {
        return roomId;
    }
    /*
    public String getDuration(){
        String strfrom = SimpleDateFormat.format(this.from.getTime());
        String strto = SimpleDateFormat.format(this.to.getTime());
        return strfrom + "-" + strto;
       // return SDFormat.format(this.from.getTime() + "-" + this.to.getTime());
    } */

    public static boolean makeBooking(Date from, Date to, Room room) {
        if (availability(from, to, room)) {
            Calendar start = Calendar.getInstance();
           // start.setTime(from);
            Calendar end = Calendar.getInstance();
        //    end.setTime(to);
       /*     for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                room.booked.add(date);
            }*/
            return true;

            //  room.booked.add(from);
            //  room.booked.add(to);
        }
        return false;
    }
    /*
    public String getTime() {
        SimpleDateFormat SDFormat = new SimpleDateFormat(" 'Formatted Date' = dd MMMM yyyy" );
        return SDFormat.format(time.getTime());
    }*/

    public static boolean availability(Date from, Date to, Room room) {
        Calendar start = Calendar.getInstance();
        start.setTime(from);
        Calendar end = Calendar.getInstance();
        end.setTime(to);
        if (start.after(end) || start.equals(end)) {
            return false;
        }
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            if (room.booked.contains(date)) {
                return false;
            }
        }
        return true;
    }
        /*
        if(to.before(from)){
                return false;
            }
            if(room.booked.isEmpty()){
                return true;
            }

            for(int i = 0; i < room.booked.size(); i++){
                if(to.after(room.booked.get(i)) && to.before(room.booked.get(i + 1))){
                    return false;
                }else if(from.after(room.booked.get(i)) && from.before(room.booked.get(i + 1))){
                    return false;
                }
            }
            return true;*/
}
