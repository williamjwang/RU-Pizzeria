package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

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

    private static final int toppingLimit = 7;
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

    private void calculatePepperoniSubtotal()
    {
        if (selectedSize.equals("Small")) cost = 8.99;
        else if (selectedSize.equals("Medium")) cost = 10.99;
        else if (selectedSize.equals("Large")) cost = 12.99;
        if (toppingCount >= NO_ADDITIONAL_COST_PEPPERONI_LIMIT)
        {
            cost += (toppingCount - NO_ADDITIONAL_COST_PEPPERONI_LIMIT) * ADDITIONAL_TOPPING_RATE;
        }
    }

    private void calculateDeluxeSubtotal()
    {
        if (selectedSize.equals("Small")) cost = 12.99;
        else if (selectedSize.equals("Medium")) cost = 14.99;
        else if (selectedSize.equals("Large")) cost = 16.99;
        if (toppingCount >= NO_ADDITIONAL_COST_DELUXE_LIMIT)
        {
            cost += (toppingCount - NO_ADDITIONAL_COST_DELUXE_LIMIT) * ADDITIONAL_TOPPING_RATE;
        }
    }

    private void calculateHawaiianSubtotal()
    {
        if (selectedSize.equals("Small")) cost = 10.99;
        else if (selectedSize.equals("Medium")) cost = 12.99;
        else if (selectedSize.equals("Large")) cost = 14.99;
        if (toppingCount >= NO_ADDITIONAL_COST_HAWAIIAN_LIMIT)
        {
            cost += (toppingCount - NO_ADDITIONAL_COST_HAWAIIAN_LIMIT) * ADDITIONAL_TOPPING_RATE;
        }
    }

    protected void calculateSubtotal()
    {
        if (pizzaTypeIndicator == pepperoniIndicator) calculatePepperoniSubtotal();
        else if (pizzaTypeIndicator == deluxeIndicator) calculateDeluxeSubtotal();
        else if (pizzaTypeIndicator == hawaiianIndicator) calculateHawaiianSubtotal();
    }

    protected void getSelectedSize()
    {
        selectedSize = sizeSpinner.getSelectedItem().toString();
        calculateSubtotal();
        displaySubtotal();
    }

    private void displaySubtotal()
    {
        subtotalValue.setText("");
        calculateSubtotal();
        subtotalValue.setText("$" + d.format(cost));
    }

    private void initializeAvailableToppings()
    {
        availableList.add("Mushrooms");
        availableList.add("Bacon");
        availableList.add("Mozzarella");
        availableList.add("Ham");
        availableList.add("Sausage");
        availableList.add("Olives");
        availableList.add("Green Peppers");
        availableList.add("Jalapenos");
        availableList.add("Chicken");
        availableList.add("Beef");
        Collections.sort(availableList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_customization);
        setTitle("Pizza Customization");

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
        storeOrders = (StoreOrders) intent.getSerializableExtra("storeOrders");
        pizzaTypeIndicator = intent.getIntExtra("pizzaType", 0);
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

    @Override
    public void onBackPressed()
    {
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", order);
        bundle.putSerializable("storeOrders", storeOrders);
        data.putExtras(bundle);
        setResult(Activity.RESULT_OK, data);
        super.onBackPressed();
    }

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
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    };

    private AdapterView.OnItemClickListener availableToppingsClick = new AdapterView.OnItemClickListener ()
    {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            availableListSelectedItem = (String)availableToppings.getItemAtPosition(position);
        }
    };

    private AdapterView.OnItemClickListener addedToppingsClick = new AdapterView.OnItemClickListener ()
    {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            addedListSelectedItem = (String)addedToppings.getItemAtPosition(position);
        }
    };

    public void addTopping(View view)
    {
        String topping = availableListSelectedItem;
        if (toppingCount < toppingLimit && topping != null)
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

    public void removeTopping(View view)
    {
        String topping = addedListSelectedItem;
        if (toppingCount > 0 && topping != null)
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
        if (topping.equals("Mushrooms")) return Topping.Mushrooms;
        else if (topping.equals("Bacon")) return Topping.Bacon;
        else if (topping.equals("Mozzarella")) return Topping.Mozzarella;
        else if (topping.equals("Ham")) return Topping.Ham;
        else if (topping.equals("Sausage")) return Topping.Sausage;
        else if (topping.equals("Olives")) return Topping.Olives;
        else if (topping.equals("Green Peppers")) return Topping.Green_Peppers;
        else if (topping.equals("Jalapenos")) return Topping.Jalapenos;
        else if (topping.equals("Chicken")) return Topping.Chicken;
        else return Topping.Beef;
    }

    public void addToOrder(View view)
    {
        Size size;
        if (selectedSize.equals("Small")) size = Size.Small;
        else if (selectedSize.equals("Medium")) size = Size.Medium;
        else size = Size.Large;

        ArrayList<Topping> toppings = new ArrayList<>();
        for (int i = 0; i < addedList.size(); i++)
        {
            String topping = addedList.get(i);
            toppings.add(toTopping(topping));
        }

        Pizza temp;
        if (pizzaTypeIndicator == pepperoniIndicator) temp = PizzaMaker.createPizza("Pepperoni");
        else if (pizzaTypeIndicator == deluxeIndicator) temp = PizzaMaker.createPizza("Deluxe");
        else temp = PizzaMaker.createPizza("Hawaiian");
        temp.setSize(size);
        temp.setToppings(toppings);
        order.add(temp);
        Toast.makeText(this, "Pizza added to order.\nNumber of pizzas in order: " + order.getNumPizzas(), Toast.LENGTH_SHORT).show();
    }
}