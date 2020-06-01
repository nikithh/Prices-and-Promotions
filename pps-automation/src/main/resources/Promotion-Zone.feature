@Promotion-Zone
Feature: Apply Promotion in Zone Step

  Scenario Outline: Apply promotion with correct requirements
    Given navigate to login page for promotion in zone level
    When user logged in using "admin" as user and password as "admin" for zone level promotion
    Then home page should be displayed for zone level promotion
    When promotion button is clicked
    When apply promotion in zone level is selected
    Then promotion product modal is displayed
    When zone name is selected and product name is inserted as "<productName>"
    Then check if requirements are met
    When go button is clicked for applying promotion in zone level
    Then Apply percentage promotion modal is displayed
    When selecting promotion percentage as "<promotionPercentage>",start date as "<startDate>" and end date as "<endDate>"
    When Apply promotion to zone button is clicked
    Then promotions in zone level table is displayed

    Examples: 
      | productName | promotionPercentage | startDate  | endDate    |
      | cerelac     |                  20 | 02/02/2020 | 02/03/2020 |
