@Create-Zone
Feature: Create Zone Step

  Scenario Outline: Create zone with incorrect requirements
    Given navigate to retailer login page for zone
    When user logged in using "admin" as user and password as "admin" for zone
    Then home page should be displayed for zone
    When location button is clicked
    When create zone is selected
    Then create zone modal is displayed
    When zone value is entered as "<zoneName>" and price per unit is entered as "<pricePerUnit>"
    When save button is clicked
    Then zone creation failed should be displayed

    Examples: 
      | zoneName | pricePerUnit |
      | USA      |           10 |

  Scenario Outline: Create zone with correct requirements
    Given navigate to retailer login page for zone
    When user logged in using "admin" as user and password as "admin" for zone
    Then home page should be displayed for zone
    When location button is clicked
    When create zone is selected
    Then create zone modal is displayed
    When zone value is entered as "<zoneName>" and price per unit is entered as "<pricePerUnit>"
    When save button is clicked
    Then zone created succesfully should be displayed

    Examples: 
      | zoneName  | pricePerUnit |
      | Zone69422 |          180 |
