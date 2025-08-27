package main;

import factory.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.FormPage;
import java.util.Properties;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class MainPage_Test {
    private static final Logger logger = LoggerFactory.getLogger(MainPage_Test.class);
    private WebDriver driver;

    @BeforeEach
    public void init() {
        logger.info("Initializing WebDriver");
        String browser = System.getProperty("browser", "chrome");
        this.driver = WebDriverFactory.create(browser);

        Properties props = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
        if (input == null) {
            logger.warn("config.properties not found, using default base.url");
            System.setProperty("base.url", "https://otus.home.kartushin.su");
        } else {
            try {
                props.load(input);
                System.setProperty("base.url", props.getProperty("base.url", "https://otus.home.kartushin.su"));
            } catch (Exception e) {
                logger.error("Failed to load config.properties", e);
            } finally {
                try {
                    input.close();
                } catch (Exception e) {
                    logger.error("Failed to close input stream", e);
                }
            }
        }
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
        String username = System.getProperty("username", "TestName");
        String email = System.getProperty("email", "test@gmail.com");
        String password = System.getProperty("password", "Pass123!");
        String confirmPassword = System.getProperty("confirmPassword", "Pass123!");
        String birthDate = System.getProperty("birthDate", "10-10-2005");
        String languageLevel = System.getProperty("languageLevel", "advanced");

        FormPage formPage = new FormPage(driver);
        logger.debug("Opening form page");
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
        String resultText = formPage.getResultText();
        // Convert birthDate from DD-MM-YYYY to YYYY-MM-DD
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(birthDate, inputFormatter);
        String formattedBirthDate = date.format(outputFormatter);
        assertThat(resultText)
                .as("Additional verification of form submission result")
                .contains("Имя пользователя: " + username + "\n")
                .contains("Электронная почта: " + email + "\n")
                .contains("Дата рождения: " + formattedBirthDate + "\n")
                .contains("Уровень языка: " + languageLevel);

        logger.info("Test testFormSubmission passed");
    }
}