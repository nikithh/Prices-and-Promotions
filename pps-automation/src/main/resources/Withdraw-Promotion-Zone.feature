@Withdraw-Promotion-Zone
Feature: Withdraw percentage promotion from zone

  Scenario Outline: Withdraw percentage promotion from zone approved
    Given navigate to login page for withdrawing promotion in zone level
    Then user logged in using "admin" as user and password as "admin" for withdrawing zone level promotion
    Then home page should be displayed for withdrawing zone level promotion
    When promotion dropdown is selected
    When withdraw promotion in zone level is selected
    Then withdraw promotion from zone modal is displayed
    When product name is selected as "<productName>"
    When go button is clicked for withdrawing promotion from zone level
    Then promotion in zone level table is displayed
    When withdraw button is clicked
    When clicking on ok button

    Examples: 
      | productName |
      | cerelac     |

  Scenario Outline: Withdraw percentage prmotion from zone disapproved
    Given navigate to login page for withdrawing promotion in zone level
    Then user logged in using "admin" as user and password as "admin" for withdrawing zone level promotion
    Then home page should be displayed for withdrawing zone level promotion
    When promotion dropdown is selected
    When withdraw promotion in zone level is selected
    Then withdraw promotion from zone modal is displayed
    When product name is selected as "<productName>"
    When go button is clicked for withdrawing promotion from zone level
    Then promotion in zone level table is displayed
    When withdraw button is clicked
    When clicking on cancel button

    Examples: 
      | productName |
      | cerelac     |
