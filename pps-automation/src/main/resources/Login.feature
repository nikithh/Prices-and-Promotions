@Login
Feature: Login

  Scenario: Login scenario test for retailer valid login
    Given navigate to retailer login page
    When user logged in using "admin" as user and password as "admin"
    Then home page should be displayed

  Scenario: Login scenario test for retailer invalid login
    Given navigate to retailer login page
    When user logged in using "admin" as user and password as "****"
