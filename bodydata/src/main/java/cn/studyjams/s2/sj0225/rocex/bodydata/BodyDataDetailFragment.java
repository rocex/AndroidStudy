package cn.studyjams.s2.sj0225.rocex.bodydata;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.studyjams.s2.sj0225.rocex.bodydata.dummy.BodyDataContent;

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
    public static final String BODY_DATA_ID = "body_data_id";
    
    /**
     * The dummy content this fragment is presenting.
     */
    private BodyDataContent.BodyData bodyData;
    
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
        TextView editTextHeight = (TextView) rootView.findViewById(R.id.editTextHeight);
        String strHeight = editTextHeight.getText().toString();
    
        if(strHeight == null || strHeight.trim().length() == 0)
        {
            editTextHeight.setError("请输入身高！");
        
            return;
        }
    
        bodyData.height = Double.parseDouble(strHeight);
    
        TextView editTextWeight = (TextView) rootView.findViewById(R.id.editTextWeight);
        String strWeight = editTextWeight.getText().toString();
    
        if(strWeight == null || strWeight.trim().length() == 0)
        {
            editTextWeight.setError("请输入体重！");
        
            return;
        }
    
        bodyData.weight = Double.parseDouble(strWeight);
    
        bodyData.calculate();
    
        setValue(bodyData);
    }
    
    public void setValue(BodyDataContent.BodyData bodyData)
    {
        if(bodyData == null)
        {
            ((TextView) rootView.findViewById(R.id.editTextWeight)).setText("");
            ((TextView) rootView.findViewById(R.id.editTextHeight)).setText("");
            ((TextView) rootView.findViewById(R.id.editTextBMI)).setText("");
            ((TextView) rootView.findViewById(R.id.textViewDate)).setText("");
            
            return;
        }
    
        ((TextView) rootView.findViewById(R.id.editTextWeight)).setText(String.valueOf(bodyData.weight));
        ((TextView) rootView.findViewById(R.id.editTextHeight)).setText(String.valueOf(bodyData.height));
        ((TextView) rootView.findViewById(R.id.editTextBMI)).setText(String.format("%.2f", bodyData.bmi));
        ((TextView) rootView.findViewById(R.id.textViewDate)).setText(bodyData.dateString);
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
    
        if(getArguments().containsKey(BODY_DATA_ID) && getArguments().getString(BODY_DATA_ID) != null)
        {
            bodyData = BodyDataContent.ITEM_MAP.get(getArguments().getString(BODY_DATA_ID));
            
            Activity activity = getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if(appBarLayout != null && bodyData.date != null)
            {
                appBarLayout.setTitle(bodyData.dateString);
            }
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.bodydata_detail, container, false);
    
        if(bodyData != null)
        {
            //            BodyDataDetailActivity activity = (BodyDataDetailActivity) getActivity();
    
            setValue(bodyData);
        }
        
        return rootView;
    }
}

