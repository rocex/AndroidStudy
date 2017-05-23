package org.rocex.studyandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.MessageFormat;
import java.text.NumberFormat;

public class CoffeeOrderActivity extends AppCompatActivity
{
    int iCount = 1;
    double dblPrice = 0;
    
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    
        setTitle("Coffee Order");
        setContentView(R.layout.activity_coffee);
    
        displayCount(iCount);
    }
    
    private void displayOrderSummary()
    {
        CheckBox checkboxChocolate = (CheckBox) findViewById(R.id.checkboxChocolate);
        CheckBox checkboxWhippedCream = (CheckBox) findViewById(R.id.checkboxWhippedCream);
    
        EditText editTextPrice = (EditText) findViewById(R.id.editTextPrice);
    
        dblPrice = Double.parseDouble(editTextPrice.getText().toString());
    
        String strSummary = MessageFormat.format("Name:{0}\nAdd Whipped cream: {1}\nAdd Chocolate: {2}\nQuantity: {3}\nTotal: {4}\n\nThank you!"
                , "Lyla", checkboxWhippedCream.isChecked(), checkboxChocolate.isChecked(), iCount, NumberFormat.getCurrencyInstance().format(dblPrice * iCount));
        
        TextView textViewSummary = (TextView) findViewById(R.id.textViewSummary);
        textViewSummary.setText(strSummary);
    }
    
    public void submitOrder(View view)
    {
        Log.d(getClass().getSimpleName(), "enter submitOrder function.");
    
        displayOrderSummary();
    }
    
    public void resetOrder(View view)
    {
        iCount = 1;
        dblPrice = 10;
    
        displayCount(iCount);
        displayPrice(dblPrice);
    
        TextView textViewSummary = (TextView) findViewById(R.id.textViewSummary);
        textViewSummary.setText("");
    }
    
    public void onIncrementCount(View view)
    {
        displayCount(++iCount);
    }
    
    public void onDecrementCount(View view)
    {
        if(iCount <= 1)
        {
            return;
        }
    
        displayCount(--iCount);
    }
    
    private void displayCount(int iNewCount)
    {
        TextView editTextCount = (TextView) findViewById(R.id.editTextCount);
    
        editTextCount.setText(String.valueOf(iNewCount));
    }
    
    private void displayPrice(double dblNewPrice)
    {
        EditText editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        
        editTextPrice.setText(String.valueOf(dblNewPrice));
    }
}