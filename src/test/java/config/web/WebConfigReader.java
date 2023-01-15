package config.web;

import org.aeonbits.owner.ConfigFactory;

public enum WebConfigReader {

    Instance;

    private static final WebConfig webConfig = ConfigFactory.create(WebConfig.class, System.getProperties());

    public WebConfig read() {
        return webConfig;
    }
}
