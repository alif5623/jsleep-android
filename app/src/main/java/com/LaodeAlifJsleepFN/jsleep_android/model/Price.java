package com.LaodeAlifJsleepFN;


/**
 * Write a description of class Price here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Price
{
    // instance variables - replace the example below with your own
    public double price;
 //   public int discount;
    public double discount;
  //  public double rebate;

    public Price(double price){
        this.price = price;
        this.discount = 0;
     //   this.rebate = 0;
    }
    /*
    public Price(double price, int discount)
    {
        this.price = price;
        this.discount = discount;
        this.rebate = 0;
    }*/
    public Price(double price, double discount){
        this.price = price;
     //   this.rebate = discount;
        this.discount = discount;
    }

    public String toString(){
        return "\nPrice: " + this.price + "\nDiscount: " + this.discount;
    }
    /*
    private double getDiscountedPrice(){
        if (discount > 100.0){
            return 100;
        }else if(discount == 100){
            return 0;
        }else{
            return price - discount;
        }
    }
    private double getRebatedPrice(){
        if(rebate > price){
            return price;
        }else{
            return price - rebate;
        }
    }

    */

}
