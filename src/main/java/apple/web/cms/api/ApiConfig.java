package apple.web.cms.api;

import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.plugin.bundled.CorsPluginConfig.CorsRule;
import org.apache.logging.log4j.Logger;

public class ApiConfig {

    private static ApiConfig instance;
    protected boolean cors = false;
    protected int port = 80;

    public ApiConfig() {
        instance = this;
    }

    public static ApiConfig get() {
        return instance;
    }

    private static void logger(Context ctx, Float time) {
        Logger logger = ApiModule.get().logger();
        String message = "(%s) -- %s => %d -- %fms".formatted(ctx.ip(), ctx.path(), ctx.statusCode(), time);
        if (ctx.statusCode() < 300) {
            logger.info(message);
            return;
        }
        if (ctx.statusCode() < 500) {
            logger.warn(message);
            return;
        }
        logger.error(message + ctx.result());
    }

    public void commonConfig(JavalinConfig config) {
        config.router.treatMultipleSlashesAsSingleSlash = true;
        config.router.ignoreTrailingSlashes = true;
        config.showJavalinBanner = false;
        if (this.cors)
            config.bundledPlugins.enableCors((cors -> cors.addRule(CorsRule::anyHost)));
        config.requestLogger.http(ApiConfig::logger);
    }

    public int getPort() {
        return this.port;
    }
}
