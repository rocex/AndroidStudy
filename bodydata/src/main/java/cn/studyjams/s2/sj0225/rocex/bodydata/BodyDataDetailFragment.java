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
    private DummyContent.DummyItem mItem;
    
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BodyDataDetailFragment()
    {
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        if(getArguments().containsKey(ARG_ITEM_ID))
        {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
    
            Activity activity = getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if(appBarLayout != null && mItem.date != null)
            {
                appBarLayout.setTitle(mItem.dateString);
            }
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.bodydata_detail, container, false);
        
        // Show the dummy content as text in a TextView.
        if(mItem != null)
        {
            ((TextView) rootView.findViewById(R.id.editTextWeight)).setText(String.valueOf(mItem.weight));
            ((TextView) rootView.findViewById(R.id.editTextStature)).setText(String.valueOf(mItem.stature));
            ((TextView) rootView.findViewById(R.id.editTextBMI)).setText(String.format("%.2f", mItem.bmi));
            ((TextView) rootView.findViewById(R.id.textViewDate)).setText(mItem.dateString);
        }
    
        rootView.findViewById(R.id.editTextWeight).setEnabled(false);
        rootView.findViewById(R.id.editTextStature).setEnabled(false);
        rootView.findViewById(R.id.editTextBMI).setEnabled(false);
        rootView.findViewById(R.id.textViewDate).setEnabled(false);
        
        return rootView;
    }
}
