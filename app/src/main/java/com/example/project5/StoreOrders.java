package com.example.project5;

//import javafx.stage.FileChooser;
//import javafx.stage.Stage;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class defines the StoreOrders data type.
 * @author William Wang, Joshua Sze
 */
public class StoreOrders implements Parcelable
{
    private ArrayList<Order> orders = new ArrayList<>();

    private static final int NOT_FOUND = -1;
    private DecimalFormat d = new DecimalFormat("###,##0.00");

    public StoreOrders()
    {

    }

    protected StoreOrders(Parcel in)
    {
        orders = in.createTypedArrayList(Order.CREATOR);
    }

    public static final Creator<StoreOrders> CREATOR = new Creator<StoreOrders>()
    {
        @Override
        public StoreOrders createFromParcel(Parcel in) { return new StoreOrders(in); }

        @Override
        public StoreOrders[] newArray(int size) { return new StoreOrders[size]; }
    };

    /**
     * This method tries to return the position of the Order in with the given phone number in the orders ArrayList.
     * @param phoneNumber A String phoneNumber
     * @return An int
     */
    public int find(String phoneNumber)
    {
        for (int i = 0; i < orders.size(); i++) { if (orders.get(i).getPhoneNumber().equals(phoneNumber)) return i; }
        return NOT_FOUND;
    }

    /**
     * This method returns the number of orders in the orders ArrayList.
     * @return An int
     */
    public int getNumOrders() { return orders.size(); }

    /**
     * This method returns an order at a given location in the orders ArrayList.
     * @param index An int index
     * @return An Order object
     */
    public Order getOrder(int index) { return orders.get(index); }

    /**
     * This method adds an order to the orders ArrayList.
     * @param order An Order object
     */
    public void addOrder(Order order) { orders.add(order);}

    /**
     * This method removes an Order at a given location in the orders ArrayList.
     * @param index An int index
     */
    public void removeOrder(int index) { orders.remove(index); }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeTypedList(orders);
    }

//    /**
//     * This method exports the information of all orders in the orders ArrayList.
//     * @throws IOException An IOException object
//     */
//    public void export() throws IOException
//    {
//        FileChooser chooser = new FileChooser();
//        chooser.setTitle("Open Target File for the Export");
//        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
//                new FileChooser.ExtensionFilter("All Files", "*.*"));
//        Stage stage = new Stage();
//        File targetFile = chooser.showSaveDialog(stage);
//        PrintWriter pw = new PrintWriter(targetFile);
//        pw.println("RU Pizzeria Store Orders");
//        pw.println();
//        for (int i = 0; i < orders.size(); i++)
//        {
//            Order orderReference = orders.get(i);
//            pw.println("Order for: " + orderReference.getPhoneNumber());
//            for (int j = 0; j < orderReference.getNumPizzas(); j++)
//            {
//                Pizza pizzaReference = orderReference.getPizza(j);
//                pw.println(pizzaReference.toString());
//            }
//            pw.println("Subtotal: $" + d.format(orderReference.getSubtotal()));
//            pw.println("Sales Tax: $" + d.format(orderReference.getSalesTax()));
//            if (i < orders.size() - 1)
//            {
//                pw.println("Order Total: $" + d.format(orderReference.getOrderTotal()));
//                pw.println();
//            }
//            else pw.print("Order Total: $" + d.format(orderReference.getOrderTotal()));
//        }
//        pw.close();
//    }
}