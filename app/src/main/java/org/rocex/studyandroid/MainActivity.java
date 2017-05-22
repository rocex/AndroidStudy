package org.rocex.studyandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void jumpToCoffeeOrder(View view)
    {
        Log.d(getClass().getSimpleName(), "enter jumpToCoffeeOrder function.");
    
        Intent intent = new Intent(this, CoffeeOrderActivity.class);
        startActivity(intent);
    }

    public void jumpToCourtCounter(View view)
    {
        Log.d(getClass().getSimpleName(), "enter jumpToCourtCounter function.");
    
        Intent intent = new Intent(this, CourtCounterActivity.class);
        startActivity(intent);
    }
}