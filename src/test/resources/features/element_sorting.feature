Feature: Story B-1: Sorting elements in page view

  As a user
  I want to be able to sort the listed page objects alphabetically
  So that I can find the objects I'm looking for quickly

  Background:
    Given web page 'http://computer-database.herokuapp.com/computers' has loaded successfully

  Scenario: 1. Alphabetical column sorting
    When the <relevant> column header is selected
    Then .....