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

public class CurrentOrderActivity extends AppCompatActivity
{
    private Order order;
    private StoreOrders storeOrders;

    private DecimalFormat d = new DecimalFormat("###,##0.00");
    private static final double SALES_TAX_RATE = 0.06625;
    private static final int NOT_FOUND = -1;

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

    int selectedPizzaIndex;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);
        setTitle("Current Order");

        Intent intent = getIntent();
        order = intent.getParcelableExtra("order");
        storeOrders = intent.getParcelableExtra("storeOrders");

        phoneNumberValue = findViewById(R.id.PhoneNumberValue);
        numPizzasValue= findViewById(R.id.NumPizzasValue);
        pizzasList = findViewById(R.id.PizzasList);
        subtotalTextView = findViewById(R.id.CurrentOrderSubtotalValue);
        salesTaxTextView = findViewById(R.id.CurrentOrderSalesTaxValue);
        orderTotalTextView = findViewById(R.id.CurrentOrderOrderTotalValue);
        removePizzaButton = findViewById(R.id.RemovePizzaButton);
        placeOrderButton = findViewById(R.id.PlaceOrderButton);

        phoneNumberValue.setText(order.getPhoneNumber());
        numPizzasValue.setText("" + order.getNumPizzas());
        pizzaList = new ArrayList<>();
        for (int i = 0; i < order.getNumPizzas(); i++) { pizzaList.add(order.getPizza(i).toString()); }
        pizzaListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pizzaList);
        pizzasList.setAdapter(pizzaListAdapter);
        pizzasList.setOnItemClickListener(pizzaClick);
        calculate();
        Toast.makeText(this, "Number of pizzas in order: " + order.getNumPizzas(), Toast.LENGTH_SHORT).show();
    }

    private AdapterView.OnItemClickListener pizzaClick = new AdapterView.OnItemClickListener ()
    {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            selectedPizzaIndex = position;
        }
    };

    public void removePizza()
    {
        removePizzaButton.setOnClickListener(view ->
        {
            if (pizzaList.isEmpty()) return;
            int index = selectedPizzaIndex;
            if (index > 0)
            {
                order.remove(index);
                pizzaList.remove(index);
                pizzaListAdapter.notifyDataSetChanged();
            }
        });
    }

    public void removePizza(View view)
    {
        removePizza();
    }

    public void placeOrder(View view)
    {
        int numPizzas = order.getNumPizzas();
        if (numPizzas == 0) return;
        if (storeOrders.find(order.getPhoneNumber()) == NOT_FOUND)
        {
            storeOrders.addOrder(order);
            phoneNumberValue.setText("");
            numPizzasValue.setText("");
            subtotalTextView.setText("");
            salesTaxTextView.setText("");
            orderTotalTextView.setText("");
            pizzaList.clear();
            pizzaListAdapter.notifyDataSetChanged();
            order = new Order();
            finish();
        }
    }
}