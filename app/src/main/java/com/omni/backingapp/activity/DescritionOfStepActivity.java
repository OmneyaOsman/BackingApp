package com.omni.backingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.omni.backingapp.R;
import com.omni.backingapp.fragment.DescritionOfStepFragment;
import com.omni.backingapp.model.Step;

import java.util.ArrayList;

public class DescritionOfStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descrition_of_step);
        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {

            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("currentPosition")) {
                int stepPos = intent.getIntExtra("currentPosition" ,0);
                ArrayList<Step> steps = intent.getParcelableArrayListExtra("steps");
                getSupportFragmentManager().beginTransaction().replace(R.id.desc_container,
                        DescritionOfStepFragment.newInstance(stepPos ,steps ,false)).commit();
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
}
