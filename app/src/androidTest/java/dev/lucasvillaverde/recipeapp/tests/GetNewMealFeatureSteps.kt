package dev.lucasvillaverde.recipeapp.tests

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaAssertions.assertAny
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import dev.lucasvillaverde.recipeapp.R
import dev.lucasvillaverde.recipeapp.MainActivity
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetNewMealFeatureSteps {
    @get:Rule
    val activityScenario: ActivityScenario<MainActivity> =
        ActivityScenario.launch(MainActivity::class.java)

    @Given("I start the application")
    fun iStartTheApplication() {
        assertDisplayed(R.id.layoutMain)
        assertDisplayed(R.id.imgEmptyState)
        assertDisplayed(R.id.tvEmptyState)
    }

    @When("Recipes are being fetched")
    fun theRecipesAreBeingFetched() {
        assertAny<SwipeRefreshLayout>(withId(R.id.srlRecipes)) {
            it.isRefreshing
        }
    }

    @Then("I expect to see a new list of recipes")
    fun iExpectToSeeANewListOfRecipes() {
        // Write code here that turns the phrase above into concrete actions    throw new cucumber.api.PendingException();
        Thread.sleep(5000)
        assertRecyclerViewItemCount(R.id.recipe_recycler_view, 8)
    }
}