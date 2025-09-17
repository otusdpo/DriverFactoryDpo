package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class AbsBasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private final String path;
    private static final Logger logger = LoggerFactory.getLogger(AbsBasePage.class);

    public AbsBasePage(WebDriver driver, String path) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.path = path;
    }

    public void open() {
        logger.info("Opening page: {}", path);
        driver.get(path);
    }

    protected WebElement $(By locator) {
        return driver.findElement(locator);
    }
}