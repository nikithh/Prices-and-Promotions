@Add-Product-To-Store-Retailer
Feature: Add Product to Store as Retailer

  Scenario Outline: Add Product to store as retailer with correct requirements
    Given access to retailer login page
    When user logged in as "admin" as user and password as "admin"
    Then home page should be shown
    When add Product To Store is selected
    Then add Product To Store modal is displayed
    Then zone value, cluster value and store value is selected
    When Add Products button is clicked
    Then Add Products page should be displayed
    When enter category is selected
    Then product type is selected
    Then quantity value is entered as "<quantity>"
    When select checkbox is ticked
    Then next Add Products button is clicked

    Examples: 
      | quantity |
      |        2 |
