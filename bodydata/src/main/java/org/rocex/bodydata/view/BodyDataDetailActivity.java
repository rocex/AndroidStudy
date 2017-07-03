package org.rocex.bodydata.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.rocex.bodydata.R;

/**
 * An activity representing a single BodyData detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link BodyDataListActivity}.
 */
public class BodyDataDetailActivity extends AppCompatActivity
{
    private BodyDataDetailFragment fragment;
    
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
                if(fragment != null)
                {
                    fragment.setEditable(true);
                }
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
            // Create the detail fragment and add it to the activity using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(BodyDataDetailFragment.BODY_DATA_ID, getIntent().getLongExtra(BodyDataDetailFragment.BODY_DATA_ID, -1));
            fragment = new BodyDataDetailFragment();
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
            // This _ID represents the Home or Up button. In the case of this
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
        if(fragment != null)
        {
            fragment.onCalculateBMI(view);
        }
    }
    
    public void onResetBMI(View view)
    {
        if(fragment != null)
        {
            fragment.setValue(null);
        }
    }
}
