package com.omni.backingapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.omni.backingapp.R;
import com.omni.backingapp.RecipeClickListener;
import com.omni.backingapp.activity.MainActivity;
import com.omni.backingapp.adapter.RecipesAdapter;
import com.omni.backingapp.model.RecipeResponse;
import com.omni.backingapp.rest.ApiClient;
import com.omni.backingapp.rest.ApiService;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 *
 */

public class HomeFragment extends Fragment implements RecipesAdapter.OnItemClick{


    private Unbinder unbinder ;
    private RecipeClickListener mListener ;
    private LinearLayoutManager mLayoutManager ;

    @BindView(R.id.recipes_recycler_view)
    RecyclerView mRecyclerView ;

    private List<RecipeResponse> mList ;


    public void setRecipeListener(RecipeClickListener mClickListener) {
        this.mListener = mClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        setRecipeListener((MainActivity) getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState==null){

            callRecipes();
        }
    }

    private void callRecipes() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<RecipeResponse>> call = apiService.callRecipes();
        call.enqueue(new Callback<List<RecipeResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<RecipeResponse>> call, @NonNull Response<List<RecipeResponse>> response) {


                mList = response.body();
                RecipesAdapter mAdapter = new RecipesAdapter(getActivity(), mList, HomeFragment.this);
                mRecyclerView.setAdapter(mAdapter);
                Toast.makeText(getActivity(), mList.get(0).getName(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: " + mList.get(0).getName());
            }

            @Override
            public void onFailure(@NonNull Call<List<RecipeResponse>> call, @NonNull Throwable t) {

                String errorType;
                String errorDesc;
                if (t instanceof IOException) {
                    errorType = getString(R.string.time_out);
                    errorDesc = String.valueOf(t.getCause());
                } else if (t instanceof IllegalStateException) {
                    errorType = getString(R.string.conversion_time);
                    errorDesc = String.valueOf(t.getCause());
                } else {
                    errorType = getString(R.string.other_error);
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }

                Toast.makeText(getActivity(), errorType, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+errorDesc);
                call.cancel();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null)
            unbinder.unbind();
    }



    @Override
    public void setOnRcipeClickListener(int position) {
        RecipeResponse currentRecipe= mList.get(position);
        mListener.onRecipeClickListener(currentRecipe);
    }
}
