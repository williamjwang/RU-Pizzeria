package com.example.project5;

import java.util.ArrayList;

/**
 * This class defines the Hawaiian data type.
 * @author William Wang, Joshua Sze
 */
public class Hawaiian extends Pizza
{
    private final int NO_ADDITIONAL_COST_HAWAIIAN_LIMIT = 2;

    /**
     * This constructor creates a new Hawaiian object.
     */
    public Hawaiian() {}

    /**
     * This constructor creates a new Hawaiian object.
     * @param size A Size enum
     * @param toppings An ArrayList of Topping objects
     */
    public Hawaiian(Size size, ArrayList<Topping> toppings)
    {
        this.size = size;
        this.toppings = toppings;
    }

    /**
     * This method calculates the cost of the Hawaiian pizza.
     * @return amount charged for the Hawaiian pizza.
     */
    public double price()
    {
        double baseCost;
        if (this.size.equals(Size.Small)) baseCost = 10.99;
        else if (this.size.equals(Size.Medium)) baseCost = 12.99;
        else baseCost = 14.99;

        int numToppings = toppings.size();
        double cost;
        if (numToppings <= NO_ADDITIONAL_COST_HAWAIIAN_LIMIT) cost = baseCost;
        else cost = baseCost + (numToppings - NO_ADDITIONAL_COST_HAWAIIAN_LIMIT) * ADDITIONAL_TOPPING_RATE;
        return cost;
    }

    /**
     * This method returns the details of the Hawaiian pizza in a String.
     * @return details of the Hawaiian pizza in a String
     */
    @Override
    public String toString() { return "Hawaiian (" + super.toString(); }
}