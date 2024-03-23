package apple.web.cms.model.raw;

import io.ebean.Model;
import io.ebean.annotation.Index;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.jetbrains.annotations.Nullable;

@Entity
@Table(name = "json_field")
public class DJsonField extends Model {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String path;
    @Index
    @Column(nullable = false)
    private int version;
    @Nullable
    @Column
    private String json;

    public DJsonField(String path, @Nullable String json, int version) {
        this.path = path;
        this.json = json;
        this.version = version;
    }

    @Nullable
    public String getJson() {
        return json;
    }

    public int getVersion() {
        return version;
    }
}
