@Login-vendor
Feature: Login-vendor

  Scenario: Login scenario test for valid vendor login
    Given navigate to vendor login form
    When user logged in using "testcompany" as user and "yatheesha" as password
    Then Vendor app should be displayed

  Scenario: Login scenario test for invalid vendor  login
    Given navigate to vendor login form
    When user logged in using "invalidcompany" as user and "12345" as password
    Then invalid popup message  showing in invalid credentials  should be displayed
