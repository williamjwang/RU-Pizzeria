package com.example.project5;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * This class defines the Pizza abstract data type.
 * @author William Wang, Joshua Sze
 */
public abstract class Pizza
{
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;

    static final double ADDITIONAL_TOPPING_RATE = 1.49;

    /**
     * This method is an abstract method to be implemented by subclasses of the Pizza class.
     * @return A double representing the cost of the pizza
     */
    public abstract double price();

    /**
     * This method sets the size of the Pizza.
     * @param size A Size enum
     */
    public void setSize(Size size) { this.size = size; }

    /**
     * This method sets the toppings of the Pizza.
     * @param toppings A Topping ArrayList
     */
    public void setToppings(ArrayList<Topping> toppings) { this.toppings = toppings; }

    /**
     * This method returns the details of the Pizza in a String.
     * @return details of the Pizza in a String
     */
    @Override
    public String toString()
    {
        String toppingString = toppings.toString().replace("_", " ");
        return size.toString() + "): " + toppingString + ": $" + price();
    }
}
