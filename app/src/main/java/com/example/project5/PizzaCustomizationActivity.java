package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class defines the PizzaCustomizationActivity of the RU Pizzeria application.
 * @author William Wang, Joshua Sze
 */
public class PizzaCustomizationActivity extends AppCompatActivity
{
    private Order order;
    private StoreOrders storeOrders;
    private String selectedSize;
    private double cost;

    private final int pepperoniIndicator = 0;
    private final int deluxeIndicator = 1;
    private final int hawaiianIndicator = 2;
    private final int NO_ADDITIONAL_COST_PEPPERONI_LIMIT = 1;
    private final int NO_ADDITIONAL_COST_DELUXE_LIMIT = 5;
    private final int NO_ADDITIONAL_COST_HAWAIIAN_LIMIT = 2;
    private final double ADDITIONAL_TOPPING_RATE = 1.49;
    private DecimalFormat d = new DecimalFormat("###,##0.00");

    private static final int toppingMin = 0;
    private static final int toppingMax = 7;
    private int toppingCount = 0;

    private int pizzaTypeIndicator;
    ImageView pizzaImage;

    ListView availableToppings;
    ListView addedToppings;

    Button addToppingButton;
    Button removeToppingButton;
    Button addPizzaButton;
    Spinner sizeSpinner;
    TextView subtotalValue;

    ArrayList<String> availableList;
    ArrayList<String> addedList;
    ArrayAdapter<String> availableAdapter;
    ArrayAdapter<String> addedAdapter;

    String availableListSelectedItem;
    String addedListSelectedItem;

    /**
     * This method calculates the subtotal of a Pepperoni pizza.
     */
    private void calculatePepperoniSubtotal()
    {
        if (selectedSize.equals(getString(R.string.small))) cost = 8.99;
        else if (selectedSize.equals(getString(R.string.medium))) cost = 10.99;
        else if (selectedSize.equals(getString(R.string.large))) cost = 12.99;
        if (toppingCount >= NO_ADDITIONAL_COST_PEPPERONI_LIMIT)
        {
            cost += (toppingCount - NO_ADDITIONAL_COST_PEPPERONI_LIMIT) * ADDITIONAL_TOPPING_RATE;
        }
    }

    /**
     * This method calculates the subtotal of a Deluxe pizza.
     */
    private void calculateDeluxeSubtotal()
    {
        if (selectedSize.equals(getString(R.string.small))) cost = 12.99;
        else if (selectedSize.equals(getString(R.string.medium))) cost = 14.99;
        else if (selectedSize.equals(getString(R.string.large))) cost = 16.99;
        if (toppingCount >= NO_ADDITIONAL_COST_DELUXE_LIMIT)
        {
            cost += (toppingCount - NO_ADDITIONAL_COST_DELUXE_LIMIT) * ADDITIONAL_TOPPING_RATE;
        }
    }

    /**
     * This method calculates the subtotal of a Hawaiian pizza.
     */
    private void calculateHawaiianSubtotal()
    {
        if (selectedSize.equals(getString(R.string.small))) cost = 10.99;
        else if (selectedSize.equals(getString(R.string.medium))) cost = 12.99;
        else if (selectedSize.equals(getString(R.string.large))) cost = 14.99;
        if (toppingCount >= NO_ADDITIONAL_COST_HAWAIIAN_LIMIT)
        {
            cost += (toppingCount - NO_ADDITIONAL_COST_HAWAIIAN_LIMIT) * ADDITIONAL_TOPPING_RATE;
        }
    }

    /**
     * This method calculates the subtotal of a pizza, depending on the type.
     */
    protected void calculateSubtotal()
    {
        if (pizzaTypeIndicator == pepperoniIndicator) calculatePepperoniSubtotal();
        else if (pizzaTypeIndicator == deluxeIndicator) calculateDeluxeSubtotal();
        else if (pizzaTypeIndicator == hawaiianIndicator) calculateHawaiianSubtotal();
    }

    /**
     * This method gets the size selected.
     */
    protected void getSelectedSize()
    {
        selectedSize = sizeSpinner.getSelectedItem().toString();
        calculateSubtotal();
        displaySubtotal();
    }

    /**
     * This method changes the subtotal displayed.
     */
    private void displaySubtotal()
    {
        subtotalValue.setText("");
        calculateSubtotal();
        subtotalValue.setText("$" + d.format(cost));
    }

    /**
     * This method initializes the availableList of toppings.
     */
    private void initializeAvailableToppings()
    {
        availableList.add(getString(R.string.mushrooms));
        availableList.add(getString(R.string.bacon));
        availableList.add(getString(R.string.mozzarella));
        availableList.add(getString(R.string.ham));
        availableList.add(getString(R.string.sausage));
        availableList.add(getString(R.string.olives));
        availableList.add(getString(R.string.green_peppers));
        availableList.add(getString(R.string.jalapenos));
        availableList.add(getString(R.string.chicken));
        availableList.add(getString(R.string.beef));
        Collections.sort(availableList);
    }

