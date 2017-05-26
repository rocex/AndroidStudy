package org.rocex.studyandroid;

import android.content.Intent;
import android.net.Uri;
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
    
    public void jumpToHappyBirthday(View view)
    {
        Log.d(getClass().getSimpleName(), "enter jumpToHappyBirthday function.");
    
        Intent intent = new Intent(this, HappyBirthdayActivity.class);
        startActivity(intent);
    }
    
    public void jumpToShowMap(View view)
    {
        Log.d(getClass().getSimpleName(), "enter jumpToShowMap function.");
        
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:47.6, -122.3"));
        
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }
}