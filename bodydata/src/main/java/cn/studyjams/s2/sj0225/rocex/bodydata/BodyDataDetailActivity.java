package cn.studyjams.s2.sj0225.rocex.bodydata;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cn.studyjams.s2.sj0225.rocex.bodydata.dummy.DummyContent;

/**
 * An activity representing a single BodyData detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link BodyDataListActivity}.
 */
public class BodyDataDetailActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodydata_detail);
    
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if(savedInstanceState == null)
        {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(BodyDataDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(BodyDataDetailFragment.ARG_ITEM_ID));
            BodyDataDetailFragment fragment = new BodyDataDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.bodydata_detail_container, fragment).commit();
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, BodyDataListActivity.class));
    
            return true;
        }
    
        return super.onOptionsItemSelected(item);
    }
    
    public void onCalculateBMI(View view)
    {
    }
    
    public void onResetBMI(View view)
    {
        setValue(null);
    }
    
    public void setValue(DummyContent.DummyItem dummyItem)
    {
        if(dummyItem == null)
        {
            ((TextView) findViewById(R.id.editTextWeight)).setText("");
            ((TextView) findViewById(R.id.editTextStature)).setText("");
            ((TextView) findViewById(R.id.editTextBMI)).setText("");
            ((TextView) findViewById(R.id.textViewDate)).setText("");
            
            return;
        }
        
        ((TextView) findViewById(R.id.editTextWeight)).setText(String.valueOf(dummyItem.weight));
        ((TextView) findViewById(R.id.editTextStature)).setText(String.valueOf(dummyItem.stature));
        ((TextView) findViewById(R.id.editTextBMI)).setText(String.valueOf(dummyItem.bmi));
        ((TextView) findViewById(R.id.textViewDate)).setText(dummyItem.dateString);
    }
}
