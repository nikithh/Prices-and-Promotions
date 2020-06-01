@Create-Group
Feature: Add Group Step

  Scenario Outline: Add group with correct requirements
    Given navigate to retailer login page for group
    When user logged in using "admin" as user and password as "admin" for group
    Then home page should be displayed for group
    When add group is selected
    Then add group modal is displayed
    When group value is entered as "<groupName>"
    When add group save button is clicked
    Then add group succeeded should be displayed

    Examples: 
      | groupName  |
      | Group69422 |

  Scenario Outline: Adding group that already exists
    Given navigate to retailer login page for group
    When user logged in using "admin" as user and password as "admin" for group
    Then home page should be displayed for group
    When add group is selected
    Then add group modal is displayed
    When group value is entered as "<groupName>"
    When add group save button is clicked
    Then group already exists should be displayed

    Examples: 
      | groupName |
      | group98   |

  Scenario Outline: Add group with incorrect requirements
    Given navigate to retailer login page for group
    When user logged in using "admin" as user and password as "admin" for group
    Then home page should be displayed for group
    When add group is selected
    Then add group modal is displayed
    When group value is entered as "<groupName>"
    When add group save button is clicked
    Then add group failed should be displayed

    Examples: 
      | groupName |
      |           |
