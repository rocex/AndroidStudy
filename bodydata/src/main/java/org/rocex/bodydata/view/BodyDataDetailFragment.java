package org.rocex.bodydata.view;

import android.databinding.DataBindingUtil;
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

import org.rocex.bodydata.R;
import org.rocex.bodydata.databinding.BodydataDetailBinding;
import org.rocex.bodydata.model.BodyData;
import org.rocex.bodydata.model.BodyDataDBHelper;

import java.util.List;

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
    
    private BodyDataDBHelper<BodyData> bodyDataDBHelper = null;
    
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
        
        bodyData.setWeight(Double.valueOf(strWeight));
        
        TextView editTextHeight = (TextView) rootView.findViewById(R.id.editTextHeight);
        String strHeight = editTextHeight.getText().toString();
        
        if(strHeight == null || strHeight.trim().length() == 0)
        {
            editTextHeight.requestFocus();
            editTextHeight.setError(getString(R.string.height_hint));//"请输入身高！"
            
            return;
        }
        
        bodyData.setHeight(Double.valueOf(strHeight));
        
        bodyData.calculate();
        
        setEditable(false);
        
        if(bodyData.getId() == null)
        {
            List<Long> listId = bodyDataDBHelper.insert(new BodyData[]{bodyData});
    
            bodyData = (BodyData) bodyDataDBHelper.query(BodyData.class, listId.get(0));
        }
        else
        {
            bodyDataDBHelper.update(new BodyData[]{bodyData});
    
            bodyData = (BodyData) bodyDataDBHelper.query(BodyData.class, bodyData.getId());
        }
    
        setValue(bodyData);
    }
    
    public void setValue(BodyData bodyData)
    {
        if(true)
        {
            return;
        }
        
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
        ((TextView) rootView.findViewById(R.id.editTextWeight)).setText(String.valueOf(bodyData.getWeight()));
        ((TextView) rootView.findViewById(R.id.editTextHeight)).setText(String.valueOf(bodyData.getHeight()));
        ((TextView) rootView.findViewById(R.id.editTextBMI)).setText(String.format("%.2f", bodyData.getBmi()));
        ((TextView) rootView.findViewById(R.id.textViewCreateTime)).setText(bodyData.getStringDate());
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
        
        bodyDataDBHelper = new BodyDataDBHelper<BodyData>(getContext());
        
        if(getArguments().containsKey(BODY_DATA_ID) && getArguments().getLong(BODY_DATA_ID, -1) != -1)
        {
            bodyData = (BodyData) bodyDataDBHelper.query(BodyData.class, getArguments().getLong(BODY_DATA_ID));
            
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
        //        BodydataDetailBinding binding = DataBindingUtil.setContentView(getActivity(), R.layout.bodydata_detail);
        BodydataDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.bodydata_detail, container, false);
    
        rootView = binding.getRoot();
        //rootView = inflater.inflate(R.layout.bodydata_detail, container, false);
        
        ((TextView) rootView.findViewById(R.id.editTextWeight)).addTextChangedListener(textWatcher);
        ((TextView) rootView.findViewById(R.id.editTextHeight)).addTextChangedListener(textWatcher);
        
        if(bodyData != null)
        {
            // setValue(bodyData);
        }
        else
        {
            bodyData = new BodyData();
            
            setEditable(true);
        }
    
        binding.setBodyData(bodyData);
        
        return rootView;
    }
}

