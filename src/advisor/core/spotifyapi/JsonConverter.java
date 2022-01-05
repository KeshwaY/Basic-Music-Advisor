package advisor.core.spotifyapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonConverter {

    public JsonObject convert(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

}
