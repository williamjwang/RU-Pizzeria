package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
            Toast.makeText(this, "Please enter a phone number!", Toast.LENGTH_LONG).show();
        }
        else if (storeOrders.find(phoneNumber) != NOT_FOUND)
        {
            result = false;
            Toast.makeText(this, "Phone number match found. Please enter a different phone number.", Toast.LENGTH_LONG).show();
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

        addPepperoniPizzaListener();
        addDeluxePizzaListener();
        addHawaiianPizzaListener();
        currentOrderListener();
        storeOrdersListener();

        Toast.makeText(this, "created", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_CUSTOMIZATION)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                phoneNumberValue.setText(currentOrder.getPhoneNumber());
                ArrayList<String> pizzasToString = data.getStringArrayListExtra("pizzasToString");
                for (int i = 0; i < pizzasToString.size(); i++)
                {
                    Toast.makeText(this, pizzasToString.get(i), Toast.LENGTH_SHORT).show();
                }

                //regex to rebuild added pizzas into currentOrder
                Toast.makeText(this, "(ON ACTIVITY RESULT) Phone Number: " + currentOrder.getPhoneNumber(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "(ON ACTIVITY RESULT) NUM PIZZAS IN CURRENT ORDER: " + currentOrder.getNumPizzas(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == LAUNCH_CURRENT_ORDER)
        {

        }
    }

    public void addPepperoniPizzaListener()
    {
        addPepperoniButton.setOnClickListener(view ->
        {
            if (!setPhoneNumber()) return;
            Intent intent = new Intent(this, PizzaCustomizationActivity.class);
            intent.putExtra("pizzaType", pepperoniIndicator);
            intent.putExtra("order", currentOrder);
            startActivityForResult(intent, LAUNCH_CUSTOMIZATION);
        });
    }

    public void addDeluxePizzaListener()
    {
        addDeluxeButton.setOnClickListener(view ->
        {
            if (!setPhoneNumber()) return;
            Intent intent = new Intent(this, PizzaCustomizationActivity.class);
            intent.putExtra("pizzaType", deluxeIndicator);
            intent.putExtra("order", currentOrder);
            startActivityForResult(intent, LAUNCH_CUSTOMIZATION);
        });
    }

    public void addHawaiianPizzaListener()
    {
        addHawaiianButton.setOnClickListener(view ->
        {
            if (!setPhoneNumber()) return;
            Intent intent = new Intent(this, PizzaCustomizationActivity.class);
            intent.putExtra("pizzaType", hawaiianIndicator);
            intent.putExtra("order", currentOrder);
            startActivityForResult(intent, LAUNCH_CUSTOMIZATION);
        });
    }

    public void currentOrderListener()
    {
        currentOrderButton.setOnClickListener(view ->
        {
            if (!setPhoneNumber()) return;
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            intent.putExtra("order", currentOrder);
            intent.putExtra("storeOrders", storeOrders);
            startActivityForResult(intent, LAUNCH_CURRENT_ORDER);
        });
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

    public void storeOrdersListener()
    {
        storeOrdersButton.setOnClickListener(view ->
        {
            if (checkNoStoreOrders())
            {
                String noStoreOrders = "There are no orders!";
                Toast.makeText(this, noStoreOrders, Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(this, StoreOrdersActivity.class);
            startActivity(intent);
        });
    }
}