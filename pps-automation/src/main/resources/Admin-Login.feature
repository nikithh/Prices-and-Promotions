@Login-Admin
Feature: Login-Admin

  Scenario: Login scenario test for valid Admin login
    Given navigate to Admin login form
    When login as admin is clicked
    When admin logged in using "Nidhi" as username and "123456" as password
    Then admin app should be displayed

  Scenario: Login scenario test for invalid admin  login
    Given navigate to Admin login form
    When login as admin is clicked
    When admin logged in using "Abc" as username and "12345" as password as invalid
    Then an invalid credentials popup message is shown
