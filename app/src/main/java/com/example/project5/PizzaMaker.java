package com.example.project5;

/**
 * This class defines the PizzaMaker data type.
 * @author William Wang, Joshua Sze
 */
public class PizzaMaker
{
    /**
     * This method returns a Pepperoni, Deluxe, or Hawaiian object
     * @param flavor A String flavor
     * @return A Pepperoni, Deluxe, or Hawaiian object
     */
    public static Pizza createPizza(String flavor)
    {
        if (flavor.equals("Pepperoni")) return new Pepperoni();
        else if (flavor.equals("Deluxe")) return new Deluxe();
        else return new Hawaiian();
    }
}