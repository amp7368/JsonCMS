package apple.web.cms.api.endpoint.json;

import apple.web.cms.api.base.ApiController;
import apple.web.cms.model.raw.JsonApi;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class JsonController extends ApiController {

    public JsonController() {
        super("/j");
    }

    @Override
    public void register(Javalin app) {
        app.get(this.path("/j/*"), this::getJson);
    }

    private void getJson(Context ctx) {
        String emptyPath = path("/j/");
        String path = ctx.path().substring(emptyPath.length())
            .replaceAll("/*$", "")
            .replaceAll("^/*", "");
        if (path.isEmpty()) throw new NotFoundResponse();
        System.out.println(path);
        String json = JsonApi.getJson(path);
        if (json == null) throw new NotFoundResponse();
        ctx.result(json);
    }
}
