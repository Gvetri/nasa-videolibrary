package com.gvetri.kotlin.videolibrary.app

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashFragmentTest {

    @Test
    fun testSplashFragment() {
        launchFragmentInContainer<SplashFragment>()
        onView(withId(R.id.rocketAnimation)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
