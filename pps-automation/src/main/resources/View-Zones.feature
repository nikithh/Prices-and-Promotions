@View-Zones
Feature: View Zones

  Scenario: Login scenario test for view zones
    Given navigate to retailer login page for viewing zone
    When user logged in using "admin" as user and password as "admin" for veing zone
    Then home page should be displayed for viewing zone
    When location dropdown is clicked
    When view zones is selected 
    Then view zones modal opens