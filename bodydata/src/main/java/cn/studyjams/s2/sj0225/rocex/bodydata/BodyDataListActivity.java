package cn.studyjams.s2.sj0225.rocex.bodydata;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.studyjams.s2.sj0225.rocex.bodydata.model.BodyData;
import cn.studyjams.s2.sj0225.rocex.bodydata.model.BodyDataDBHelper;

/**
 * An activity representing a list of BodyDatas. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BodyDataDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BodyDataListActivity extends AppCompatActivity
{
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
     */
    private boolean mTwoPane;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_bodydata_list);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Context context = fab.getContext();
                Intent intent = new Intent(context, BodyDataDetailActivity.class);
                
                context.startActivity(intent);
            }
        });
        
        View recyclerView = findViewById(R.id.bodydata_list);
    
        assert recyclerView != null;
    
        setupRecyclerView((RecyclerView) recyclerView);
        
        if(findViewById(R.id.bodydata_detail_container) != null)
        {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }
    
    private void setupRecyclerView(@NonNull RecyclerView recyclerView)
    {
        BodyDataDBHelper bodyDataDBHelper = new BodyDataDBHelper(this);
    
        List list = bodyDataDBHelper.query(null, (String[]) null);
    
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(list));//BodyDataContent.ITEMS
    }
    
    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>
    {
        private final List<BodyData> listBodyData;
        
        public SimpleItemRecyclerViewAdapter(List<BodyData> listBodyData)
        {
            this.listBodyData = listBodyData;
        }
        
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bodydata_list_content, parent, false);
            
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position)
        {
            holder.bodyData = listBodyData.get(position);
    
            holder.textViewCreateTime.setText(DateFormat.format("yyyy-MM-dd", holder.bodyData.getCreate_time()).toString());
            holder.textViewWeight.setText(holder.bodyData.weight + "kg");
            holder.textViewHeight.setText(holder.bodyData.height + "cm");
            holder.textViewBMI.setText(String.format("%.2f", holder.bodyData.bmi));
            
            holder.view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(mTwoPane)
                    {
                        Bundle arguments = new Bundle();
                        arguments.putLong(BodyDataDetailFragment.BODY_DATA_ID, holder.bodyData.getId());
                        BodyDataDetailFragment fragment = new BodyDataDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction().replace(R.id.bodydata_detail_container, fragment).commit();
                    }
                    else
                    {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, BodyDataDetailActivity.class);
                        intent.putExtra(BodyDataDetailFragment.BODY_DATA_ID, holder.bodyData.getId());
                        
                        context.startActivity(intent);
                    }
                }
            });
        }
        
        @Override
        public int getItemCount()
        {
            return listBodyData.size();
        }
        
        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public final View view;
            public final TextView textViewId;
            public final TextView textViewCreateTime;
            public final TextView textViewWeight;
            public final TextView textViewHeight;
            public final TextView textViewBMI;
            
            public BodyData bodyData;
            
            public ViewHolder(View view)
            {
                super(view);
                
                this.view = view;
                textViewId = (TextView) view.findViewById(R.id.textViewId);
                textViewCreateTime = (TextView) view.findViewById(R.id.textViewCreateTime);
                textViewWeight = (TextView) view.findViewById(R.id.textViewWeight);
                textViewHeight = (TextView) view.findViewById(R.id.textViewHeight);
                textViewBMI = (TextView) view.findViewById(R.id.textViewBMI);
            }
            
            @Override
            public String toString()
            {
                return super.toString() + " '" + textViewBMI.getText() + "'";
            }
        }
    }
}
