package apple.web.cms.model.auth.identity;

import apple.web.cms.model.auth.authentication.DAuthToken;
import apple.web.cms.model.auth.permission.DPermission;
import apple.web.cms.model.auth.permission.DRole;
import io.ebean.Model;
import io.javalin.security.RouteRole;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "duser")
public class DUser extends Model {

    @Id
    private UUID id;
    @Column(unique = true, nullable = false)
    private String name;
    @OneToMany
    private List<DAuthToken> activeTokens;
    @Column
    private DRole role;

    public DUser(String name) {
        this.name = name;
    }

    public DRole getRole() {
        return role;
    }

    public void setRole(DRole role) {
        this.role = role;
    }

    public boolean hasPermissions(RouteRole... routeRole) {
        return hasPermissions(List.of(routeRole));
    }

    private Set<DPermission> getPermissions() {
        return Stream.of(getRole())
            .flatMap(r -> r.getPermissions().stream())
            .collect(Collectors.toSet());
    }

    public boolean hasPermissions(Collection<? extends RouteRole> routePermissions) {
        @SuppressWarnings("all") boolean hasAll = this.getPermissions().containsAll(routePermissions);
        return hasAll;
    }

    public String getName() {
        return this.name;
    }
}
