package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void onOrder(View view)
    {
        EditText editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        EditText editTextCount = (EditText) findViewById(R.id.editTextCount);
        TextView textViewQuantity = (TextView) findViewById(R.id.textViewQuantity);

        double dblPrice = Double.parseDouble(editTextPrice.getText().toString());
        double dblCount = Double.parseDouble(editTextCount.getText().toString());

        textViewQuantity.setText(NumberFormat.getCurrencyInstance().format(dblPrice * dblCount));
    }

    public void onAdd(View view)
    {
        EditText editTextCount = (EditText) findViewById(R.id.editTextCount);

        int iCount = Integer.parseInt(editTextCount.getText().toString());

        editTextCount.setText(String.valueOf(iCount + 1));
    }

    public void onReduce(View view)
    {
        EditText editTextCount = (EditText) findViewById(R.id.editTextCount);

        int iCount = Integer.parseInt(editTextCount.getText().toString());

        if (iCount <= 1) return;

        editTextCount.setText(String.valueOf(iCount - 1));
    }
}


