package com.omni.backingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.omni.backingapp.R;
import com.omni.backingapp.RecipeClickListener;
import com.omni.backingapp.fragment.HomeFragment;
import com.omni.backingapp.model.Ingredient;
import com.omni.backingapp.model.RecipeResponse;
import com.omni.backingapp.model.Step;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null) {

            HomeFragment fragment = new HomeFragment();
            fragment.setRecipeListener(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment, "home").commit();
        }
    }

    @Override
    public void onRecipeClickListener(RecipeResponse recipe) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putParcelableArrayListExtra("current ingredient",(ArrayList<Ingredient>) recipe.getIngredients());
        intent.putParcelableArrayListExtra("current Steps",(ArrayList<Step>) recipe.getSteps());

        startActivity(intent);
    }
}
