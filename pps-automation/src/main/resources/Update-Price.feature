@Update-price
Feature: Update the price of a product

  Scenario Outline: Update the price of the product
    Given navigate to login page for updating the price of the product
    When clicked on login as vendor
    Then user logged in using "testcompany" as user and password as "yatheesha" for updating the price of the product
    Then home page should be displayed for updating the price of the product
    When update product price is selected
    Then update price modal is displayed
    When product name here is selected as "<productName>"
    When edit button is clicked to update the price
    Then update price and quantity modal is displayed
    When product base "<price>" is entered, "<quantity>" is entered
    When clicking on update button
    Then price is updated

    Examples: 
      | productName | price | quantity |
      | Nutriben    |     5 |        2 |
