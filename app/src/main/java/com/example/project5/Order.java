package com.example.project5;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class defines the Order data type.
 * @author William Wang, Joshua Sze
 */
public class Order implements Parcelable
{
    private ArrayList<Pizza> pizzas = new ArrayList<>();
    private String phoneNumber;

    private static final double SALES_TAX_RATE = 0.06625;

    /**
     * This constructor creates a new Order object with no phone number.
     */
    public Order() { this(""); }

    /**
     * This constructor creates a new Order object with a given phone number.
     * @param phoneNumber A String phoneNumber
     */
    public Order(String phoneNumber) { this.phoneNumber = phoneNumber; }

    protected Order(Parcel in)
    {
//        pizzas = in.readArrayList(null);
        phoneNumber = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) { return new Order(in); }

        @Override
        public Order[] newArray(int size) { return new Order[size]; }
    };

    /**
     * This method sets/changes the phone number of the Order object.
     * @param phoneNumber A String phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /**
     * This method return the phone number of the Order object.
     * @return A String phoneNumber
     */
    public String getPhoneNumber() { return phoneNumber; }

    /**
     * This method returns the number of Pizzas that are in the Order object.
     * @return An int numPizzas
     */
    public int getNumPizzas() { return pizzas.size();}

    /**
     * This method returns a particular Pizza in the Order object.
     * @param index An int index
     * @return A Pizza pizza
     */
    public Pizza getPizza(int index) { return pizzas.get(index); }

    /**
     * This method adds a Pizza to the Order object.
     * @param pizza A Pizza object
     */
    public void add(Pizza pizza) { pizzas.add(pizza); }

    /**
     * This method removes a Pizza from the Order object.
     * @param index An int which contains the location of a Pizza in the Order object.
     */
    public void remove(int index) { pizzas.remove(index); }

    public ArrayList<Pizza> getPizzas()
    {
        return pizzas;
    }

    /**
     * This method returns the subtotal of the Order object.
     * @return A double subtotal
     */
    public double getSubtotal()
    {
        double subtotal = 0;
        for (int i = 0; i < pizzas.size(); i++)
        {
            subtotal += pizzas.get(i).price();
        }
        return subtotal;
    }

    /**
     * This method returns the sales tax of the Order object.
     * @return A double salesTax
     */
    public double getSalesTax()
    {
        return getSubtotal() * SALES_TAX_RATE;
    }

    /**
     * This method returns the order total of the Order object.
     * @return a double orderTotal
     */
    public double getOrderTotal()
    {
        return getSubtotal() + getSalesTax();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
//        dest.writeList(pizzas);
        dest.writeString(phoneNumber);
    }
}