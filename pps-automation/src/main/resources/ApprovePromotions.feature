@Approve-promotions
Feature: Approve promotions Step

  Scenario Outline: Approve promotions accept scenario
    Given navigate to retailer login page for approving promotions
    When user logged in using "admin" as user and password as "admin" for approving promotions
    Then home page should be displayed for approving promotions
    When admin dropdown button is clicked
    When approving promotions is selected
    Then select a product modal is displayed for approving promotions
    When product name is entered as "<productName>" for approving promotions
    When approve promotions button is clicked
    Then approve promotions modal is displayed
    When approve green button is clicked
    Then an alert is opened and when selected okay to approve
    Then promotion is approved

    Examples: 
      | productName |
      | cerelac     |

  Scenario Outline: Approve promotions reject scenario
    Given navigate to retailer login page for approving promotions
    When user logged in using "admin" as user and password as "admin" for approving promotions
    Then home page should be displayed for approving promotions
    When admin dropdown button is clicked
    When approving promotions is selected
    Then select a product modal is displayed for approving promotions
    When product name is entered as "<productName>" for approving promotions
    When approve promotions button is clicked
    Then approve promotions modal is displayed
    When reject red button is clicked
    Then an alert is opened and when selected okay to reject
    Then promotion is rejected

    Examples: 
      | productName |
      | SulaWine    |
