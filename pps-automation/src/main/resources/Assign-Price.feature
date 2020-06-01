@Assign-Price
Feature: Assign Price Step

  Scenario Outline: Assign price and cluster
    Given navigate to retailer login page for assigning price
    When user logged in using "admin" as user and password as "admin" for assigning price
    Then home page should be displayed for assigning price
    When price dropdown button is selected
    When assign price to zone/cluster is selected
    Then select a product modal is displayed
    When product name is entered as "<productName>"
    When assign price and cluster button is clicked
    Then assign to cluster modal is displayed
    Then "<cluster>" letter is given, cluster is selected, "<quantity>" is given, "<percentage>" is entered
    When clicked on save button
    Then cluster modal is displayed

    Examples: 
      | cluster | productName | quantity | percentage |
      | C       | cerelac     |        2 |         20 |

  Scenario Outline: Assign price and zone
    Given navigate to retailer login page for assigning price
    When user logged in using "admin" as user and password as "admin" for assigning price
    Then home page should be displayed for assigning price
    When price dropdown button is selected
    When assign price to zone/cluster is selected
    Then select a product modal is displayed
    When product name is entered as "<productName>"
    When assign price and zone button is clicked
    Then assign to zone modal is displayed
    Then zone is selected, "<quantity>" is given, "<percentage>" is entered
    Then cluster is selected, "<clusterquantity>" is given and "<clusterpercentage>" is entered
    When clicked on save
    Then profit percentage modal is displayed

    Examples: 
      | productName | quantity | percentage | clusterquantity | clusterpercentage |
      | cerelac     |        2 |         12 |               2 |                10 |
