package apple.web.cms.model.auth.permission;

import io.ebean.annotation.DbEnumValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public enum DRole {

    ADMIN("admin", DPermission.values()),
    PUBLIC("public");

    private static final Map<String, DRole> byIds = new HashMap<>();
    private final String name;
    private final List<DPermission> permissions;

    DRole(String name, DPermission... permissions) {
        this.name = name;
        this.permissions = List.of(permissions);
    }

    @Nullable
    public static DRole find(String rawRole) {
        if (byIds.isEmpty())
            for (DRole role : values()) {
                byIds.put(role.name, role);
            }
        return byIds.get(rawRole);
    }

    public List<DPermission> getPermissions() {
        return permissions;
    }

    @DbEnumValue
    public String id() {
        return name;
    }

    public String getName() {
        return name;
    }
}
