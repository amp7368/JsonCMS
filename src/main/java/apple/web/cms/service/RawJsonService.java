package apple.web.cms.service;

import apple.web.cms.model.raw.DJsonDocument;
import apple.web.cms.model.raw.DJsonField;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RawJsonService {

    public void update(DJsonDocument prev, JsonObject next) {
        Map<String, DJsonField> flattenedPrev = prev.currentFields();
        Map<String, String> flattenedNext = FlattenJson.flatten(next);
        update(flattenedPrev, flattenedNext);
    }

    public Set<DJsonField> update(Map<String, DJsonField> flattenedPrev, Map<String, String> flattenedNext) {
        Set<String> deleted = new HashSet<>(flattenedPrev.keySet());
        Set<DJsonField> updates = new HashSet<>();
        for (String path : flattenedNext.keySet()) {
            deleted.remove(path);

            DJsonField prev = flattenedPrev.get(path);
            String next = flattenedNext.get(path);

            if (prev == null) {
                int version = 1;
                updates.add(new DJsonField(path, next, version));
            } else if (!next.equals(prev.getJson())) {
                int version = prev.getVersion() + 1;
                updates.add(new DJsonField(path, next, version));
            }
        }
        for (String path : deleted) {
            DJsonField prev = flattenedPrev.get(path);
            int version = prev.getVersion() + 1;
            updates.add(new DJsonField(path, null, version));
        }
        return updates;
    }
}
