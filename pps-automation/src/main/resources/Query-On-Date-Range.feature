@Query-On-Date-Range
Feature: Querying Promotions for Products for a given date range

  Scenario Outline: Querying info based on correct requirements
    Given navigate to retailer login page for querying date range
    When user logged in using "admin" as user and password as "admin" for querying date range
    Then home page should be displayed for querying date range
    When promotion button is selected
    Then query for date range button is clicked on nav bar
    Then Promotions for Products page is displayed
    When Start date is entered as "<startDate>" and End date is entered as "<endDate>"
    When Zone is selected
    Then Show promotions button is clicked
    Then Promotions for Products page is shown

    Examples: 
      | startDate  | endDate    |
      | 02/02/2020 | 02/03/2020 |

  Scenario Outline: Querying info based on correct requirements
    Given navigate to retailer login page for querying date range
    When user logged in using "admin" as user and password as "admin" for querying date range
    Then home page should be displayed for querying date range
    When promotion button is selected
    Then query for date range button is clicked on nav bar
    Then Promotions for Products page is displayed
    When Start date is entered as "<startDate>" and End date is entered as "<endDate>"
    When Cluster is selected
    Then Show promotions button is clicked
    Then Promotions for Products page is shown

    Examples: 
      | startDate  | endDate    |
      | 02/02/2020 | 02/03/2020 |

  Scenario Outline: Querying info based on incorrect requirements
    Given navigate to retailer login page for querying date range
    When user logged in using "admin" as user and password as "admin" for querying date range
    Then home page should be displayed for querying date range
    When promotion button is selected
    Then query for date range button is clicked on nav bar
    Then Promotions for Products page is displayed
    When Start date is entered as "<startDate>" and End date is entered as "<endDate>"
    Then End Date has to be greater than Start Date criteria is not met

    Examples: 
      | startDate  | endDate    |
      | 02/02/2020 | 02/01/2020 |
