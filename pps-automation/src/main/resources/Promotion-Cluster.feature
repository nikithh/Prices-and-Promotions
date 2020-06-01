@Promotion-Cluster
Feature: Apply Promotion in Cluster Step

  Scenario Outline: Apply promotion with correct requirements
    Given navigate to login page for promotion in cluster level
    When user logged in using "admin" as user and password as "admin" for cluster level promotion
    Then home page should be displayed for cluster level promotion
    When promotion btn is clicked
    When apply promotion in cluster level is selected
    Then promotion product modal for cluster is displayed
    When product name is selected as "<productName>", zoneName and clusterName
    When go button is clicked for applying promotion in cluster level
    Then Apply percentage promotion cluster modal is displayed
    When selecting promotion percentage as "<promotionPercentage>",start date as "<startDate>", end date as "<endDate>"
    When Apply promotion to cluster button is clicked
    Then promotions in cluster level table is displayed

    Examples: 
      | productName | promotionPercentage | startDate  | endDate    |
      | cerelac     |                 -20 | 02/02/2020 | 02/03/2020 |
