package apple.web.cms.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlattenJson extends JsonReader {

    private static final String SEPARATOR = ".";

    public FlattenJson(Reader in) {
        super(in);
    }

    public static Map<String, String> flatten(JsonObject json) {
        return flatten(json, null);
    }

    private static Map<String, String> flatten(JsonObject json, @Nullable String path) {
        Gson gson = gson();
        Map<String, String> flattened = new HashMap<>();
        for (String property : json.keySet()) {
            String fieldPath;
            if (path == null) fieldPath = property;
            else fieldPath = path + SEPARATOR + property;

            JsonElement field = json.get(property);
            if (field.isJsonObject())
                flattened.putAll(flatten(field.getAsJsonObject(), fieldPath));
            else flattened.put(fieldPath, gson.toJson(field));
        }
        return flattened;
    }

    @NotNull
    public static Gson gson() {
        return new Gson();
    }
}
