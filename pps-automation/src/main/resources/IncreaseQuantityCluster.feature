@IncreaseQuantityCluster
Feature: Increase Product Quantity at Cluster

  Scenario Outline: Increase Product Quantity at Cluster
    Given navigate to retailer login page for updating quantity in Cluster
    When user logged in using "admin" as user and password as "admin" for updating quantity at cluster
    Then home page should be displayed for updating quantity cluster
    When Quantity selected from the sub-nav bar
    When Increase Quantity Cluster is selected
    Then Increase Quantity Cluster Assignment Page is displayed
    When "<productName>" is selected, zone is selected, cluster is selected
    When clicked go button
    Then assign cluster quantity is displayed
    When "<quantity>" is entered as text
    When clicked  assign button
    Then cluster quantity model is displayed

    Examples: 
      | productName | quantity |
      | cerelac     |        2 |
