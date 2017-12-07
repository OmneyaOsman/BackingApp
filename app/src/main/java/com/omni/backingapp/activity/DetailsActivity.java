package com.omni.backingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.omni.backingapp.R;
import com.omni.backingapp.StepClickListener;
import com.omni.backingapp.fragment.DescritionOfStepFragment;
import com.omni.backingapp.fragment.DetailFragment;
import com.omni.backingapp.model.Ingredient;
import com.omni.backingapp.model.Step;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements StepClickListener {

    public static boolean mIsTwoPane = false;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("saved" , true);
    }

   private  DetailFragment fragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if(savedInstanceState==null) {
            ActionBar actionBar = this.getSupportActionBar();

            // Set the action bar back button to look like an up button
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            Intent intent = getIntent();
            if (intent.hasExtra("current ingredient") && intent.hasExtra("current Steps")) {
                ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra("current ingredient");
                ArrayList<Step> steps = intent.getParcelableArrayListExtra("current Steps");

                 fragment = DetailFragment.newInstance(ingredients, steps);
                fragment.setListener(this);
                getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,
                        fragment ,"detail").commit();

                //check if TwoPane
                if (null != findViewById(R.id.desc_container)) {
                    mIsTwoPane = true;

                }
            }
        }else {
            fragment = (DetailFragment) getSupportFragmentManager().findFragmentByTag("detail");
            fragment.setListener(this);
            //check if TwoPane
            if (null != findViewById(R.id.desc_container)) {
                mIsTwoPane = true;

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStepClickListener(int currentPosition , ArrayList<Step> steps) {

        if(!mIsTwoPane) {
            Intent intent = new Intent(DetailsActivity.this, DescritionOfStepActivity.class);
            intent.putExtra("currentPosition", currentPosition);
            intent.putParcelableArrayListExtra("steps", steps);

            startActivity(intent);
        }else{
             DescritionOfStepFragment stepFragment =  DescritionOfStepFragment.newInstance(currentPosition, steps, mIsTwoPane);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace( R.id.desc_container ,stepFragment)
                    .commit();
        }
    }
}
