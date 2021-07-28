package dev.lucasvillaverde.recipeapp.tests

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
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
        clickOn(R.id.btnDeleteMeals)
    }

    @When("I click on add meal button")
    fun iClickOnAddMealButton() {
        // Write code here that turns the phrase above into concrete actions    throw new cucumber.api.PendingException();
        clickOn(R.id.btnGetMeal)
    }

    @Then("I expect to see a new meal recipe in the list")
    fun iExpectToSeeANewMealRecipeInTheList() {
        // Write code here that turns the phrase above into concrete actions    throw new cucumber.api.PendingException();
        Thread.sleep(2000)
        assertListItemCount(R.id.`@+id/recipe_recycler_view`, 1)
    }
}