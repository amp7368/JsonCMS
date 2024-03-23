package apple.web.cms;

import apple.lib.modules.AppleModule;
import apple.lib.modules.ApplePlugin;
import apple.lib.modules.configs.factory.AppleConfigLike;
import apple.web.cms.api.ApiModule;
import apple.web.cms.model.CMSDatabaseModule;
import java.util.List;

public class JsonCMS extends ApplePlugin {

    private static JsonCMS instance;

    public JsonCMS() {
        instance = this;
    }

    public static void main(String[] args) {
        new JsonCMS().start();
    }

    public static JsonCMS get() {
        return instance;
    }

    @Override
    public String getName() {
        return "JsonCMS";
    }

    @Override
    public List<AppleModule> createModules() {
        return List.of(new CMSDatabaseModule(), new ApiModule());
    }

    @Override
    public List<AppleConfigLike> getConfigs() {
        return List.of(configJson(JsonCMSConfig.class, "JsonCMSConfig"));
    }
}
