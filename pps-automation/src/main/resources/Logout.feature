@Logout
Feature: Logout

  Scenario: Logout scenario test for retailer
    Given navigate to retailer login
    When user logged in using "admin" and password as "admin"
    Then home page is shown
    Then LOGOUT button is clicked
    Then home page should be shown again