package com.dj.login.presentation.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dj.login.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LoginActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun correctEmailPasswordNavigatesToDashboard() {
        onView(withId(R.id.etEmail)).perform(typeText("daniyaljavaid@gmail.com"))
        onView(withId(R.id.etPassword)).perform(typeText("Test@123"))
        onView(withId(R.id.btnLogin)).perform(click())
    }

    @Test
    fun invalidEmailShowsError() {
        onView(withId(R.id.etEmail)).perform(typeText("daniyalgmail.com"))
        onView(withId(R.id.etPassword)).perform(typeText("Test@123"))
        onView(withId(R.id.btnLogin)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText("Invalid Email")))
    }

    @Test
    fun invalidPasswordShowsError() {
        onView(withId(R.id.etEmail)).perform(typeText("daniyal@gmail.com"))
        onView(withId(R.id.etPassword)).perform(typeText("Test"))
        onView(withId(R.id.btnLogin)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText("Invalid Password")))
    }
}