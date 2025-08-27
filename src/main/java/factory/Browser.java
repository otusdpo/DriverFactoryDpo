package factory;

public enum Browser {
    CHROME("chrome"),
    FIREFOX("firefox"),
    SAFARI("safari");

    private final String name;

    Browser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Browser fromString(String browserName) {
        for (Browser browser : values()) {
            if (browser.getName().equalsIgnoreCase(browserName)) {
                return browser;
            }
        }
        throw new IllegalArgumentException("No enum constant for browser: " + browserName);
    }
}
