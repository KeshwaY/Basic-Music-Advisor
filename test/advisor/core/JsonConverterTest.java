package advisor.core;

import advisor.core.spotifyapi.JsonConverter;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class JsonConverterTest {

    private JsonConverter converter = new JsonConverter();

    @Test
    void itShouldConvertToJson() {
        // given
        String json = "{\"key\":\"value\"}";

        // when
        JsonObject jsonObject = converter.convert(json);

        // then
        assertThat(jsonObject.get("key").getAsString())
                .isEqualTo("value");
    }
}