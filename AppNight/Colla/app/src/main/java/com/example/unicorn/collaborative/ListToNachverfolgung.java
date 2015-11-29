package com.example.unicorn.collaborative;

/**
 * Created by User on 29.11.2015.
 */
public class ListToNachverfolgung {
    public ListToNachverfolgung(String name, int missing) {
        this.name = name;
        this.missing = missing;
    }

    public ListToNachverfolgung(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   private String name;

    public int getMissing() {
        return missing;
    }

    public void setMissing(int missing) {
        this.missing = missing;
    }

    private int missing;
}
