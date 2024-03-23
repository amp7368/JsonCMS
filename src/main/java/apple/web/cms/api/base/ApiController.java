package apple.web.cms.api.base;

import am.ik.yavi.core.ConstraintViolation;
import am.ik.yavi.core.ConstraintViolations;
import apple.web.cms.api.base.serialize.InstantGsonSerializing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public abstract class ApiController {

    private final String basePath;

    public ApiController(String basePath) {
        this.basePath = version() + basePath;
    }

    public ApiController() {
        this.basePath = version();
    }

    public static Gson apiGson() {
        return new GsonBuilder().registerTypeAdapter(Instant.class, new InstantGsonSerializing()).create();
    }

    protected String version() {
        return "/v1";
    }

    protected String path(String subPath) {
        return this.basePath + subPath;
    }

    public abstract void register(Javalin app);

    protected String stringify(Object obj) {
        return apiGson().toJson(obj);
    }

    protected void checkErrors(Context ctx, ConstraintViolations errors) {
        if (!errors.isEmpty()) {
            Map<String, String> messages = new HashMap<>();
            for (ConstraintViolation error : errors)
                messages.put(error.name(), error.message());
            messages.put("value", ctx.body());
            throw new BadRequestResponse("Bad request", messages);
        }
    }

    protected <T> T checkBodyAndGet(Context ctx, Class<T> type) {
        String body = ctx.body();
        if (body.isBlank()) throw new BadRequestResponse("Request body not found");
        try {
            return ApiController.apiGson().fromJson(body, type);
        } catch (JsonSyntaxException exception) {
            throw new BadRequestResponse("Syntax error in JSON", Map.of("cause", exception.getMessage(), "value", body));
        }
    }
}
