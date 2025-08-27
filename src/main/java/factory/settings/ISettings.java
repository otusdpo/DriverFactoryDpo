package factory.settings;

import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface ISettings {

    AbstractDriverOptions settings (DesiredCapabilities desiredCapabilities, String... userArgs);

}
