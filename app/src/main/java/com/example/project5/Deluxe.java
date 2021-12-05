package com.example.project5;

import java.util.ArrayList;

/**
 * This class defines the Deluxe data type.
 * @author William Wang, Joshua Sze
 */
public class Deluxe extends Pizza
{
    private final int NO_ADDITIONAL_COST_DELUXE_LIMIT = 5;

    /**
     * This constructor creates a new Deluxe object.
     */
    public Deluxe() {}

    /**
     * This constructor creates a new Deluxe object.
     * @param size A Size enum
     * @param toppings An ArrayList of Topping objects
     */
    public Deluxe(Size size, ArrayList<Topping> toppings)
    {
        this.size = size;
        this.toppings = toppings;
    }

    /**
     * This method calculates the cost of the Deluxe pizza.
     * @return amount charged for the Deluxe pizza.
     */
    public double price()
    {
        double baseCost;
        if (this.size.equals(Size.Small)) baseCost = 12.99;
        else if (this.size.equals(Size.Medium)) baseCost = 14.99;
        else baseCost = 16.99;

        int numToppings = toppings.size();
        double cost;
        if (numToppings <= NO_ADDITIONAL_COST_DELUXE_LIMIT) cost = baseCost;
        else cost = baseCost + (numToppings - NO_ADDITIONAL_COST_DELUXE_LIMIT) * ADDITIONAL_TOPPING_RATE;
        return cost;
    }

    /**
     * This method returns the details of the Deluxe pizza in a String.
     * @return details of the Deluxe pizza in a String
     */
    @Override
    public String toString() { return "Deluxe (" + super.toString(); }
}