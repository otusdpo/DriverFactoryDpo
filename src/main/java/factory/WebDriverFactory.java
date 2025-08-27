package factory;

import exceptions.BrowserNotSupportedException;
import factory.settings.ChromeDriverSettings;
import factory.settings.ISettings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

    public static WebDriver create(String webDriverName, Object options) {
        logger.info("Creating WebDriver for browser: {}", webDriverName);
        Browser browser = Browser.fromString(webDriverName);

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ISettings settings = new ChromeDriverSettings();
                ChromeOptions chromeOptions = new ChromeOptions ();
                if (options instanceof ChromeOptions) {
                    chromeOptions.merge((ChromeOptions) options);
                }
                return new ChromeDriver(chromeOptions);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (options instanceof FirefoxOptions) {
                    firefoxOptions.merge((FirefoxOptions) options);
                }
                return new FirefoxDriver(firefoxOptions);
            case SAFARI:
                WebDriverManager.safaridriver().setup();
                SafariOptions safariOptions = new SafariOptions();
                if (options instanceof SafariOptions) {
                    safariOptions.merge((SafariOptions) options);
                }
                return new SafariDriver(safariOptions);
            default:
                logger.error("Unsupported browser: {}", webDriverName);
                throw new BrowserNotSupportedException(webDriverName);
        }
    }

    public static WebDriver create(String webDriverName) {
        return create(webDriverName, null);
    }
}