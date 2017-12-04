package com.omni.backingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.omni.backingapp.activity.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Omni on 02/12/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    @Rule
   public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;
    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {


        mIdlingResource = MainActivity.getIdlingResource();

        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);

    }


    @Before
    public void init(){
        mainActivityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }



    @Test
    public void testRecyclerView(){

        onView(withId(R.id.recipes_recycler_view)).perform(scrollToPosition(1));
        onView(withId(R.id.recipes_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }



}
