package apple.web.cms.model;

import apple.lib.ebean.database.AppleEbeanDatabase;
import apple.lib.ebean.database.config.AppleEbeanDatabaseConfig;
import apple.lib.ebean.database.config.AppleEbeanPostgresConfig;
import apple.web.cms.model.auth.authentication.DAuthToken;
import apple.web.cms.model.auth.identity.DUser;
import apple.web.cms.model.auth.identity.DUserBasicCredentials;
import apple.web.cms.model.image.DImage;
import apple.web.cms.model.raw.DJsonDocument;
import apple.web.cms.model.raw.DJsonField;
import io.ebean.config.AutoTuneConfig;
import io.ebean.config.AutoTuneMode;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import java.util.Collection;
import java.util.List;

public class CMSDatabase extends AppleEbeanDatabase {

    @Override
    protected void addEntities(List<Class<?>> entities) {
        // auth
        entities.addAll(List.of(DUser.class, DUserBasicCredentials.class, DAuthToken.class));
        // json
        entities.addAll(List.of(DJsonDocument.class, DJsonField.class));
        // image
        entities.add(DImage.class);
    }

    @Override
    protected boolean isDefault() {
        return true;
    }

    @Override
    protected Collection<Class<?>> getQueryBeans() {
        return List.of();
    }

    @Override
    protected AppleEbeanDatabaseConfig getConfig() {
        return CMSDatabaseConfig.get();
    }

    @Override
    protected DatabaseConfig configureDatabase(DataSourceConfig dataSourceConfig) {
        DatabaseConfig databaseConfig = super.configureDatabase(dataSourceConfig);
        AutoTuneConfig autoTune = databaseConfig.getAutoTuneConfig();
        autoTune.setProfiling(true);
        autoTune.setQueryTuning(true);
        autoTune.setMode(AutoTuneMode.DEFAULT_ON);
        return databaseConfig;
    }

    @Override
    protected String getName() {
        return "JsonCMS";
    }

    public static class CMSDatabaseConfig extends AppleEbeanPostgresConfig {

        private static CMSDatabaseConfig instance;

        public CMSDatabaseConfig() {
            instance = this;
        }

        public static CMSDatabaseConfig get() {
            return instance;
        }
    }
}
