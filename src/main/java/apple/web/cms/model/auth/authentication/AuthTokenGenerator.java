package apple.web.cms.model.auth.authentication;

import apple.web.cms.model.auth.identity.DUser;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

public class AuthTokenGenerator {

    private static final SecureRandom random = new SecureRandom();

    @NotNull
    public static DAuthToken create(DUser user) {
        Timestamp lastUsed = Timestamp.from(Instant.now());
        byte[] tokenBytes = new byte[DAuthToken.TOKEN_BYTES];
        random.nextBytes(tokenBytes);
        DAuthToken token = new DAuthToken(user, tokenBytes, lastUsed);
        token.save();
        return token;
    }
}
