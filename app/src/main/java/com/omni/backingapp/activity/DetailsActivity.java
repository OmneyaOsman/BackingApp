package com.omni.backingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.omni.backingapp.R;
import com.omni.backingapp.StepClickListener;
import com.omni.backingapp.fragment.DetailFragment;
import com.omni.backingapp.model.Ingredient;
import com.omni.backingapp.model.Step;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements StepClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        if(intent.hasExtra("current ingredient")&&intent.hasExtra("current Steps")){
           ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra("current ingredient");
            ArrayList<Step> steps =intent.getParcelableArrayListExtra("current Steps");

            DetailFragment fragment =  DetailFragment.newInstance(ingredients , steps);
            fragment.setListener(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container ,
                   fragment).commit();
        }
    }


    @Override
    public void onStepClickListener(Step step) {
        Intent intent = new Intent(DetailsActivity.this, DescritionOfStepActivity.class);
        intent.putExtra("current Step",step);

        startActivity(intent);
    }
}
