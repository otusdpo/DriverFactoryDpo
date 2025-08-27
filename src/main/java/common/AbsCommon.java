package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public abstract class AbsCommon {
    protected WebDriver driver;
    protected Actions actions;
    protected WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(AbsCommon.class);

    public AbsCommon(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement $(By locator) {
        logger.debug("Finding element by locator: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> $$(By locator) {
        logger.debug("Finding elements by locator: {}", locator);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
}