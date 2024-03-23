package apple.web.cms.model.auth.authentication;

import apple.web.cms.model.auth.identity.DUser;
import io.ebean.Model;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "auth_token")
public class DAuthToken extends Model {

    public static final int TOKEN_BYTES = 32;
    private static final Duration TIME_TO_EXPIRE = Duration.ofHours(1);

    @Id
    @Column(columnDefinition = "char(32)")
    private String token;
    @Column
    private Timestamp lastUsed;
    @ManyToOne
    private DUser user;

    public DAuthToken(DUser user, byte[] tokenBytes, Timestamp lastUsed) {
        this.user = user;
        this.token = new String(tokenBytes);
        this.lastUsed = lastUsed;
    }

    @NotNull
    public static String decodeUrlToken(@NotNull String token) {
        return new String(Base64.getUrlDecoder().decode(token.getBytes(StandardCharsets.ISO_8859_1)));
    }

    private static String encodeUrlToken(String token) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token.getBytes());
    }

    public boolean isExpired() {
        return getExpiration().isBefore(Instant.now());
    }

    public String getUrlToken() {
        return encodeUrlToken(this.token);
    }

    public Instant getExpiration() {
        return this.lastUsed.toInstant().plus(TIME_TO_EXPIRE);
    }

    public DUser getUser() {
        return user;
    }
}
