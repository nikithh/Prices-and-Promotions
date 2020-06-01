@Create-Store
Feature: Create Store Step

  Scenario Outline: Create store with correct requirements
    Given navigate to vendor login page for store
    When user logged in using "admin" as user and password as "admin" for store
    Then home page should be displayed for store
    When location is clicked
    When create store is selected
    Then create store modal is displayed
    When zone value is selected as zoneName , cluster name as clusterName, store name is selected as "<storeName>"
    When street name value is selected as "<streetName>", city name as "<cityName>" and pin code as "<pinCode>"
    When save button is clicked for adding store
    Then store created should be displayed

    Examples: 
      | storeName  | streetName | cityName | pinCode |
      | Store69422 | Street347  | City457  |   56015 |

  Scenario Outline: Create store with incorrect requirements
    Given navigate to vendor login page for store
    When user logged in using "admin" as user and password as "admin" for store
    Then home page should be displayed for store
    When location is clicked
    When create store is selected
    Then create store modal is displayed
    When zone value is selected as zoneName , cluster name as clusterName
    When save button is clicked for adding store
    Then store creation failed should be displayed

    Examples: 
      | storeName | streetName | cityName | pinCode |
      |           |            |          |         |
