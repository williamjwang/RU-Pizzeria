package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class defines the CurrentOrderActivity of the RU Pizzeria application.
 * @author William Wang, Joshua Sze
 */
public class CurrentOrderActivity extends AppCompatActivity
{
    private Order order;
    private StoreOrders storeOrders;

    private DecimalFormat d = new DecimalFormat("###,##0.00");
    private static final double SALES_TAX_RATE = 0.06625;
    private static final int NOT_FOUND = -1;
    private static final int ORDER_PLACED = 0;
    private static final int ORDER_NOT_PLACED = 1;

    TextView phoneNumberValue;
    TextView numPizzasValue;
    TextView subtotalTextView;
    TextView salesTaxTextView;
    TextView orderTotalTextView;
    ListView pizzasList;
    Button removePizzaButton;
    Button placeOrderButton;

    ArrayList<String> pizzaList;
    ArrayAdapter pizzaListAdapter;

    private int selectedPizzaIndex = -1;

    /**
     * This method calculates/updates the subtotal, sales tax, and order total of the current order and displays them.
     */
    private void calculate()
    {
        int numPizzas = order.getNumPizzas();
        numPizzasValue.setText("" + order.getNumPizzas());
        if (numPizzas == 0)
        {
            subtotalTextView.setText("");
            salesTaxTextView.setText("");
            orderTotalTextView.setText("");
        }
        double subtotal = 0;
        for (int i = 0; i < numPizzas; i++) { subtotal += order.getPizza(i).price(); }
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
        setContentView(R.layout.activity_current_order);
        setTitle(getString(R.string.current_order));

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(getString(R.string.order));
        storeOrders = (StoreOrders) intent.getSerializableExtra(getString(R.string.storeOrders));

        phoneNumberValue = findViewById(R.id.PhoneNumberValue);
        numPizzasValue= findViewById(R.id.NumPizzasValue);
        pizzasList = findViewById(R.id.PizzasList);
        subtotalTextView = findViewById(R.id.CurrentOrderSubtotalValue);
        salesTaxTextView = findViewById(R.id.CurrentOrderSalesTaxValue);
        orderTotalTextView = findViewById(R.id.CurrentOrderOrderTotalValue);
        removePizzaButton = findViewById(R.id.RemovePizzaButton);
        placeOrderButton = findViewById(R.id.PlaceOrderButton);

        phoneNumberValue.setText(order.getPhoneNumber());
        pizzaList = new ArrayList<>();
        for (int i = 0; i < order.getNumPizzas(); i++) { pizzaList.add(order.getPizza(i).toString()); }
        pizzaListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pizzaList);
        pizzasList.setAdapter(pizzaListAdapter);
        pizzasList.setOnItemClickListener(pizzaClick);
        calculate();
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
        setResult(ORDER_NOT_PLACED, data);
        super.onBackPressed();
    }

    /**
     * This Listener checks for changes in the pizza selected.
     */
    private AdapterView.OnItemClickListener pizzaClick = new AdapterView.OnItemClickListener ()
    {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            selectedPizzaIndex = position;
        }
    };

    /**
     * This method removes the last selected pizza from the order.
     * @param view A View object
     */
    public void removePizza(View view)
    {
        int index = selectedPizzaIndex;
        if (index == -1) return;
        if (index < order.getNumPizzas())
        {
            order.remove(index);
            pizzaList.remove(index);
            pizzaListAdapter.notifyDataSetChanged();
            calculate();
        }
    }

    /**
     * This method places the order into the store orders.
     * @param view A View object
     */
    public void placeOrder(View view)
    {
        int numPizzas = order.getNumPizzas();
        if ((numPizzas == 0) || (storeOrders.find(order.getPhoneNumber()) != NOT_FOUND)) return;
        storeOrders.addOrder(order);
        phoneNumberValue.setText("");
        numPizzasValue.setText("");
        subtotalTextView.setText("");
        salesTaxTextView.setText("");
        orderTotalTextView.setText("");
        pizzaList.clear();
        pizzaListAdapter.notifyDataSetChanged();
        Toast.makeText(this, getString(R.string.order_placed), Toast.LENGTH_SHORT).show();

        Intent data = new Intent();
        data.putExtra(getString(R.string.order), order);
        data.putExtra(getString(R.string.storeOrders), storeOrders);
        setResult(ORDER_PLACED, data);
        super.onBackPressed();
    }
}