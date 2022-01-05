package advisor.core.spotifyapi;

import advisor.core.spotifyapi.abstraction.AccessToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;

class BearerTokenTest {

    private AccessToken underTest;

    @BeforeEach
    void setUp() {
        underTest = new BearerToken();
    }

    @Test
    void itShouldSetPlainJsonFromJsonWithRefreshToken() {
        // given
        String jsonResponse = "{\n" +
                "   \"access_token\": \"NgCXRK...MzYjw\",\n" +
                "   \"token_type\": \"Bearer\",\n" +
                "   \"scope\": \"user-read-private user-read-email\",\n" +
                "   \"expires_in\": 3600,\n" +
                "   \"refresh_token\": \"NgAagA...Um_SHo\"\n" +
                "}";
        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.SECOND, 3600);
        JsonObject givenObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // when
        underTest.setPlainJson(givenObject);

        // then
        assertThat(underTest.getToken())
                .isEqualTo(givenObject.get("access_token").getAsString());
        assertThat(underTest.getRefreshToken())
                .isEqualTo(givenObject.get("refresh_token").getAsString());
        assertThat(underTest.getType())
                .isEqualTo(givenObject.get("token_type").getAsString());
        assertThat(underTest.expirationDate().get(Calendar.MINUTE))
                .isEqualTo(expireDate.get(Calendar.MINUTE));
    }

    @Test
    void itShouldSetPlainJsonFromJsonWithoutRefreshToken() {
        // given
        String jsonResponseWithRefreshToken = "{\n" +
                "   \"access_token\": \"NgCXRK...MzYjw\",\n" +
                "   \"token_type\": \"Bearer\",\n" +
                "   \"scope\": \"user-read-private user-read-email\",\n" +
                "   \"expires_in\": 3600,\n" +
                "   \"refresh_token\": \"NgAagA...Um_SHo\"\n" +
                "}";
        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.SECOND, 3600);
        JsonObject refreshTokenObject = JsonParser.parseString(jsonResponseWithRefreshToken).getAsJsonObject();

        String jsonResponseWithoutRefreshToken = "{\n" +
                "   \"access_token\": \"NgA6ZcYI...ixn8bUQ\",\n" +
                "   \"token_type\": \"Bearer\",\n" +
                "   \"scope\": \"user-read-private user-read-email\",\n" +
                "   \"expires_in\": 3600\n" +
                "}";
        JsonObject withoutRefreshToken = JsonParser.parseString(jsonResponseWithoutRefreshToken).getAsJsonObject();


        // when
        underTest.setPlainJson(refreshTokenObject);
        underTest.setPlainJson(withoutRefreshToken);

        // then
        assertThat(underTest.getToken())
                .isEqualTo(withoutRefreshToken.get("access_token").getAsString());
        assertThat(underTest.getRefreshToken())
                .isEqualTo(refreshTokenObject.get("refresh_token").getAsString());
        assertThat(underTest.getType())
                .isEqualTo(withoutRefreshToken.get("token_type").getAsString());
        assertThat(underTest.expirationDate().get(Calendar.MINUTE))
                .isEqualTo(expireDate.get(Calendar.MINUTE));
    }

}