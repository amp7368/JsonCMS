package apple.web.cms.model.auth.identity;

import apple.web.cms.model.auth.ApiSecurity;
import io.ebean.Model;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_basic_credentials")
public class DUserBasicCredentials extends Model {

    @Id
    private UUID id;
    @Column
    @OneToOne(optional = false)
    private DUser user;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public DUserBasicCredentials(DUser user, String username, String password) {
        this.user = user;
        this.username = username;
        this.password = ApiSecurity.hashPassword(password);
    }

    public DUser getUser() {
        return user;
    }

    public boolean isValidPassword(String password) {
        return ApiSecurity.checkPassword(this.password, password);
    }
}
