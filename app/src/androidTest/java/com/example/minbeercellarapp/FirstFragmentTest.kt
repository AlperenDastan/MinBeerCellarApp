package com.example.minbeercellarapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirstFragmentTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_loginInputsAndButtonVisible() {
        // Check if email and password inputs and the login button are displayed
        onView(withId(R.id.emailEditText)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.passwordEditText)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.loginButton)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_loginWithEmptyFields_ShowsError() {
        // Attempt to click login with empty fields
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.textViewErrorEmail)).check(matches(withText("Email cannot be empty")))
        onView(withId(R.id.textViewErrorPassword)).check(matches(withText("Password cannot be empty")))
    }

    @Test
    fun test_validLogin() {
        // Type valid credentials and click the login button
        onView(withId(R.id.emailEditText)).perform(typeText("abuumin@live.dk"))
        onView(withId(R.id.passwordEditText)).perform(typeText("123123"))
        onView(withId(R.id.loginButton)).perform(click())

        // Assuming a successful login will navigate away, check for the absence of the login button
        // or presence of a new element on the next screen.
        // onView(withId(R.id.loginButton)).check(doesNotExist())
    }
}