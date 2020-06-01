@Cancel-Effective-Price-Change
Feature: Cancel effective price change

  Scenario: cancelling effective price change based on correct requirements
    Given navigate to retailer login page for cancel effective price change
    When user logged in using "admin" as user and password as "admin" for cancelling effective price change
    Then home page should be displayed for cancelling effective price change
    When price dropdown btn is clicked
    Then cancel effective price change button is clicked on nav bar
    Then cancel effective price change modal is displayed
    When withdraw button is clicked to cancel
    Then promotion gets deleted and same page is shown
