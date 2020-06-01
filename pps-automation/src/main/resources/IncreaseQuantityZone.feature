@IncreaseQuantityZone
Feature: Increase Product Quantity at Zone

  Scenario Outline: Increase Product Quantity at Zone
    Given navigate to retailer login page for updating quantity in zone
    When user logged in using "admin" as user and password as "admin" for updating quantity at zone
    Then home page should be displayed for updating quantity
    When Quantity is selected from the sub-nav bar
    When Increase Quantity Zone is selected
    Then Increase Quantity Zone Assignment Page is displayed
    When "<productName>" is selected, zone is selected
    When clicked on go button
    Then assign zone quantity is displayed
    When "<quantity>" is entered
    When clicked on assign button
    Then zone quantity model is displayed

    Examples: 
      | productName | quantity |
      | cerelac     |        2 |
