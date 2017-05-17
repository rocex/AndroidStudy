package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class CourtCounter extends AppCompatActivity
{
    int iACounter = 0;
    int iBCounter = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    
        setTitle("Court Counter");
        setContentView(R.layout.activity_court_counter);
    }
    
    public void resetCounter(View view)
    {
        iACounter = 0;
        iBCounter = 0;
        
        displayACounter(iACounter);
        displayBCounter(iBCounter);
    }
    
    private void displayCounter(int iViewId, int iCounter)
    {
        TextView textViewQuantity = (TextView) findViewById(iViewId);
        
        textViewQuantity.setText(String.valueOf(iCounter));
    }
    
    private void displayBCounter(int iCounter)
    {
        displayCounter(R.id.textBCounter, iCounter);
    }
    
    private void displayACounter(int iCounter)
    {
        displayCounter(R.id.textACounter, iCounter);
    }
    
    public void onAddA1(View view)
    {
        iACounter = iACounter + 1;
        
        displayACounter(iACounter);
    }
    
    public void onAddA2(View view)
    {
        iACounter = iACounter + 2;
        
        displayACounter(iACounter);
    }
    
    public void onAddA3(View view)
    {
        iACounter = iACounter + 3;
        
        displayACounter(iACounter);
    }
    
    public void onAddB1(View view)
    {
        iBCounter = iBCounter + 1;
    
        displayBCounter(iBCounter);
    }
    
    public void onAddB2(View view)
    {
        iBCounter = iBCounter + 2;
    
        displayBCounter(iBCounter);
    }
    
    public void onAddB3(View view)
    {
        iBCounter = iBCounter + 3;
    
        displayBCounter(iBCounter);
    }
}
