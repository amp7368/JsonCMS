package apple.web.cms.api.auth;

import apple.web.cms.api.auth.grant.GrantRequest;
import apple.web.cms.api.auth.signup.SignupRequest;
import apple.web.cms.api.base.ApiController;
import apple.web.cms.model.auth.AuthQuery;
import apple.web.cms.model.auth.authentication.DAuthToken;
import apple.web.cms.model.auth.identity.DUser;
import apple.web.cms.model.auth.permission.DPermission;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.security.BasicAuthCredentials;
import org.jetbrains.annotations.NotNull;

public class AuthController extends ApiController {

    public AuthController() {
        super("/auth");
    }

    @Override
    public void register(Javalin app) {
        app.get(this.path("/login"), this::login);
        app.post(this.path("/signup"), this::signup);
        app.post(this.path("/grant"), this::grant, DPermission.PRIVATE);
    }

    private void grant(Context ctx) {
        GrantRequest request = this.checkBodyAndGet(ctx, GrantRequest.class);
        this.checkErrors(ctx, GrantRequest.VALIDATOR.validator().validate(request));
        DUser user = AuthQuery.grant(request);

        String role = user.getRole().getName();
        String username = user.getName();
        GrantResponse response = new GrantResponse(username, role);
        ctx.json(response).status(HttpStatus.OK);
    }

    private void signup(Context ctx) {
        SignupRequest request = this.checkBodyAndGet(ctx, SignupRequest.class);
        this.checkErrors(ctx, SignupRequest.VALIDATOR.validator().validate(request));

        @NotNull DAuthToken login = AuthQuery.signup(request);
        ctx.json(new AuthResponse(login.getUrlToken(), login.getExpiration()));
    }

    private void login(Context ctx) {
        BasicAuthCredentials credentials = ctx.basicAuthCredentials();
        if (credentials == null) throw new BadRequestResponse("Login not found in 'Authorization' header");
        @NotNull DAuthToken login = AuthQuery.login(credentials.getUsername(), credentials.getPassword());
        ctx.json(new AuthResponse(login.getUrlToken(), login.getExpiration()));
    }
}
