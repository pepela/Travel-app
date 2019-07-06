package com.peranidze.travel.signin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.peranidze.travel.R
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SignInActivityLogInTest {

    companion object {
        private const val CORRECT_EMAIL = "you@are.tested"
        private const val INCORRECT_EMAIL = "not email"
        private const val PASSWORD_1 = "password"
    }

    @get:Rule
    val activityRule = ActivityTestRule(SignInActivity::class.java)

    @Test
    fun loginFormIsDisplayed() {
        onView(withId(R.id.login_email_et)).check(matches(isDisplayed()))
        onView(withId(R.id.login_password_et)).check(matches(isDisplayed()))
        onView(withText("Log in")).check(matches(isDisplayed()))
        onView(withText("Create account")).check(matches(isDisplayed()))

        onView(withId(R.id.sign_up_email_et)).check(doesNotExist())
        onView(withId(R.id.sign_up_password_et)).check(doesNotExist())
        onView(withId(R.id.sign_up_repeat_password_et)).check(doesNotExist())
        onView(withText("Sign up")).check(doesNotExist())
    }

    @Test
    fun emptyEmailShowsError() {
        onView(withId(R.id.login_password_et)).perform(typeText(PASSWORD_1))
        onView(withText("Log in")).perform(click())

        onView(withId(R.id.login_email_et)).check(matches(hasErrorText("Please enter email")))
    }

    @Test
    fun invalidEmailShowsError() {
        onView(withId(R.id.login_email_et)).perform(typeText(INCORRECT_EMAIL))
        onView(withId(R.id.login_password_et)).perform(typeText(PASSWORD_1))
        onView(withText("Log in")).perform(click())

        onView(withId(R.id.login_email_et)).check(matches(hasErrorText("Incorrect email")))
        onView(withId(R.id.sign_in_progress)).check(matches(not(isDisplayed())))
    }

    @Test
    fun emptyPasswordShowsError() {
        onView(withId(R.id.login_email_et)).perform(typeText(CORRECT_EMAIL))
        onView(withText("Log in")).perform(click())

        onView(withId(R.id.login_password_et)).check(matches(hasErrorText("Please enter password")))
    }

    @Test
    fun fillLoginShowsProgress() {
        onView(withId(R.id.login_email_et)).perform(typeText(CORRECT_EMAIL))
        onView(withId(R.id.login_password_et)).perform(typeText(PASSWORD_1))
        onView(withText("Log in")).perform(click())

        onView(withId(R.id.sign_in_progress)).check(matches(isDisplayed()))
    }

    @Test
    fun createAccountClickShowsCreateAccount() {
        onView(withText("Create account")).perform(click())

        onView(withId(R.id.sign_up_email_et)).check(matches(isDisplayed()))
        onView(withId(R.id.sign_up_password_et)).check(matches(isDisplayed()))
        onView(withId(R.id.sign_up_repeat_password_et)).check(matches(isDisplayed()))
        onView(withText("Sign up")).check(matches(isDisplayed()))

        onView(withId(R.id.login_email_et)).check(doesNotExist())
        onView(withId(R.id.login_password_et)).check(doesNotExist())
        onView(withText("Log in")).check(doesNotExist())
        onView(withText("Create account")).check(doesNotExist())
    }
}