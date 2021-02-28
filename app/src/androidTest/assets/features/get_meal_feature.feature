Feature: Get a new meal

  Scenario: Successful get a new meal recipe
    Given I start the application
    When I click on add meal button
    Then I expect to see a new meal recipe in the list