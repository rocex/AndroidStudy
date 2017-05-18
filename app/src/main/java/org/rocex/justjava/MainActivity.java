package org.rocex.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.android.justjava.R;

public class MainActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
    }
    
    public void jumpToCoffee(View view)
    {
        Log.d(getClass().getSimpleName(), "enter jumpToCoffee function.");
    
        Intent intent = new Intent(getApplicationContext(), CoffeeActivity.class);
        startActivity(intent);
    }
    
    public void jumpToCourtCounter(View view)
    {
        Log.d(getClass().getSimpleName(), "enter jumpToCourtCounter function.");
    
        Intent intent = new Intent(getApplicationContext(), CourtCounterActivity.class);
        startActivity(intent);
    }
}