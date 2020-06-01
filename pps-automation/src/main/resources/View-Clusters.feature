@View-Clusters
Feature: View Clusters

  Scenario: Login scenario test for view cluster
    Given navigate to retailer login page for viewing cluster
    When user logged in using "admin" as user and password as "admin" for viewing cluster
    Then home page should be displayed for viewing cluster
    When location is selected
    When view clusters is selected
    Then view clusters modal opens
    Then user logs out
    Then retailer log in is shown again