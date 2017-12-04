package com.omni.backingapp;

import com.omni.backingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by Omni on 21/11/2017.
 */

public interface StepClickListener {
    void onStepClickListener(int currentPosition , ArrayList<Step> stepList);
}
