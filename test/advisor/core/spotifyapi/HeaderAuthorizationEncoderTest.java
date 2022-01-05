package advisor.core.spotifyapi;

import advisor.core.spotifyapi.abstraction.Encoder;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.*;

class HeaderAuthorizationEncoderTest {

    @Test
    void itShouldEncodeString() {
        // given
        String clientId = "123";
        String clientSecret = "TEST";
        String expected = "Basic " + Base64.getEncoder().encodeToString(
                String.format("%s:%s", clientId, clientSecret).getBytes(StandardCharsets.UTF_8)
        );

        Encoder encoder = new HeaderAuthorizationEncoder();

        // when
        String result = encoder.encode(clientId, clientSecret);

        // then
        assertThat(result).isEqualTo(expected);
    }
}