package apple.web.cms.model.raw;

import apple.web.cms.service.FlattenJson;
import com.google.gson.JsonObject;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "json_document")
public class DJsonDocument extends Model {

    @Id
    private UUID id;
    @WhenCreated
    private Timestamp createdAt;
    @WhenModified
    private Timestamp modifiedAt;

    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private String currentJson;
    @Column
    private List<DJsonField> fields;

    public Map<String, DJsonField> currentFields() {
        return JsonApi.queryCurrentFields(this.id);
    }

    public JsonObject jsonObj() {
        return FlattenJson.gson().fromJson(currentJson, JsonObject.class);
    }

    public String json() {
        return currentJson;
    }
}
