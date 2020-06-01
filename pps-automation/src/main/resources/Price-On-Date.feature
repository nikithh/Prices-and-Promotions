@Price-On-Date
Feature: Price On Date

  Scenario Outline: Assign Price On Date
    Given navigate to retailer login page for assigning price on date
    When user logged in using "admin" as user and password as "admin" for assigning price on date
    Then home page should be displayed for assigning price on date
    When prices dropdown is selected
    When price on date is selected
    Then price on date modal is displayed
    When product name is entered as "<productName>" for price on date
    When assign price to product is clicked
    Then it should route to next page with product details and promotion details
    When start date is entered as "<startDate>" and End date is entered as "<endDate>" and percentage as "<effectivePercentage>"
    When save is clicked
    Then price assignment is complete

    Examples: 
      | productName | startDate  | endDate    | effectivePercentage |
      | SulaWine    | 06/04/2020 | 07/13/2020 |                   5 |
