package apple.web.cms.model.raw;

import apple.web.cms.model.raw.query.QDJsonDocument;
import java.util.Map;
import java.util.UUID;

public interface JsonApi {

    static Map<String, DJsonField> queryCurrentFields(UUID id) {
        return null;
    }

    static String getJson(String path) {
        DJsonDocument document = new QDJsonDocument().where()
            .path.ieq(path)
            .findOne();
        if (document == null) return null;

        return document.json();
    }
}
