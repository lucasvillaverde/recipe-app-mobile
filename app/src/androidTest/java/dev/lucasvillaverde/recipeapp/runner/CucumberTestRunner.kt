package dev.lucasvillaverde.recipeapp.runner

import io.cucumber.android.runner.CucumberAndroidJUnitRunner
import io.cucumber.junit.CucumberOptions

@CucumberOptions(
    glue = ["dev.lucasvillaverde.recipeapp.tests"],
    features = ["features"],
    tags = ["~@wip"]
)
class CucumberTestRunner : CucumberAndroidJUnitRunner()