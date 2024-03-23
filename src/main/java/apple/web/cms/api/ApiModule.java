package apple.web.cms.api;

import apple.lib.modules.AppleModule;
import apple.lib.modules.configs.factory.AppleConfigLike;
import apple.web.cms.api.auth.AuthController;
import apple.web.cms.api.base.ApiController;
import apple.web.cms.api.endpoint.json.JsonController;
import apple.web.cms.model.auth.ApiSecurity;
import io.javalin.Javalin;
import io.javalin.json.JavalinGson;
import java.util.List;

public class ApiModule extends AppleModule {

    private static ApiModule instance;

    public ApiModule() {
        instance = this;
    }

    public static ApiModule get() {
        return instance;
    }

    @Override
    public void onEnable() {
        Javalin app = Javalin.create(cfg -> {
            ApiConfig.get().commonConfig(cfg);
            cfg.jsonMapper(new JavalinGson(ApiController.apiGson(), false));
        });
        app.beforeMatched(ApiSecurity::handle);

        registerControllers(app);
        ExceptionHandlers.registerExceptions(app);
        app.start(getPort());
    }

    private void registerControllers(Javalin app) {
        new JsonController().register(app);
        new AuthController().register(app);
    }

    private int getPort() {
        return ApiConfig.get().getPort();
    }

    @Override
    public List<AppleConfigLike> getConfigs() {
        return List.of(configJson(ApiConfig.class, "Api.config"));
    }

    @Override
    public String getName() {
        return "Api";
    }
}