    /**
     * This method defines the onCreate method performed when this activity is created.
     * @param savedInstanceState A Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_customization);
        setTitle(getString(R.string.pizza_customization));

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(getString(R.string.order));
        storeOrders = (StoreOrders) intent.getSerializableExtra(getString(R.string.storeOrders));
        pizzaTypeIndicator = intent.getIntExtra(getString(R.string.pizzaType), 0);
        pizzaImage = findViewById(R.id.PizzaImage);
        if (pizzaTypeIndicator == pepperoniIndicator) pizzaImage.setImageResource(R.drawable.pepperoni_pizza);
        else if (pizzaTypeIndicator == deluxeIndicator) pizzaImage.setImageResource(R.drawable.deluxe_pizza);
        else if (pizzaTypeIndicator == hawaiianIndicator) pizzaImage.setImageResource(R.drawable.hawaiian_pizza);

        availableList = new ArrayList<>();
        initializeAvailableToppings();
        addedList = new ArrayList<>();
        availableAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, availableList);
        addedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addedList);

        sizeSpinner = findViewById(R.id.SizeSpinner);
        availableToppings = findViewById(R.id.AvailableToppingsList);
        addedToppings = findViewById(R.id.AddedToppingsList);
        addToppingButton = findViewById(R.id.AddToppingButton);
        removeToppingButton = findViewById(R.id.RemoveToppingButton);
        subtotalValue = findViewById(R.id.SubtotalValue);
        addPizzaButton = findViewById(R.id.AddPizzaButton);

        availableToppings.setAdapter(availableAdapter);
        addedToppings.setAdapter(addedAdapter);
        sizeSpinner.setOnItemSelectedListener(sizeClick);
        availableToppings.setOnItemClickListener(availableToppingsClick);
        addedToppings.setOnItemClickListener(addedToppingsClick);

        getSelectedSize();
    }

    /**
     * This method defines the behavior of this Activity after the back button is pressed.
     */
    @Override
    public void onBackPressed()
    {
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.order), order);
        bundle.putSerializable(getString(R.string.storeOrders), storeOrders);
        data.putExtras(bundle);
        setResult(Activity.RESULT_OK, data);
        super.onBackPressed();
    }

    /**
     * This Listener checks for changes in the size selected.
     */
    private AdapterView.OnItemSelectedListener sizeClick = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            selectedSize = (String)sizeSpinner.getItemAtPosition(position);
            calculateSubtotal();
            displaySubtotal();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    };

    /**
     * This Listener checks and updates the most recent topping selected in the availableToppings ListView.
     */
    private AdapterView.OnItemClickListener availableToppingsClick = new AdapterView.OnItemClickListener ()
    {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            availableListSelectedItem = (String)availableToppings.getItemAtPosition(position);
        }
    };

    /**
     * This Listener checks and updates the most recent topping selected in the addedToppings ListView.
     */
    private AdapterView.OnItemClickListener addedToppingsClick = new AdapterView.OnItemClickListener ()
    {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            addedListSelectedItem = (String)addedToppings.getItemAtPosition(position);
        }
    };

    /**
     * This method adds the most recent topping selected in the availableToppings ListView to the addedToppings ListView.
     * @param view A View object
     */
    public void addTopping(View view)
    {
        String topping = availableListSelectedItem;
        if (toppingCount < toppingMax && topping != null)
        {
            addedList.add(topping);
            availableList.remove(topping);
            Collections.sort(addedList);
            availableAdapter.notifyDataSetChanged();
            addedAdapter.notifyDataSetChanged();
            toppingCount++;
            availableListSelectedItem = null;
            calculateSubtotal();
            displaySubtotal();
        }
    }

    /**
     * This method adds the most recent topping selected in the addedToppings ListView to the availableToppings ListView.
     * @param view A View object
     */
    public void removeTopping(View view)
    {
        String topping = addedListSelectedItem;
        if (toppingCount > toppingMin && topping != null)
        {
            availableList.add(topping);
            addedList.remove(topping);
            Collections.sort(availableList);
            availableAdapter.notifyDataSetChanged();
            addedAdapter.notifyDataSetChanged();
            toppingCount--;
            addedListSelectedItem = null;
            calculateSubtotal();
            displaySubtotal();
        }
    }

    /**
     * This method returns a Topping enum, depending on the String input.
     * @param topping A String
     * @return A Topping object
     */
    private Topping toTopping(String topping)
    {
        if (topping.equals(getString(R.string.mushrooms))) return Topping.Mushrooms;
        else if (topping.equals(getString(R.string.bacon))) return Topping.Bacon;
        else if (topping.equals(getString(R.string.mozzarella))) return Topping.Mozzarella;
        else if (topping.equals(getString(R.string.ham))) return Topping.Ham;
        else if (topping.equals(getString(R.string.sausage))) return Topping.Sausage;
        else if (topping.equals(getString(R.string.olives))) return Topping.Olives;
        else if (topping.equals(getString(R.string.green_peppers))) return Topping.Green_Peppers;
        else if (topping.equals(getString(R.string.jalapenos))) return Topping.Jalapenos;
        else if (topping.equals(getString(R.string.chicken))) return Topping.Chicken;
        else return Topping.Beef;
    }

    /**
     * This method adds a customized Pizza to the order.
     * @param view A View objects
     */
    public void addToOrder(View view)
    {
        Size size;
        if (selectedSize.equals(getString(R.string.small))) size = Size.Small;
        else if (selectedSize.equals(getString(R.string.medium))) size = Size.Medium;
        else size = Size.Large;

        ArrayList<Topping> toppings = new ArrayList<>();
        for (int i = 0; i < addedList.size(); i++)
        {
            String topping = addedList.get(i);
            toppings.add(toTopping(topping));
        }

        Pizza temp;
        if (pizzaTypeIndicator == pepperoniIndicator) temp = PizzaMaker.createPizza(getString(R.string.pepperoni));
        else if (pizzaTypeIndicator == deluxeIndicator) temp = PizzaMaker.createPizza(getString(R.string.deluxe));
        else temp = PizzaMaker.createPizza(getString(R.string.hawaiian));
        temp.setSize(size);
        temp.setToppings(toppings);
        order.add(temp);
        Toast.makeText(this, getString(R.string.pizza_added), Toast.LENGTH_SHORT).show();
    }
}