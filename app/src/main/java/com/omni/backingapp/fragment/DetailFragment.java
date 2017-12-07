package com.omni.backingapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omni.backingapp.R;
import com.omni.backingapp.StepClickListener;
import com.omni.backingapp.adapter.IngredientAdapter;
import com.omni.backingapp.adapter.StepAdapter;
import com.omni.backingapp.model.Ingredient;
import com.omni.backingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class DetailFragment extends Fragment implements StepAdapter.OnShowMoreClickListener{


    @BindView(R.id.ingrediant_list)
    RecyclerView recyclerView;

    @BindView(R.id.steps_list)
    RecyclerView stepsRecyclerView;

    private StepClickListener mListener ;

    public void setListener(StepClickListener mListener) {
        this.mListener = mListener;
    }

    private ArrayList<Ingredient> ingredientArrayList ;
   private ArrayList<Step> stepArrayList ;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(stepArrayList!=null) {
            outState.putParcelableArrayList("stepArrayList",  stepArrayList);

            RecyclerView.LayoutManager layoutManager = stepsRecyclerView.getLayoutManager();
            if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
                int mScrollPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                outState.putInt("mScrollPosition", mScrollPosition);

            }
        }
        if(ingredientArrayList!=null) {
            outState.putParcelableArrayList("ingredientArrayList",  ingredientArrayList);
        }
    }




    public static DetailFragment newInstance (ArrayList<Ingredient> ingredients , ArrayList<Step> steps){
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("current ingredient" , ingredients);
        args.putParcelableArrayList("current Steps" , steps);
        fragment.setArguments(args);
        return fragment;

    }

    View rootView ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null) {
            rootView = inflater.inflate(R.layout.detail_fragment, container, false);
            ButterKnife.bind(this, rootView);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(manager);

            LinearLayoutManager StepManager = new LinearLayoutManager(getActivity());
            stepsRecyclerView.setLayoutManager(StepManager);
        }


        if(savedInstanceState==null) {
            if (getArguments() != null) {
                if (getArguments().containsKey("current ingredient") && getArguments().containsKey("current Steps")) {
                    ingredientArrayList = getArguments().getParcelableArrayList("current ingredient");
                    stepArrayList = getArguments().getParcelableArrayList("current Steps");
                }
            }
        }
        else if (savedInstanceState.containsKey("stepArrayList")) {
            ingredientArrayList = savedInstanceState.getParcelableArrayList("ingredientArrayList");
            stepArrayList = savedInstanceState.getParcelableArrayList("stepArrayList");

            if (stepArrayList != null) {

                int mScrollPosition = savedInstanceState.getInt("mScrollPosition");
                RecyclerView.LayoutManager layoutManager = stepsRecyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int count = layoutManager.getChildCount();
                    if (mScrollPosition != RecyclerView.NO_POSITION && mScrollPosition < count) {
                        layoutManager.scrollToPosition(mScrollPosition);
                    }
                }

            }
        }


        return  rootView ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRecyclerView(ingredientArrayList , stepArrayList);
    }

    private void setRecyclerView( ArrayList<Ingredient> ingredientArrayList, ArrayList<Step> stepArrayList ){
        IngredientAdapter adapter = new IngredientAdapter(getActivity(),ingredientArrayList);
        recyclerView.setAdapter(adapter);

        StepAdapter stepAdapter = new StepAdapter(getActivity(),stepArrayList , DetailFragment.this);
        stepsRecyclerView.setAdapter(stepAdapter);
    }

    @Override
    public void setOnClickListener(int position) {
        mListener.onStepClickListener(position , stepArrayList);
    }
}
