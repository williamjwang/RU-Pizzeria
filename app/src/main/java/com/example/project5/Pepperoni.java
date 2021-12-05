package com.example.project5;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class defines the Pepperoni data type.
 * @author William Wang, Joshua Sze
 */
public class Pepperoni extends Pizza implements Serializable
{
    private final int NO_ADDITIONAL_COST_PEPPERONI_LIMIT = 1;

    /**
     * This constructor creates a new Pepperoni object
     */
    public Pepperoni() { }

    /**
     * This constructor creates a new Pepperoni object.
     * @param size A Size enum
     * @param toppings An ArrayList of Topping objects
     */
    public Pepperoni(Size size, ArrayList<Topping> toppings)
    {
        this.size = size;
        this.toppings = toppings;
    }

    /**
     * This method calculates the cost of the Pepperoni pizza.
     * @return amount charged for the Pepperoni pizza.
     */
    public double price()
    {
        double baseCost;
        if (this.size.equals(Size.Small)) baseCost = 8.99;
        else if (this.size.equals(Size.Medium)) baseCost = 10.99;
        else baseCost = 12.99;

        int numToppings = toppings.size();
        double cost;
        if (numToppings <= NO_ADDITIONAL_COST_PEPPERONI_LIMIT) cost = baseCost;
        else cost = baseCost + (numToppings - NO_ADDITIONAL_COST_PEPPERONI_LIMIT) * ADDITIONAL_TOPPING_RATE;
        return cost;
    }

    /**
     * This method returns the details of the Pepperoni pizza in a String.
     * @return details of the Pepperoni pizza in a String
     */
    @Override
    public String toString() { return "Pepperoni (" + super.toString(); }
}