package main;

import factory.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.FormPage;

public class MainPage_Test {
    private static final Logger logger = LoggerFactory.getLogger(MainPage_Test.class);
    private WebDriver driver;

    @BeforeEach
    public void init() {
        logger.info("Initializing WebDriver");
        String browser = System.getProperty("browser", "chrome");
        this.driver = WebDriverFactory.create(browser);
    }

    @AfterEach
    public void close() {
        if (driver != null) {
            logger.info("Closing WebDriver");
            driver.quit();
        }
    }

    @Test
    public void testFormSubmission() {
        logger.info("Starting test: testFormSubmission");
        String baseUrl = System.getProperty("base.url", "https://otus.home.kartushin.su");
        String username = System.getProperty("username", "TestName");
        String email = System.getProperty("email", "test@gmail.com");
        String password = System.getProperty("password", "Pass123!");
        String confirmPassword = System.getProperty("confirmPassword", "Pass123!");
        String birthDate = System.getProperty("birthDate", "10-10-2005");
        String languageLevel = System.getProperty("languageLevel", "advanced");

        FormPage formPage = new FormPage(driver, baseUrl);
        logger.debug("Opening form page: {}", baseUrl + "/form.html");
        formPage.open();

        logger.debug("Filling username: {}", username);
        formPage.fillUsername(username);

        logger.debug("Filling email: {}", email);
        formPage.fillEmail(email);

        logger.debug("Filling password");
        formPage.fillPassword(password);

        logger.debug("Filling confirm password");
        formPage.fillConfirmPassword(confirmPassword);

        logger.debug("Verifying password match");
        formPage.verifyPasswordMatch(password, confirmPassword);

        logger.debug("Filling birth date: {}", birthDate);
        formPage.fillBirthDate(birthDate);

        logger.debug("Selecting language level: {}", languageLevel);
        formPage.selectLanguageLevel(languageLevel);

        logger.debug("Submitting form");
        formPage.submitForm();

        logger.debug("Verifying result text (exact match)");
        formPage.verifyResultText(username, email, birthDate, languageLevel);

        logger.debug("Additional verification of result text (contains check)");
        formPage.verifyResultTextContains(username, email, birthDate, languageLevel);

        logger.info("Test testFormSubmission passed");
    }
}