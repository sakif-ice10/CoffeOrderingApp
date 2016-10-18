/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */
package com.example.android.justjava;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.justjava.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // Get the customer name
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString();

        // check if the whippeddcream topping is checked
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_chekcbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        // check if the chocolate topping is checked
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_chekcbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        //calculate the total price using calculatePrice() method
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // display the order summary
        String orderSummary = createOrderSummary(price, hasWhippedCream,hasChocolate, name);
        //displayMessage(orderSummary);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param //number of whippedcream and chocolate toppings
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {

        // base price per coffe is $5
        int basePricePerCoffe = 5;

        //add $1 to base price if customer wants whippedcream toppings
        if(addWhippedCream){
            basePricePerCoffe = basePricePerCoffe + 1;
        }

        //add $2 to base price if customer wants chocolate toppings
        if(addChocolate){
            basePricePerCoffe = basePricePerCoffe + 2;
        }

        //calculate total price
        return quantity * basePricePerCoffe;
    }

    /**
     * create order summary.
     *
     * @param //price of the order, toppings status, name of the customer
     */
    private String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate, String name) {

        String summary = getString(R.string.name) + name ;
               summary += getString(R.string.add_whippedCream) + addWhippedCream;
               summary += getString(R.string.add_chocolate) + addChocolate;
               summary += getString(R.string.Quantity) +quantity;
               summary += getString(R.string.total) + price ;
               summary += getString(R.string.thank_you);
        return summary;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(MainActivity.this, "You can't order more than 100 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity +1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        
        if (quantity == 1){
            Toast.makeText(MainActivity.this, "You can't order less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity -1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given text on the screen.
     * @param  message to be displayed
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}