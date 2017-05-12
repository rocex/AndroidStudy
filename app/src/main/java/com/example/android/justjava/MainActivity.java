package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void onOrder(View view)
    {
        displayQuantity();
    }

    private void displayQuantity()
    {
        EditText editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        EditText editTextCount = (EditText) findViewById(R.id.editTextCount);
        TextView textViewQuantity = (TextView) findViewById(R.id.textViewQuantity);

        double dblPrice = Double.parseDouble(editTextPrice.getText().toString());
        double dblCount = Double.parseDouble(editTextCount.getText().toString());

        textViewQuantity.setText(String.valueOf(dblPrice * dblCount));
    }
}
