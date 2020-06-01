@vendor-reg
Feature: Registration

  Scenario Outline: Registration senario for Vendor
    Given navigate to vendor reg form
    When email value is entered as "<Email>" and password as "<Password>" , confirm password as "<ConfirmPassword>",company name as "<Company Name>",company type as "<Company Type>" product Catogory as "<Product Catogry>"
    Then  validate the outcomes
   
    Examples: 
      | Email         	 |Password |ConfirmPassword|Company Name|Company Type|Product Catogry|
      | abc123@gmail.com |12345678 | 12345678      | company    |BabyFood    |Baby           |

