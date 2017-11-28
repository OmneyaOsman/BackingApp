package com.omni.backingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.omni.backingapp.R;
import com.omni.backingapp.fragment.DescritionOfStepFragment;
import com.omni.backingapp.model.Step;

public class DescritionOfStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descrition_of_step);

        if (savedInstanceState == null) {

            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("current Step")) {
                Step step = intent.getParcelableExtra("current Step");
                getSupportFragmentManager().beginTransaction().replace(R.id.desc_container,
                        DescritionOfStepFragment.newInstance(step)).commit();
            }

        }

    }
}
