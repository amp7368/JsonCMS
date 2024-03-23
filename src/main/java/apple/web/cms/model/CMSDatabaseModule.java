package apple.web.cms.model;

import apple.lib.ebean.database.AppleEbeanDatabaseMetaConfig;
import apple.lib.modules.AppleModule;
import apple.lib.modules.configs.factory.AppleConfigLike;
import apple.web.cms.JsonCMS;
import apple.web.cms.JsonCMSConfig;
import apple.web.cms.model.CMSDatabase.CMSDatabaseConfig;
import apple.web.cms.model.auth.AuthQuery;
import java.util.List;

public class CMSDatabaseModule extends AppleModule {

    private static CMSDatabaseModule instance;

    public CMSDatabaseModule() {
        instance = this;
    }

    public static CMSDatabaseModule get() {
        return instance;
    }

    @Override
    public void onLoad() {
        AppleEbeanDatabaseMetaConfig.configureMeta(
            JsonCMS.class,
            JsonCMS.get().getDataFolder(),
            logger()::error,
            logger()::info);

        new CMSDatabase();

        if (!JsonCMSConfig.get().isConfigured()) {
            logger().error("Please configure JsonCMSConfig");
            System.exit(1);
        }
        AuthQuery.createDefaultAdmin();
    }

    @Override
    public List<AppleConfigLike> getConfigs() {
        return List.of(configJson(CMSDatabaseConfig.class, "DatabaseConfig"));
    }

    @Override
    public String getName() {
        return "Database";
    }
}
