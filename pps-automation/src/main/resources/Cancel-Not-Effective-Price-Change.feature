@Cancel-Not-Effective-Price-Change
Feature: Cancel not effective price change

  Scenario: canceling not effective price change based on correct requirements
    Given navigate to retailer login page for cancel not effective price change
    When user logged in using "admin" as user and password as "admin" for canceling not effective price change
    Then home page should be displayed for canceling not effective price change
    When price dropdown is selected and opens
    Then cancel not effective price change button is clicked on nav bar
    Then cancel not effective price change modal is displayed
    When cancel price change button is clicked
    Then promotion gets deleted and same page is shown here
