package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    @Override
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
        TextView textViewQuantity = (TextView) findViewById(R.id.textViewQuantity);
        TextView editTextPrice = (TextView) findViewById(R.id.editTextPrice);

        textViewQuantity.setText("200");
    }
}
