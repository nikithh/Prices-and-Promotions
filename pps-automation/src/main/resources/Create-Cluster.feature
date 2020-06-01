@Create-Cluster
Feature: Create Cluster Step

  Scenario Outline: Create cluster with incorrect requirements
    Given navigate to retailer login page for creating cluster
    When user logged in using "admin" as user and password as "admin" for creating cluster
    Then home page should be displayed for creating cluster
    When location dropdown is selected
    When create cluster is selected for creating cluster
    Then create cluster modal is displayed for creating cluster
    When zone value is selected as "<zoneName>" and clustername is entered as "<clusterName>" and taxrate is entered as "<taxRate>"
    When save button is clicked for creating cluster
    Then cluster creation failed should be displayed for creating cluster

    Examples: 
      | zoneName  | clusterName | taxRate |
      | Australia | four        |      10 |

  Scenario Outline: Create cluster with correct requirements
    Given navigate to retailer login page for creating cluster
    When user logged in using "admin" as user and password as "admin" for creating cluster
    Then home page should be displayed for creating cluster
    When location dropdown is selected
    When create cluster is selected for creating cluster
    Then create cluster modal is displayed for creating cluster
    When zone value is selected as "<zoneName>" and clustername is entered as "<clusterName>" and taxrate is entered as "<taxRate>"
    When save button is clicked for creating cluster
    Then cluster created succesfully should be displayed for creating cluster

    Examples: 
      | zoneName  | clusterName | taxRate |
      | Australia | Custer69422 |      15 |
