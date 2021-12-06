package com.example.project5;

//import javafx.stage.FileChooser;
//import javafx.stage.Stage;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class defines the StoreOrders data type.
 * @author William Wang, Joshua Sze
 */
public class StoreOrders implements Serializable
{
    private ArrayList<Order> orders = new ArrayList<>();

    public StoreOrders()
    {

    }

    /**
     * This method tries to return the position of the Order in with the given phone number in the orders ArrayList.
     * @param phoneNumber A String phoneNumber
     * @return An int
     */
    public int find(String phoneNumber)
    {
        final int NOT_FOUND = -1;
        for (int i = 0; i < orders.size(); i++) { if (orders.get(i).getPhoneNumber().equals(phoneNumber)) return i; }
        return NOT_FOUND;
    }

    /**
     * This method returns the number of orders in the orders ArrayList.
     * @return An int
     */
    public int getNumOrders() { return orders.size(); }

    /**
     * This method returns an order at a given location in the orders ArrayList.
     * @param index An int index
     * @return An Order object
     */
    public Order getOrder(int index) { return orders.get(index); }

    /**
     * This method adds an order to the orders ArrayList.
     * @param order An Order object
     */
    public void addOrder(Order order) { orders.add(order);}

    /**
     * This method removes an Order at a given location in the orders ArrayList.
     * @param index An int index
     */
    public void removeOrder(int index) { orders.remove(index); }
}