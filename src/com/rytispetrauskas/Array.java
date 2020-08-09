package com.rytispetrauskas;

import java.util.ArrayList;

public class Array {
    private ArrayList<ArrayList<Integer>> array = new ArrayList<>();

    public void setValue(int value, int counter) {
        synchronized (array) {
            ArrayList<Integer> newArray = new ArrayList<>();
            newArray.add(value);
            newArray.add(counter);
            array.add(newArray);
        }
    }

    public ArrayList<ArrayList<Integer>> getValue() {
        synchronized (array) {
            return array;
        }
    }
}
