@Sell-Cancel
Feature: Sell-Cancel

  Scenario Outline: Sell product test
    Given navigate to retailer login form
    When retailer logged in using "admin" as username and "admin" as password
    Then retailer modal is opened
    When price button is clicked
    When clicked on sell/cancel product button
    Then sell/cancel product modal is opened
    When a baby product is selected as "<productName>" to sell/cancel
    When go button is clicked
    Then it routed to the next page with product description
    When sell product button is clicked
    When selected okay in the alert shown to sell
    Then product is sold

    Examples: 
      | productName |
      | cerelac     |

  Scenario Outline: Cancel product test
    Given navigate to retailer login form
    When retailer logged in using "admin" as username and "admin" as password
    Then retailer modal is opened
    When price button is clicked
    When clicked on sell/cancel product button
    Then sell/cancel product modal is opened
    When a baby product is selected as "<productName>" to sell/cancel
    When go button is clicked
    Then it routed to the next page with product description
    When cancel product button is clicked
    When selected okay in the alert shown to cancel
    Then product is cancelled

    Examples: 
      | productName |
      | cerelac     |
