package org.rocex.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.justjava.R;

public class CourtCounterActivity extends AppCompatActivity
{
    int iScoreA = 0;
    int iScoreB = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    
        setTitle("Court Counter");
        setContentView(R.layout.activity_court_counter);
    }
    
    public void resetAllScore(View view)
    {
        iScoreA = 0;
        iScoreB = 0;
    
        displayScore(R.id.textACounter, iScoreA);
        displayScore(R.id.textBCounter, iScoreB);
    }
    
    private void displayScore(int iViewId, int iCounter)
    {
        TextView textViewQuantity = (TextView) findViewById(iViewId);
        
        textViewQuantity.setText(String.valueOf(iCounter));
    }
    
    public void onAddA1(View view)
    {
        iScoreA = iScoreA + 1;
    
        displayScore(R.id.textACounter, iScoreA);
    }
    
    public void onAddA2(View view)
    {
        iScoreA = iScoreA + 2;
    
        displayScore(R.id.textACounter, iScoreA);
    }
    
    public void onAddA3(View view)
    {
        iScoreA = iScoreA + 3;
    
        displayScore(R.id.textACounter, iScoreA);
    }
    
    public void onAddB1(View view)
    {
        iScoreB = iScoreB + 1;
    
        displayScore(R.id.textBCounter, iScoreB);
    }
    
    public void onAddB2(View view)
    {
        iScoreB = iScoreB + 2;
    
        displayScore(R.id.textBCounter, iScoreB);
    }
    
    public void onAddB3(View view)
    {
        iScoreB = iScoreB + 3;
    
        displayScore(R.id.textBCounter, iScoreB);
    }
}
