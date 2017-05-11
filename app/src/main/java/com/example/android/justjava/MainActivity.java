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
        TextView textView = (TextView) findViewById(R.id.textView0);

        textView.setText("200");
    }
}
