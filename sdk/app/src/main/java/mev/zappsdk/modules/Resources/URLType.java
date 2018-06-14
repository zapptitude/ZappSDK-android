package mev.zappsdk.modules.Resources;

/**
 * Created by Andrew on 2/6/18.
 */

public enum URLType {
    DEV_ENVIRONMENT("https://zapptitude-dev.herokuapp.com/api"), PROD_ENVIRONMENT("https://test.zapptitude.com/api");

    private String value;

    public String getValue() {
        return value;
    }

    URLType(String value) {
        this.value = value;
    }
}
