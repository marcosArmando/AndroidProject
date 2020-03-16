package com.yucatancorp.androidproject;


import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.yucatancorp.androidproject.Activities.MainActivity;
import com.yucatancorp.androidproject.Activities.PokemonsActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void getActivityIntent() {

        onView(withId(R.id.ETnombre)).perform(typeText("marcos@gmail.com"));
        onView(withId(R.id.ETpassword)).perform(typeText("123456"));
        onView(withId(R.id.btnLogIn)).perform(click());

        intended(hasComponent(PokemonsActivity.class.getName()));

    }

}
