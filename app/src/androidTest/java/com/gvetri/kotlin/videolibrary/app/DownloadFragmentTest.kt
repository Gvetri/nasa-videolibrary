package com.gvetri.kotlin.videolibrary.app

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gvetri.kotlin.videolibrary.download.android.DownloadFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DownloadFragmentTest {
    @Test
    fun testSplashFragment() {
        launchFragmentInContainer<DownloadFragment>()
        onView(ViewMatchers.withId(R.id.hello_world_download))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
