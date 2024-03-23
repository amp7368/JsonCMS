package apple.web.cms.model.auth;

import apple.web.cms.JsonCMSConfig;
import apple.web.cms.model.auth.authentication.DAuthToken;
import apple.web.cms.model.auth.identity.DUser;
import com.password4j.Password;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import org.jetbrains.annotations.NotNull;

public class ApiSecurity {

    public static String hashPassword(String password) {
        return Password.hash(password)
            .addRandomSalt()
            .addPepper(JsonCMSConfig.get().pepper)
            .withArgon2()
            .getResult();
    }

    @NotNull
    public static DUser verifyRequestIdentity(Context ctx) {
        String tokenHeader = ctx.header("Authorization");
        if (tokenHeader == null) throw new ForbiddenResponse("'Authorization' header not provided");
        if (tokenHeader.length() <= "Bearer ".length()) throw new ForbiddenResponse("'Authorization' header is not in Bearer format");
        tokenHeader = tokenHeader.substring("Bearer ".length());

        DAuthToken token = AuthQuery.findToken(tokenHeader);
        if (token == null) throw new ForbiddenResponse("Invalid Authorization token");
        if (token.isExpired()) throw new ForbiddenResponse("Authorization token is expired");

        DUser identity = token.getUser();
        if (identity == null) throw new ForbiddenResponse("Token has no identity?");

        return identity;
    }


    public static boolean checkPassword(String hashed, String input) {
        return Password.check(input, hashed).addPepper(JsonCMSConfig.get().pepper).withArgon2();
    }

    public static void handle(@NotNull Context ctx) throws Exception {
        if (ctx.routeRoles().isEmpty()) return;

        DUser identity = verifyRequestIdentity(ctx);
        if (!identity.hasPermissions(ctx.routeRoles())) {
            throw new ForbiddenResponse("Insufficient permissions");
        }
    }

}
