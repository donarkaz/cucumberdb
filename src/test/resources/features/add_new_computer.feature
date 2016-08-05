Feature: Story B-2: Add new computers

  As an administrator
  I want to be able to add new computers to my database
  So that I can keep record and enable various system views

  Background:
    Given home page has loaded successfully
    And the Add button has been selected
    And the 'Add computer' web page has been displayed

  @sanity @regression @sunny
  Scenario: 1. Creation with all fields
    Given the 'Computer name' textfield has been populated
    And the 'Introduced date' has been correctly populated
    And the 'Discontinued date' has been correctly populated
    And a computer Company has been selected
    When the 'Create this computer' button is selected
    Then the home page will load successfully
    And a creation confirmation message will be displayed

  @regression @sunny
  Scenario: 2. Creation with only mandatory fields
    Given the 'Computer name' textfield has been populated
    When the 'Create this computer' button is selected
    Then the home page will load successfully
    And a creation confirmation message will be displayed

  @regression @sunny
  Scenario: 3. Cancel creation
    When the 'Cancel' button is selected
    Then the home page will load successfully
    And no creation confirmation message will be displayed

  @regression @rainy
  Scenario: 4. No mandatory fields filled in
    When the 'Create this computer' button is selected
    Then a ComputerName warning message will be displayed for the relevant fields

  @regression @rainy
  Scenario: 5. Non-date format input in Date fields
    Given the 'Introduced date' has not been correctly populated
    And the 'Discontinued date' has not been correctly populated
    When the 'Create this computer' button is selected
    Then a Date warning message will be displayed for the relevant fields

  @regression @rainy
  Scenario Outline: 6. Sql injection in Date fields
    Given a malicious Sql query has been added to the <textfield>
    When the 'Create this computer' button is selected
    Then a Date warning message will be displayed for the relevant fields

    Examples:
      | textfield         |
      | Introduced date   |
      | Discontinued date |