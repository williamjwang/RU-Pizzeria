package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private StoreOrders storeOrders = new StoreOrders();
    private Order currentOrder = new Order();

    private static final int NOT_FOUND = -1;
    private final int pepperoniIndicator = 0;
    private final int deluxeIndicator = 1;
    private final int hawaiianIndicator = 2;

    private final int LAUNCH_CUSTOMIZATION = 0;
    private final int LAUNCH_CURRENT_ORDER = 1;
    private final int LAUNCH_STORE_ORDERS = 2;

    private static final int ORDER_PLACED = 0;
    private static final int ORDER_NOT_PLACED = 1;

    EditText phoneNumberValue;
    ImageButton addPepperoniButton;
    ImageButton addDeluxeButton;
    ImageButton addHawaiianButton;
    ImageButton currentOrderButton;
    ImageButton storeOrdersButton;

    /**
     * This method creates a new Order object and sets the currentOrder to it.
     */
    public void newOrder() { currentOrder = new Order(); }

    private boolean setPhoneNumber()
    {
        boolean result = true;
        String phoneNumber = phoneNumberValue.getText().toString();
        if (phoneNumber.equals(""))
        {
            result = false;
            Toast.makeText(this, "Please enter a phone number!", Toast.LENGTH_SHORT).show();
        }
        else if (storeOrders.find(phoneNumber) != NOT_FOUND)
        {
            result = false;
            Toast.makeText(this, "Phone number match found, enter a different phone number.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            currentOrder.setPhoneNumber(phoneNumber);
            phoneNumberValue.setText(phoneNumber);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumberValue = findViewById(R.id.PhoneNumber);
        addPepperoniButton = findViewById(R.id.AddPepperoniImageButton);
        addDeluxeButton = findViewById(R.id.AddDeluxeImageButton);
        addHawaiianButton = findViewById(R.id.AddHawaiianImageButton);
        currentOrderButton = findViewById(R.id.CurrentOrderImageButton);
        storeOrdersButton = findViewById(R.id.StoreOrdersImageButton);

        Toast.makeText(this, "Application started.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        currentOrder = (Order) data.getSerializableExtra("order");
        storeOrders = (StoreOrders) data.getSerializableExtra("storeOrders");

        if (requestCode == LAUNCH_CUSTOMIZATION) phoneNumberValue.setText(currentOrder.getPhoneNumber());
        else if (requestCode == LAUNCH_CURRENT_ORDER)
        {
            if (resultCode == ORDER_PLACED)
            {
                newOrder();
                phoneNumberValue.setText("");
            }
            else if (resultCode == ORDER_NOT_PLACED) phoneNumberValue.setText(currentOrder.getPhoneNumber());
        }
        else if (requestCode == LAUNCH_STORE_ORDERS);
    }


    public void addPepperoniPizza(View view)
    {
        if (!setPhoneNumber()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("pizzaType", pepperoniIndicator);
        bundle.putSerializable("order", currentOrder);
        bundle.putSerializable("storeOrders", storeOrders);
        intent.putExtras(bundle);
        startActivityForResult(intent, LAUNCH_CUSTOMIZATION);
    }

    public void addDeluxePizza(View view)
    {
        if (!setPhoneNumber()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("pizzaType", deluxeIndicator);
        bundle.putSerializable("order", currentOrder);
        bundle.putSerializable("storeOrders", storeOrders);
        intent.putExtras(bundle);
        startActivityForResult(intent, LAUNCH_CUSTOMIZATION);
    }

    public void addHawaiianPizza(View view)
    {
        if (!setPhoneNumber()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("pizzaType", hawaiianIndicator);
        bundle.putSerializable("order", currentOrder);
        bundle.putSerializable("storeOrders", storeOrders);
        intent.putExtras(bundle);
        startActivityForResult(intent, LAUNCH_CUSTOMIZATION);
    }

    public void seeCurrentOrder(View view)
    {
        if (!setPhoneNumber()) return;
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", currentOrder);
        bundle.putSerializable("storeOrders", storeOrders);
        intent.putExtras(bundle);
        startActivityForResult(intent, LAUNCH_CURRENT_ORDER);
    }

    /**
     * This method checks whether there are no store orders.
     * @return A boolean describing if there are no store orders
     */
    private boolean checkNoStoreOrders()
    {
        if (storeOrders.getNumOrders() == 0) return true;
        else return false;
    }

    public void seeStoreOrders(View view)
    {
        if (checkNoStoreOrders())
        {
            String noStoreOrders = "There are no orders!";
            Toast.makeText(this, noStoreOrders, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, StoreOrdersActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", currentOrder);
        bundle.putSerializable("storeOrders", storeOrders);
        intent.putExtras(bundle);
        startActivityForResult(intent, LAUNCH_STORE_ORDERS);
    }
}