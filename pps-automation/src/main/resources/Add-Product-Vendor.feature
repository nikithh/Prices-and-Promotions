@Vendor-AddProduct
Feature: Vendor Adding product Feature File

  Scenario Outline: Vendor adding product scenario test for product being alcohol
    Given navigate to login page for vendor
    When Clicked on login as vendor
    When user logged in using "testcompany" as user input and password as "yatheesha" and login is clicked
    Then Vendor home page should be displayed for adding product
    When add a product is clicked
    Then add a product modal is opened to add
    When product name is given as "<productName>"
    When product category is selected as alcohol product
    #When quantity is selected as "<quantity>",product base price is selected as "<baseprice>"
    #When abv is entered as "<abv>", volume is entered as "<vol>" and uom is selected
    #When product product description is selected as "<description>"
    #When clicked on save button to save the product
    #Then product is saved

    Examples: 
      | productName | quantity | baseprice | abv | vol | description                    |
      | SmirnOfff   |       50 |       299 |  10 | 500 | Smirnoff is an alcohol product |

  Scenario Outline: Vendor adding product scenario test for product being baby
    Given navigate to login page for vendor
    When Clicked on login as vendor
    When user logged in using "testcompany" as user input and password as "yatheesha" and login is clicked
    Then Vendor home page should be displayed for adding product
    When add a product is clicked
    Then add a product modal is opened to add
    When product name is given as "<productName>"
    When product category is selected as baby product
    #When quantity is selected as "<quantity>",product base price is selected as "<baseprice>"
    #When uom is selected
    #When product product description is selected as "<description>"
    #When clicked on save button to save the product
    #Then product is saved

    Examples: 
      | productName | quantity | baseprice | description              |
      | Cerlacccc   |       50 |       199 | Cerlac is a baby product |
