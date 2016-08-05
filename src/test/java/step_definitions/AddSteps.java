package step_definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.Assert.fail;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

/**
 * Created by Ares on 03/08/2016.
 */
public class AddSteps {
    private static final String ADD_BUTTON = "add";
    private static final String NAME_INPUT_FIELD = "name";
    private static final String NEW_COMPUTER_NAME = "Test Computer";
    private static final String INTRODUCED_INPUT_FIELD = "introduced";
    private static final String DISCONTINUED_INPUT_FIELD = "discontinued";
    private static final String COMPANY_DROPDOWN_SELECTOR_MENU = "company";
    private static final String COMPANY_SELECTION = "Nokia";
    private static final String HOMEPAGE_URL = "http://computer-database.herokuapp.com/computers";
    private static final String NEW_COMPUTER_URL = "http://computer-database.herokuapp.com/computers/new";
    private static final String COMPUTER_WARNING_MESSAGE = "Required";
    private static final String DATE_WARNING_MESSAGE = "Date ('yyyy-MM-dd')";

    public WebDriver driver;

    public AddSteps() {
        driver = Hooks.driver;
    }

    @Given("^home page has loaded successfully$")
    public void goToHomepage() throws Throwable {
        //open the web page
        driver.get(HOMEPAGE_URL);

        //validate Title and URL
        assertEquals("Computers database", driver.getTitle());
        assertEquals(HOMEPAGE_URL, driver.getCurrentUrl());
    }

    @Given("^the Add button has been selected$")
    public void selectAddButton() throws Throwable {
        //click the button link
        driver.findElement(By.id(ADD_BUTTON)).click();
    }

    @Given("^the 'Add computer' web page has been displayed$")
    public void displayAddComputerWebPage() throws Throwable {
        //validate Title and URL
        assertEquals("Computers database", driver.getTitle());
        assertEquals(NEW_COMPUTER_URL, driver.getCurrentUrl());
    }

    @Given("^the 'Computer name' textfield has been populated$")
    public void populateCompNameField() throws Throwable {
        //type into the Name textfield
        driver.findElement(By.id(NAME_INPUT_FIELD)).sendKeys("Test Computer");
    }

    @Given("^the 'Introduced date' has been correctly populated$")
    public void populateIntroDate() throws Throwable {
        //type into the Introduced Date textfield
        driver.findElement(By.id(INTRODUCED_INPUT_FIELD)).sendKeys("2016-08-06");
    }

    @Given("^the 'Discontinued date' has been correctly populated$")
    public void populateDiscoDate() throws Throwable {
        //type into the Discontinued Date textfield
        driver.findElement(By.id(DISCONTINUED_INPUT_FIELD)).sendKeys("2017-08-06");
    }

    @Given("^a computer Company has been selected$")
    public void selectComputerCompany() throws Throwable {
        //select from the Company dropdown menu selector
        Select companySelect = new Select(driver.findElement(By.id(COMPANY_DROPDOWN_SELECTOR_MENU)));
        companySelect.selectByVisibleText(COMPANY_SELECTION);
    }

    @Given("^the 'Introduced date' has not been correctly populated")
    public void populateIncorrectIntroDate() throws Throwable {
        //type into the Discontinued Date textfield
        driver.findElement(By.id(INTRODUCED_INPUT_FIELD)).sendKeys("201n-08-06");
    }

    @Given("^the 'Discontinued date' has not been correctly populated")
    public void populateIncorrectDiscoDate() throws Throwable {
        //type into the Discontinued Date textfield
        driver.findElement(By.id(DISCONTINUED_INPUT_FIELD)).sendKeys("2017-08- 6");
    }

    @Given("^a malicious Sql query has been added to the (Computer name|Introduced date|Discontinued date)$")
    public void populateFieldWithSqlQuery(String fieldName) throws Throwable {

        if ("Computer name".equals(fieldName)) {
            driver.findElement(By.id(NAME_INPUT_FIELD)).sendKeys("105; DROP TABLE Computers");
        } else if ("Introduced date".equals(fieldName)) {
            driver.findElement(By.id(INTRODUCED_INPUT_FIELD)).sendKeys("105; DROP TABLE Computers");
        } else if ("Discontinued date".equals(fieldName)) {
            driver.findElement(By.id(DISCONTINUED_INPUT_FIELD)).sendKeys("105; DROP TABLE Computers");
        } else {
            fail("Input couldn't be matched.");
        }

    }

    @When("^the 'Create this computer' button is selected$")
    public void selectCreateNewComputerButton() throws Throwable {
        //select Create new Computer button
        driver.findElement(By.xpath("//div[@class='actions']/input[@value='Create this computer']")).click();
    }

    @When("^the 'Cancel' button is selected$")
    public void selectCancelButton() throws Throwable {
        //select Create new Computer button
        driver.findElement(By.xpath("//div[@class='actions']/a[@class='btn']")).click();
    }

    @Then("^the home page will load successfully$")
    public void returnToHomepage() throws Throwable {
        Thread.sleep(1000);
        //assert we are on the right web page
        assertEquals("Browser hasn't returned to homepage", HOMEPAGE_URL, driver.getCurrentUrl());
        //validate Title and URL
        assertEquals("Computers database", driver.getTitle());
    }

    @Then("^a creation confirmation message will be displayed$")
    public void confirmSuccessfulCreationMessage() throws Throwable {
        //validate successful creation message
        assertEquals("Confirmation message doesn't match the condition!", "Done! Computer " + NEW_COMPUTER_NAME +
                " has been created", driver.findElement(By.cssSelector(".alert-message.warning")).getText());
    }

    @Then("^no creation confirmation message will be displayed$")
    public void confirmNoCreationMessage() throws Throwable {
        boolean isMessageVisible = false;
        try {
            driver.findElement(By.cssSelector(".alert-message.warning"));
            isMessageVisible = true;
        } catch (NoSuchElementException ne) {
        }

        assertFalse("Message confirmation element has been found!", isMessageVisible);
    }

    @Then("^a 'mandatory field' error message will be displayed$")
    public void verifyMandatoryFieldWarning() throws Throwable {
        assertEquals("Warning message hasn't been displayed!", "Required", driver.findElement(By.cssSelector(".help-inline")).getText());
    }

    @Then("^a (Date|ComputerName) warning message will be displayed for the relevant fields$")
    public void verifyDateFieldWarning(String warningMessage) throws Throwable {
        //add all warning messages in a List and assert their text
        List<WebElement> list = driver.findElements(By.cssSelector(".clearfix.error"));

        for (WebElement element : list) {
            warningMessage = element.findElement(By.cssSelector(".help-inline")).getText();
            //assert only the elements which have been highlighted (selected)
            if (warningMessage.equalsIgnoreCase(DATE_WARNING_MESSAGE)) {
                assertEquals("Wrong warning message has been displayed for Date!", DATE_WARNING_MESSAGE, warningMessage);
            } else if (warningMessage.equalsIgnoreCase(COMPUTER_WARNING_MESSAGE)) {
                assertEquals("Wrong warning message has been displayed for Date!", COMPUTER_WARNING_MESSAGE, warningMessage);
            } else {
                fail("No warning message matched.");
            }
        }
    }
}