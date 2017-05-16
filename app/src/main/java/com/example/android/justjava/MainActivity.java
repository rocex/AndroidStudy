package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
{
    int iCount = 1;
    
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        EditText editTextCount = (EditText) findViewById(R.id.editTextCount);
        
        iCount = Integer.parseInt(editTextCount.getText().toString());
        
        EditText editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        editTextPrice.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(editTextPrice.getText().toString())));
    }
    
    public void submitOrder(View view)
    {
        Log.d(getClass().getSimpleName(), "enter submitOrder function.");
        
        EditText editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        TextView textViewQuantity = (TextView) findViewById(R.id.textViewQuantity);
        
        double dblPrice = Double.parseDouble(editTextPrice.getText().toString());
        
        textViewQuantity.setText(NumberFormat.getCurrencyInstance().format(dblPrice * iCount));
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
        EditText editTextCount = (EditText) findViewById(R.id.editTextCount);
        
        editTextCount.setText(String.valueOf(iNewCount));
    }
}