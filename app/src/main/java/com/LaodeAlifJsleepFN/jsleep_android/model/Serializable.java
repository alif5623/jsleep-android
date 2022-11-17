package com.LaodeAlifJsleepFN;
import java.util.HashMap;


public class Serializable implements Comparable<Serializable>
{
    public final int id;
    private static HashMap<Class<?>, Integer> mapCounter = new HashMap<Class<?>, Integer>();
    protected Serializable()
    {
        Integer counter = mapCounter.get(getClass());
        if(counter == null){
            counter = 0;
        }else{
            counter += 1;
        }
       // System.out.println("ID: " + id);
        mapCounter.put(getClass(), counter);
        this.id = counter;
    }

    public int compareTo(Serializable serial){
        return this.id - serial.id;
    }

    public boolean equals (Object other){
        return other instanceof Serializable && ((Serializable) other).id == id;
    }

    public boolean equals(Serializable serial){
        if(serial.id == this.id){
            return true;
        }else{
            return false;
        }
    }

    public static <T extends Serializable> Integer getClosingId(Class<T> tClass){
        return mapCounter.get(tClass);
    }

    public static <T extends Serializable> Integer setClosingId(Class<T> tClass, int id){
        return mapCounter.put(tClass, id);
    }
}
