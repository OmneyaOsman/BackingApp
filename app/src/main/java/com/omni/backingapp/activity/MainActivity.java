package com.omni.backingapp.activity;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.omni.backingapp.widget.IngredientWidgetProvider;

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


        if(homeFragment==null) {
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

    private void saveRecipeId(int recipeId) {
        SharedPreferences sp = getSharedPreferences("recipe", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("recipeId", recipeId);
        editor.commit();
    }

    @Override
    public void onRecipeClickListener(RecipeResponse recipe) {
        saveRecipeId(recipe.getId());
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putParcelableArrayListExtra("current ingredient",(ArrayList<Ingredient>) recipe.getIngredients());
        intent.putParcelableArrayListExtra("current Steps",(ArrayList<Step>) recipe.getSteps());

        startActivity(intent);


        Intent widgetIntent = new Intent(this, IngredientWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), IngredientWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(widgetIntent);
    }
}
