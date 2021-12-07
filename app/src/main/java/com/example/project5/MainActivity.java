package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * We chose not to define the parent activity name in the manifest file so there
 * would be no need for a back button. We couldn't figure out how to disable the
 * back button with the parent activity name defined.
 */

/**
 * This class defines the MainActivity of the RU Pizzeria application.
 * @author William Wang, Joshua Sze
 */
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

    /**
     * This method sets the phone number being interacted with.
     * @return A boolean describing whether the phone number has been successfully set.
     */
    private boolean setPhoneNumber()
    {
        boolean result = true;
        String phoneNumber = phoneNumberValue.getText().toString();
        if (phoneNumber.equals(""))
        {
            result = false;
            Toast.makeText(this, getString(R.string.enter_phone_number), Toast.LENGTH_SHORT).show();
        }
        else if (storeOrders.find(phoneNumber) != NOT_FOUND)
        {
            result = false;
            Toast.makeText(this, getString(R.string.enter_different_phone_number), Toast.LENGTH_SHORT).show();
        }
        else
        {
            currentOrder.setPhoneNumber(phoneNumber);
            phoneNumberValue.setText(phoneNumber);
        }
        return result;
    }

    /**
     * This method defines the onCreate method performed when this activity is created.
     * @param savedInstanceState A Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.ru_pizzeria_main));

        phoneNumberValue = findViewById(R.id.PhoneNumber);
        addPepperoniButton = findViewById(R.id.AddPepperoniImageButton);
        addDeluxeButton = findViewById(R.id.AddDeluxeImageButton);
        addHawaiianButton = findViewById(R.id.AddHawaiianImageButton);
        currentOrderButton = findViewById(R.id.CurrentOrderImageButton);
        storeOrdersButton = findViewById(R.id.StoreOrdersImageButton);

        Toast.makeText(this, getString(R.string.application_start), Toast.LENGTH_SHORT).show();
    }

    /**
     * This method defines the actions to be performed after the activity is returned to.
     * @param requestCode An int
     * @param resultCode An int
     * @param data An Intent object
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        currentOrder = (Order) data.getSerializableExtra(getString(R.string.order));
        storeOrders = (StoreOrders) data.getSerializableExtra(getString(R.string.storeOrders));
        phoneNumberValue.setText(currentOrder.getPhoneNumber());

        if ((requestCode == LAUNCH_CURRENT_ORDER) && (resultCode == ORDER_PLACED))
        {
            newOrder();
            phoneNumberValue.setText("");
        }
    }

    /**
     * This method calls the PizzaCustomization activity to add customized Pepperoni pizzas.
     * @param view A View object
     */
    public void addPepperoniPizza(View view)
    {
        if (!setPhoneNumber()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.pizzaType), pepperoniIndicator);
        bundle.putSerializable(getString(R.string.order), currentOrder);
        bundle.putSerializable(getString(R.string.storeOrders), storeOrders);
        intent.putExtras(bundle);
        startActivityForResult(intent, LAUNCH_CUSTOMIZATION);
    }

    /**
     * This method calls the PizzaCustomization activity to add customized Deluxe pizzas.
     * @param view A View object
     */
    public void addDeluxePizza(View view)
    {
        if (!setPhoneNumber()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.pizzaType), deluxeIndicator);
        bundle.putSerializable(getString(R.string.order), currentOrder);
        bundle.putSerializable(getString(R.string.storeOrders), storeOrders);
        intent.putExtras(bundle);
        startActivityForResult(intent, LAUNCH_CUSTOMIZATION);
    }

    /**
     * This method calls the PizzaCustomization activity to add customized Hawaiian pizzas.
     * @param view A View object
     */
    public void addHawaiianPizza(View view)
    {
        if (!setPhoneNumber()) return;
        Intent intent = new Intent(this, PizzaCustomizationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.pizzaType), hawaiianIndicator);
        bundle.putSerializable(getString(R.string.order), currentOrder);
        bundle.putSerializable(getString(R.string.storeOrders), storeOrders);
        intent.putExtras(bundle);
        startActivityForResult(intent, LAUNCH_CUSTOMIZATION);
    }

    /**
     * This method calls the CurrentOrder activity to see the details of the current order.
     * @param view A View object
     */
    public void seeCurrentOrder(View view)
    {
        if (!setPhoneNumber()) return;
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.order), currentOrder);
        bundle.putSerializable(getString(R.string.storeOrders), storeOrders);
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

    /**
     * This method calls the StoreOrders activity to see the details of all store orders.
     * @param view A View object
     */
    public void seeStoreOrders(View view)
    {
        if (checkNoStoreOrders())
        {
            Toast.makeText(this, getString(R.string.no_orders), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, StoreOrdersActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.order), currentOrder);
        bundle.putSerializable(getString(R.string.storeOrders), storeOrders);
        intent.putExtras(bundle);
        startActivityForResult(intent, LAUNCH_STORE_ORDERS);
    }
}