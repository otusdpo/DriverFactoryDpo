package factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeDriverSettings implements ISettings {

    @Override
    public AbstractDriverOptions settings(DesiredCapabilities desiredCapabilities, String... userArgs) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.addArguments(userArgs);

        chromeOptions.merge(desiredCapabilities);

        return chromeOptions;

    }
}
