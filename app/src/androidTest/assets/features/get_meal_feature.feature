Feature: Get a new meal

  Scenario: Successful get a new meal recipe
    Given I start the application
    When Recipes are being fetched
    Then I expect to see a new list of recipes