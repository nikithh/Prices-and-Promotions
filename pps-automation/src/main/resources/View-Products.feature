@View-Products
Feature: View Products

  Scenario: Scenario for view products
    Given navigate to vendor login for view products
    When vendor login button is clicked
    When user logged in using "testcompany" as user and password as "yatheesha" for viewing products
    Then home page should be displayed for vendor to view products
    When view products button is clicked
    Then products modal is opened
