package cn.studyjams.s2.sj0225.rocex.bodydata;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.studyjams.s2.sj0225.rocex.bodydata.dummy.DummyContent;

/**
 * A fragment representing a single BodyData detail screen.
 * This fragment is either contained in a {@link BodyDataListActivity}
 * in two-pane mode (on tablets) or a {@link BodyDataDetailActivity}
 * on handsets.
 */
public class BodyDataDetailFragment extends Fragment
{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    
    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem dummyItem;
    
    private View rootView;
    
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BodyDataDetailFragment()
    {
        super();
    }
    
    public void onCalculateBMI(View view)
    {
    }
    
    public void setValue(DummyContent.DummyItem dummyItem)
    {
        if(dummyItem == null)
        {
            ((TextView) rootView.findViewById(R.id.editTextWeight)).setText("");
            ((TextView) rootView.findViewById(R.id.editTextHeight)).setText("");
            ((TextView) rootView.findViewById(R.id.editTextBMI)).setText("");
            ((TextView) rootView.findViewById(R.id.textViewDate)).setText("");
            
            return;
        }
        
        ((TextView) rootView.findViewById(R.id.editTextWeight)).setText(String.valueOf(dummyItem.weight));
        ((TextView) rootView.findViewById(R.id.editTextHeight)).setText(String.valueOf(dummyItem.height));
        ((TextView) rootView.findViewById(R.id.editTextBMI)).setText(String.format("%.2f", dummyItem.bmi));
        ((TextView) rootView.findViewById(R.id.textViewDate)).setText(dummyItem.dateString);
    }
    
    public void setEditable(boolean blEditable)
    {
        rootView.findViewById(R.id.editTextWeight).setEnabled(blEditable);
        rootView.findViewById(R.id.editTextHeight).setEnabled(blEditable);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    
        if(getArguments().containsKey(ARG_ITEM_ID) && getArguments().getString(ARG_ITEM_ID) != null)
        {
            dummyItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            
            Activity activity = getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if(appBarLayout != null && dummyItem.date != null)
            {
                appBarLayout.setTitle(dummyItem.dateString);
            }
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.bodydata_detail, container, false);
    
        if(dummyItem != null)
        {
            BodyDataDetailActivity activity = (BodyDataDetailActivity) getActivity();
    
            setValue(dummyItem);
        }
        
        return rootView;
    }
}
