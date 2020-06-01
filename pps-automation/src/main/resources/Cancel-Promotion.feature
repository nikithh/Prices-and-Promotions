@Cancel-Promotion
Feature: Cancel Promotion Step

  Scenario Outline: Cancel promotion with correct requirements
    Given navigate to login page for cancelling promotion
    When user logged in using "admin" as user and password as "admin" for cancelling promotion
    Then home page should be displayed for cancelling promotion
    When promotion dropdown clicked
    When cancel percentage promotion in zone level is selected
    Then cancel promotion modal is displayed
    When product name is selected as "<productName>",zone name as zoneName
    When go button is clicked for cancelling promotion
    Then cancel promotion table modal is displayed
    When selecting cancel promotion button
    When clicking on okay for promotion cancellation
    Then the promotion is cancelled

    Examples: 
      | productName |
      | Lactogen    |

  Scenario Outline: Cancel promotion with correct requirements
    Given navigate to login page for cancelling promotion
    When user logged in using "admin" as user and password as "admin" for cancelling promotion
    Then home page should be displayed for cancelling promotion
    When promotion dropdown clicked
    When cancel percentage promotion in zone level is selected
    Then cancel promotion modal is displayed
    When product name is selected as "<productName>",zone name as zoneName
    When go button is clicked for cancelling promotion
    Then cancel promotion table modal is displayed
    When selecting cancel promotion button
    When clicking cancel for promotion cancellation
    Then the promotion is not cancelled

    Examples: 
      | productName |
      | Lactogen    |
