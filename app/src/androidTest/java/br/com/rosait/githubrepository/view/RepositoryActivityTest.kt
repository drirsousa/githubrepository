package br.com.rosait.githubrepository.view

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import br.com.rosait.githubrepository.R
import org.junit.Test

class RepositoryActivityTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun shouldDisplayTitle() {
        launchActivity<RepositoryActivity>().apply {
            val expectedTitle = context.getString(R.string.app_name)

            moveToState(Lifecycle.State.RESUMED)

            Espresso.onView(ViewMatchers.withText(expectedTitle))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }
}