package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class defines the StoreOrdersActivity of the RU Pizzeria application.
 * @author William Wang, Joshua Sze
 */
public class StoreOrdersActivity extends AppCompatActivity
{

    private Order order;
    private StoreOrders storeOrders;
    private String selectedPhoneNumber;

    private DecimalFormat d = new DecimalFormat("###,##0.00");
    private static final double SALES_TAX_RATE = 0.06625;

    Spinner orderNumberSpinner;
    ListView pizzasList;
    TextView subtotalTextView;
    TextView salesTaxTextView;
    TextView orderTotalTextView;
    Button cancelOrderButton;

    ArrayList<String> phoneNumberList;
    ArrayList<String> pizzaList;
    ArrayAdapter<String> phoneNumberAdapter;
    ArrayAdapter pizzasListAdapter;

    /**
     * This method sets/updates the pizzas in the pizzasList ListView.
     * @param position
     */
    public void setPizzasList(int position)
    {
        pizzaList.clear();
        Order temp = storeOrders.getOrder(position);
        for (int i = 0; i < temp.getNumPizzas(); i++) { pizzaList.add(temp.getPizza(i).toString()); }
        pizzasListAdapter.notifyDataSetChanged();
    }

    /**
     * This method calculates/updates the subtotal, sales tax, and order total to match the selected order.
     * @param position An int
     */
    public void calculate(int position)
    {
        subtotalTextView.setText("");
        salesTaxTextView.setText("");
        orderTotalTextView.setText("");

        Order temp = storeOrders.getOrder(position);
        int numPizzas = temp.getNumPizzas();
        double subtotal = 0;
        for (int i = 0; i < numPizzas; i++) { subtotal += temp.getPizza(i).price(); }
        if (subtotal > 0) subtotalTextView.setText("$" + d.format(subtotal));
        double salesTax = subtotal * SALES_TAX_RATE;
        if (salesTax > 0) salesTaxTextView.setText("$" + d.format(salesTax));
        double orderTotal = subtotal + salesTax;
        if (orderTotal > 0) orderTotalTextView.setText("$" + d.format(orderTotal));
    }

    /**
     * This method defines the onCreate method performed when this activity is created.
     * @param savedInstanceState A Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);
        setTitle("Store Orders");

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
        storeOrders = (StoreOrders) intent.getSerializableExtra("storeOrders");

        orderNumberSpinner = findViewById(R.id.OrderNumberSpinner);
        pizzasList = findViewById(R.id.StoreOrdersPizzasList);
        subtotalTextView = findViewById(R.id.StoreOrdersSubtotalValue);
        salesTaxTextView = findViewById(R.id.StoreOrdersSalesTaxValue);
        orderTotalTextView = findViewById(R.id.StoreOrdersOrderTotalValue);
        cancelOrderButton = findViewById(R.id.CancelOrderButton);

        phoneNumberList = new ArrayList<>();
        pizzaList = new ArrayList<>();

        for (int i = 0; i < storeOrders.getNumOrders(); i++) { phoneNumberList.add(storeOrders.getOrder(i).getPhoneNumber()); }
        for (int i = 0; i < storeOrders.getOrder(0).getNumPizzas(); i++) { pizzaList.add(storeOrders.getOrder(0).getPizza(i).toString()); }

        phoneNumberAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, phoneNumberList);
        pizzasListAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, pizzaList);
        orderNumberSpinner.setAdapter(phoneNumberAdapter);
        orderNumberSpinner.setOnItemSelectedListener(phoneNumberClick);
        pizzasList.setAdapter(pizzasListAdapter);

        selectedPhoneNumber = storeOrders.getOrder(0).getPhoneNumber();
        setPizzasList(0);
        calculate(0);
    }

    /**
     * This method defines the behavior of this Activity after the back button is pressed.
     */
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

    /**
     * This Listener checks for changes in the order selected.
     */
    private AdapterView.OnItemSelectedListener phoneNumberClick = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            selectedPhoneNumber = (String)orderNumberSpinner.getItemAtPosition(position);
            setPizzasList(position);
            calculate(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    };

    /**
     * This method removes the displayed order from the storeOrders.
     * @param view A View object
     */
    public void removeOrder(View view)
    {
        int index = storeOrders.find(selectedPhoneNumber);
        storeOrders.removeOrder(index);
        if (storeOrders.getNumOrders() != 0)
        {
            phoneNumberList.remove(selectedPhoneNumber);
            phoneNumberAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, phoneNumberList);
            orderNumberSpinner.setAdapter(phoneNumberAdapter);
            setPizzasList(0);
            calculate(0);
            pizzasListAdapter.notifyDataSetChanged();
            String orderRemoved = "Order '" + selectedPhoneNumber + "' successfully removed.";
            Toast.makeText(this, orderRemoved, Toast.LENGTH_SHORT).show();
        }
        else
        {
            String noOrders = "There are no orders!";
            Toast.makeText(this, noOrders, Toast.LENGTH_SHORT);
            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", order);
            bundle.putSerializable("storeOrders", storeOrders);
            data.putExtras(bundle);
            setResult(Activity.RESULT_OK, data);
            super.onBackPressed();
        }
    }
}