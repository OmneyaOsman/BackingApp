package com.omni.backingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.omni.backingapp.IdlingResource.SimpleIdlingResource;
import com.omni.backingapp.R;
import com.omni.backingapp.RecipeClickListener;
import com.omni.backingapp.fragment.HomeFragment;
import com.omni.backingapp.model.Ingredient;
import com.omni.backingapp.model.RecipeResponse;
import com.omni.backingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements RecipeClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;
    private HomeFragment homeFragment ;


    private static SimpleIdlingResource mSimpleIdlingResource ;

    private static  String TAG = MainActivity.class.getSimpleName();


    @VisibleForTesting
    @NonNull
    public static SimpleIdlingResource getIdlingResource() {
        if (mSimpleIdlingResource == null)
            mSimpleIdlingResource = new SimpleIdlingResource();
        return mSimpleIdlingResource;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (homeFragment != null)
            outState.putString("tag", "home");
        Log.d(TAG, "onSaveInstanceState: ");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // create and initialize resource here
        if (mSimpleIdlingResource == null) {
            mSimpleIdlingResource = new SimpleIdlingResource();
            mSimpleIdlingResource.setIdleState(false);
        }

        unbinder = ButterKnife.bind(this);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);


        if(savedInstanceState==null) {
            Log.d(TAG, "onCreate: null");

            homeFragment = new HomeFragment();
            homeFragment.setRecipeListener(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container, homeFragment, "home").commit();

        }else{

            homeFragment =(HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder!=null)
            unbinder.unbind();
    }

    @Override
    public void onRecipeClickListener(RecipeResponse recipe) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putParcelableArrayListExtra("current ingredient",(ArrayList<Ingredient>) recipe.getIngredients());
        intent.putParcelableArrayListExtra("current Steps",(ArrayList<Step>) recipe.getSteps());

        startActivity(intent);
    }
}
