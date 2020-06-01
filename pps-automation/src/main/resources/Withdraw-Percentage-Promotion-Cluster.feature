@Withdraw-Percentage-Promotion-Cluster
Feature: Withdraw promotion from cluster level feature

  Scenario Outline: To withdraw promotion from cluster level successfully
    Given navigate to login page to withdraw promotion in cluster level
    When user logged in using "admin" as user and password as "admin" for withdrawing promotion at cluster level
    Then home page should be displayed for withdraw promotion at cluster level
    When promotion is clicked
    When withdraw percentage promotion from cluster level is selected
    Then withdraw percentage promotion modal is displayed
    When zone name and cluster is selected and product name is inserted as "<productName>"
    Then check if all requirements are met for withdrawing percentage promotion from cluster level
    When go button is clicked for withdrawing promotion in cluster level
    Then promotion at cluster modal is displayed

    Examples: 
      | productName |
      | cerelac     |
