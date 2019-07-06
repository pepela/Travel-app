package com.peranidze.travel.signin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.peranidze.travel.R
import com.peranidze.travel.signin.signup.SignUpFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SignInActivityCreateAccountTest {

    companion object {
        private const val CORRECT_EMAIL = "you@are.tested"
        private const val INCORRECT_EMAIL = "not email"
        private const val PASSWORD_1 = "password"
        private const val PASSWORD_2 = "not_the_same"
        private const val INCORRECT_PASSWORD_1 = "p"
        private const val INCORRECT_PASSWORD_2 = "password_password_password_password_password_password_password"
    }

    @get:Rule
    val activityRule = ActivityTestRule(SignInActivity::class.java)


    @Before
    fun setup() {
        activityRule.activity.supportFragmentManager.beginTransaction()
            .replace(R.id.sign_in_container, SignUpFragment.createInstance()).commit()
    }

    @Test
    fun fillingSignUpShowsProgress() {
        onView(withId(R.id.sign_up_email_et)).perform(typeText(CORRECT_EMAIL))
        onView(withId(R.id.sign_up_password_et)).perform(typeText(PASSWORD_1))
        onView(withId(R.id.sign_up_repeat_password_et)).perform(typeText(PASSWORD_1))
        onView(withText("Sign up")).perform(click())

        onView(withId(R.id.sign_in_progress)).check(matches(isDisplayed()))
    }

    @Test
    fun fillingSignUpEmptyEmailShowsError() {
        onView(withId(R.id.sign_up_password_et)).perform(typeText(PASSWORD_1))
        onView(withId(R.id.sign_up_repeat_password_et)).perform(typeText(PASSWORD_1))
        onView(withText("Sign up")).perform(click())

        onView(withId(R.id.sign_up_email_et)).check(matches(hasErrorText("Please enter email")))
    }

    @Test
    fun fillingSignUpWrongEmailShowsError() {
        onView(withId(R.id.sign_up_email_et)).perform(typeText(INCORRECT_EMAIL))
        onView(withId(R.id.sign_up_password_et)).perform(typeText(PASSWORD_1))
        onView(withId(R.id.sign_up_repeat_password_et)).perform(typeText(PASSWORD_1))
        onView(withText("Sign up")).perform(click())

        onView(withId(R.id.sign_up_email_et)).check(matches(hasErrorText("Incorrect email")))
    }

    @Test
    fun fillingSignUpEmptyPasswordShowsError() {
        onView(withId(R.id.sign_up_email_et)).perform(typeText(CORRECT_EMAIL))
        onView(withId(R.id.sign_up_repeat_password_et)).perform(typeText(PASSWORD_1))
        onView(withText("Sign up")).perform(click())

        onView(withId(R.id.sign_up_password_et)).check(matches(hasErrorText("Please enter password")))
    }

    @Test
    fun fillingSignUpShortPasswordShowsError() {
        onView(withId(R.id.sign_up_email_et)).perform(typeText(CORRECT_EMAIL))
        onView(withId(R.id.sign_up_password_et)).perform(typeText(INCORRECT_PASSWORD_1))
        onView(withText("Sign up")).perform(click())

        onView(withId(R.id.sign_up_password_et)).check(matches(hasErrorText("Incorrect password")))
    }

    @Test
    fun fillingSignUpLongPasswordShowsError() {
        onView(withId(R.id.sign_up_email_et)).perform(typeText(CORRECT_EMAIL))
        onView(withId(R.id.sign_up_password_et)).perform(typeText(INCORRECT_PASSWORD_2))
        onView(withText("Sign up")).perform(click())

        onView(withId(R.id.sign_up_password_et)).check(matches(hasErrorText("Incorrect password")))
    }

    @Test
    fun fillingSignUpEmptyRepeatPasswordShowsError() {
        onView(withId(R.id.sign_up_email_et)).perform(typeText(CORRECT_EMAIL))
        onView(withId(R.id.sign_up_password_et)).perform(typeText(PASSWORD_1))
        onView(withText("Sign up")).perform(click())

        onView(withId(R.id.sign_up_repeat_password_et)).check(matches(hasErrorText("Please repeat password")))
    }

    @Test
    fun fillingSignUpPasswordsDoNotMatchShowsError() {
        onView(withId(R.id.sign_up_email_et)).perform(typeText(CORRECT_EMAIL))
        onView(withId(R.id.sign_up_password_et)).perform(typeText(PASSWORD_1))
        onView(withId(R.id.sign_up_repeat_password_et)).perform(typeText(PASSWORD_2))
        onView(withText("Sign up")).perform(click())

        onView(withId(R.id.sign_up_repeat_password_et)).check(matches(hasErrorText("Password don\'t match")))
    }

}