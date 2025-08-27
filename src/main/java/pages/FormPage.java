package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormPage extends AbsBasePage {
    private static final Logger logger = LoggerFactory.getLogger(FormPage.class);

    // Locators
    private final By usernameField = By.id("username");
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By confirmPasswordField = By.id("confirm_password");
    private final By birthDateField = By.id("birthdate");
    private final By languageLevelSelect = By.id("language_level");
    private final By submitButton = By.xpath("//*[@id=\"registrationForm\"]/input");
    private final By resultDiv = By.id("output");

    public FormPage(WebDriver driver) {
        super(driver, "/form.html");
        logger.info("Initialized FormPage with path: /form.html");
    }

    @Override
    public void open() {
        super.open();
        logger.info("Opened FormPage at URL: {}", driver.getCurrentUrl());
        wait.until(d -> $(usernameField).isDisplayed());
    }

    public FormPage fillUsername(String username) {
        logger.info("Filling username: {}", username);
        $(usernameField).sendKeys(username);
        return this;
    }

    public FormPage fillEmail(String email) {
        logger.info("Filling email: {}", email);
        $(emailField).sendKeys(email);
        return this;
    }

    public FormPage fillPassword(String password) {
        logger.info("Filling password");
        $(passwordField).sendKeys(password);
        return this;
    }

    public FormPage fillConfirmPassword(String confirmPassword) {
        logger.info("Filling confirm password");
        $(confirmPasswordField).sendKeys(confirmPassword);
        return this;
    }

    public FormPage fillBirthDate(String birthDate) {
        logger.info("Filling birth date: {}", birthDate);
        $(birthDateField).sendKeys(birthDate);
        return this;
    }

    public FormPage selectLanguageLevel(String level) {
        logger.info("Selecting language level: {}", level);
        logger.debug("Locating dropdown with locator: {}", languageLevelSelect);
        WebElement selectElement = $(languageLevelSelect);
        logger.debug("Dropdown found, checking if enabled and displayed");
        if (!selectElement.isEnabled() || !selectElement.isDisplayed()) {
            logger.error("Dropdown is not interactable: enabled={}, displayed={}", selectElement.isEnabled(), selectElement.isDisplayed());
            throw new AssertionError("Language level dropdown is not interactable");
        }
        Select select = new Select(selectElement);
        logger.debug("Available options: {}", select.getOptions().stream().map(WebElement::getText).toList());
        try {
            select.selectByVisibleText(level);
            logger.debug("Selected option by visible text: {}", select.getFirstSelectedOption().getText());
        } catch (Exception e) {
            logger.warn("Failed to select by visible text '{}', trying by value: {}", level, e.getMessage());
            select.selectByValue(level.toLowerCase());
            logger.debug("Selected option by value: {}", select.getFirstSelectedOption().getText());
        }
        return this;
        }

    public FormPage verifyPasswordMatch(String password, String confirmPassword) {
        logger.info("Verifying password match");
        if (!password.equals(confirmPassword)) {
            logger.error("Password '{}' does not match confirm password '{}'", password, confirmPassword);
            throw new AssertionError("Password and confirm password do not match");
        }
        return this;
    }

    public FormPage submitForm() {
        logger.info("Submitting form");
        $(submitButton).click();
        wait.until(d -> $(resultDiv).isDisplayed());
        return this;
    }

    public String getResultText() {
        logger.debug("Retrieving result text");
        return $(resultDiv).getText();
    }

    public FormPage verifyResultText(String expectedUsername, String expectedEmail, String expectedBirthDate, String expectedLevel) {
        logger.info("Verifying result text");
        String result = getResultText();
        // Convert expectedBirthDate from DD-MM-YYYY to YYYY-MM-DD
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(expectedBirthDate, inputFormatter);
        String formattedBirthDate = date.format(outputFormatter);
        String expected = String.format("Имя пользователя: %s\nЭлектронная почта: %s\nДата рождения: %s\nУровень языка: %s",
                expectedUsername, expectedEmail, formattedBirthDate, expectedLevel);
        if (!result.equals(expected)) {
            logger.error("Result text mismatch: expected '{}', but got '{}'", expected, result);
            throw new AssertionError(String.format("Result text mismatch: expected '%s', but got '%s'", expected, result));
        }
        return this;
    }

}