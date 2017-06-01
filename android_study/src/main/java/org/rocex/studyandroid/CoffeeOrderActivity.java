package org.rocex.studyandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.text.NumberFormat;

public class CoffeeOrderActivity extends AppCompatActivity
{
    int iCount = 1;
    double dblPrice = 0;
    
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle("Coffee Order");
        setContentView(R.layout.activity_coffee);
    
        displayCount(iCount);
    }
    
    private void sendEMail(String strSubject, String strMessage, String... strAddresses)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, strAddresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, strSubject);
        
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }
    
    private void displayOrderSummary()
    {
        EditText editTextUserName = (EditText) findViewById(R.id.editTextUserName);
    
        if(editTextUserName.getText().length() == 0)
        {
            Toast.makeText(this, "Input your name, pls!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        CheckBox checkboxChocolate = (CheckBox) findViewById(R.id.checkboxChocolate);
        CheckBox checkboxWhippedCream = (CheckBox) findViewById(R.id.checkboxWhippedCream);
    
        EditText editTextPrice = (EditText) findViewById(R.id.editTextPrice);
    
        dblPrice = Double.parseDouble(editTextPrice.getText().toString());
    
        double dblBasePrice = dblPrice;
    
        if(checkboxWhippedCream.isChecked())
        {
            dblBasePrice += 1;
        }
    
        if(checkboxChocolate.isChecked())
        {
            dblBasePrice += 2;
        }
    
        double dblSumPrice = dblBasePrice * iCount;
    
        String strSummary = MessageFormat.format("Name:{0}\nAdd Whipped cream: {1}\nAdd Chocolate: {2}\nQuantity: {3}\nTotal: {4}\n\nThank you!", editTextUserName.getText(), checkboxWhippedCream.isChecked(), checkboxChocolate.isChecked(), iCount, NumberFormat.getCurrencyInstance().format(dblSumPrice));
        
        TextView textViewSummary = (TextView) findViewById(R.id.textViewSummary);
        textViewSummary.setText(strSummary);
    
        sendEMail(editTextUserName.getText().toString(), strSummary, "");
    }
    
    public void submitOrder(View view)
    {
        Log.d(getClass().getSimpleName(), "enter submitOrder function.");
    
        displayOrderSummary();
    }
    
    public void resetOrder(View view)
    {
        iCount = 1;
        dblPrice = 10;
    
        displayCount(iCount);
        displayPrice(dblPrice);
    
        TextView textViewSummary = (TextView) findViewById(R.id.textViewSummary);
        textViewSummary.setText("");
    }
    
    public void onIncrementCount(View view)
    {
        if(iCount >= 100)
        {
            return;
        }
        
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
        TextView editTextCount = (TextView) findViewById(R.id.editTextCount);
    
        editTextCount.setText(String.valueOf(iNewCount));
    }
    
    private void displayPrice(double dblNewPrice)
    {
        EditText editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        
        editTextPrice.setText(String.valueOf(dblNewPrice));
    }
}
