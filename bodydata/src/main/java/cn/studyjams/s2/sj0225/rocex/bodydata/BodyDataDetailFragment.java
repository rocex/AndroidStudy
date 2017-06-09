package cn.studyjams.s2.sj0225.rocex.bodydata;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.studyjams.s2.sj0225.rocex.bodydata.model.BodyData;
import cn.studyjams.s2.sj0225.rocex.bodydata.model.BodyDataContent;
import cn.studyjams.s2.sj0225.rocex.bodydata.model.BodyDataDBHelper;

/**
 * A fragment representing a single BodyData detail screen.
 * This fragment is either contained in a {@link BodyDataListActivity}
 * in two-pane mode (on tablets) or a {@link BodyDataDetailActivity}
 * on handsets.
 */
public class BodyDataDetailFragment extends Fragment
{
    // The fragment argument representing the item _ID that this fragment represents.
    public static final String BODY_DATA_ID = "body_data_id";
    
    private BodyDataDBHelper bodyDataDBHelper = null;
    
    // The model content this fragment is presenting.
    private BodyData bodyData;
    private View rootView;
    
    private TextWatcher textWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        }
        
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }
        
        @Override
        public void afterTextChanged(Editable s)
        {
            Button btnCalculate = (Button) rootView.findViewById(R.id.btnCalculate);
            btnCalculate.setEnabled(true);
        }
    };
    
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
        TextView editTextWeight = (TextView) rootView.findViewById(R.id.editTextWeight);
        String strWeight = editTextWeight.getText().toString();
        
        if(strWeight == null || strWeight.trim().length() == 0)
        {
            editTextWeight.requestFocus();
            editTextWeight.setError(getString(R.string.weight_hint));//"请输入体重！"
            
            return;
        }
        
        bodyData.weight = Double.valueOf(strWeight);
        
        TextView editTextHeight = (TextView) rootView.findViewById(R.id.editTextHeight);
        String strHeight = editTextHeight.getText().toString();
        
        if(strHeight == null || strHeight.trim().length() == 0)
        {
            editTextHeight.requestFocus();
            editTextHeight.setError(getString(R.string.height_hint));//"请输入身高！"
            
            return;
        }
        
        bodyData.height = Double.valueOf(strHeight);
        
        bodyData.calculate();
        
        setValue(bodyData);
    
        setEditable(false);
        
        if(bodyData.getId() == null)
        {
            bodyDataDBHelper.insert(bodyData);
        }
        else
        {
            bodyDataDBHelper.update(bodyData);
        }
    }
    
    public void setValue(BodyData bodyData)
    {
        if(bodyData == null)
        {
            ((TextView) rootView.findViewById(R.id.textViewId)).setText("");
            ((TextView) rootView.findViewById(R.id.editTextWeight)).setText("");
            ((TextView) rootView.findViewById(R.id.editTextHeight)).setText("");
            ((TextView) rootView.findViewById(R.id.editTextBMI)).setText("");
            ((TextView) rootView.findViewById(R.id.textViewCreateTime)).setText("");
            
            return;
        }
    
        ((TextView) rootView.findViewById(R.id.textViewId)).setText(String.valueOf(bodyData.getId()));
        ((TextView) rootView.findViewById(R.id.editTextWeight)).setText(String.valueOf(bodyData.weight));
        ((TextView) rootView.findViewById(R.id.editTextHeight)).setText(String.valueOf(bodyData.height));
        ((TextView) rootView.findViewById(R.id.editTextBMI)).setText(String.format("%.2f", bodyData.bmi));
        ((TextView) rootView.findViewById(R.id.textViewCreateTime)).setText(bodyData.dateString);
    }
    
    public void setEditable(boolean blEditable)
    {
        rootView.findViewById(R.id.editTextWeight).setEnabled(blEditable);
        rootView.findViewById(R.id.editTextHeight).setEnabled(blEditable);
        
        rootView.findViewById(R.id.btnCalculate).setEnabled(blEditable);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    
        bodyDataDBHelper = new BodyDataDBHelper(getContext());
    
        if(getArguments().containsKey(BODY_DATA_ID) && getArguments().getLong(BODY_DATA_ID, -1) != -1)
        {
            bodyData = BodyDataContent.ITEM_MAP.get(getArguments().getString(BODY_DATA_ID));
    
            bodyData = bodyDataDBHelper.queryById(getArguments().getLong(BODY_DATA_ID));
            
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
            if(appBarLayout != null)
            {
                //appBarLayout.setTitle(bodyData.dateString);
            }
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.bodydata_detail, container, false);
        
        ((TextView) rootView.findViewById(R.id.editTextWeight)).addTextChangedListener(textWatcher);
        ((TextView) rootView.findViewById(R.id.editTextHeight)).addTextChangedListener(textWatcher);
        
        if(bodyData != null)
        {
            setValue(bodyData);
        }
        else
        {
            bodyData = new BodyData();
            
            setEditable(true);
        }
        
        return rootView;
    }
}

