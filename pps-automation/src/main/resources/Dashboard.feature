@Dashboard
Feature: Dashboard functionality

  Scenario: Create zone in dashboard
    Given navigate to retailer login page for dashboard
    When user logged in using "admin" as user and password as "admin" for dashboard
    Then home page should be displayed for dashboard
    When dashboard is selected
    Then dashboard modal is displayed
    When clicked on plus button for adding zone
    Then it gets routed to add zone page

  Scenario: Create zone in dashboard
    Given navigate to retailer login page for dashboard
    When user logged in using "admin" as user and password as "admin" for dashboard
    Then home page should be displayed for dashboard
    When dashboard is selected
    Then dashboard modal is displayed
    When clicked on plus button for adding cluster
    Then it gets routed to add cluster page

  Scenario: Create zone in dashboard
    Given navigate to retailer login page for dashboard
    When user logged in using "admin" as user and password as "admin" for dashboard
    Then home page should be displayed for dashboard
    When dashboard is selected
    Then dashboard modal is displayed
    When clicked on plus button for adding product
    Then it gets routed to add product page
