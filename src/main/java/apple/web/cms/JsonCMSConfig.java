package apple.web.cms;

public class JsonCMSConfig {

    private static JsonCMSConfig instance;
    public String pepper = "pepper";
    public String adminUsername = "username";
    public String adminPassword = "password";

    public JsonCMSConfig() {
        instance = this;
    }

    public static JsonCMSConfig get() {
        return instance;
    }

    public boolean isConfigured() {
        return !pepper.equals("pepper") &&
            !adminUsername.equals("username") &&
            !adminPassword.equals("password");
    }
}
