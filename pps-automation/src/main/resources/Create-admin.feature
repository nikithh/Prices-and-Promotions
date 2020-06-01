@Create-Admin
Feature: Create-Admin

  Scenario: Create Admin scenario test pass
    Given navigate to retailer login page to create admin
    When user logged in using "admin" as user and password as "admin" to create admin
    Then home page should be displayed to create admin
    When clicked on admin dropdown
    When clicked on create admin
    Then create admin modal is opened
    When username is entered as "Preeeeeti" and password is entered as "123456"
    When clicked on create admin button
    Then admin is created

  Scenario: Create Admin scenario test fail
    Given navigate to retailer login page to create admin
    When user logged in using "admin" as user and password as "admin" to create admin
    Then home page should be displayed to create admin
    When clicked on admin dropdown
    When clicked on create admin
    Then create admin modal is opened
    When username is entered as "Prttti" and password is entered as "123456"
    When clicked on create admin button
    Then admin already exists is shown
