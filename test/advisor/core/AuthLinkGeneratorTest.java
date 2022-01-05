package advisor.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AuthLinkGeneratorTest {

    @Test
    void itShouldGenerateLink() {
        // given
        String clientId = "123";
        String redirectURI = "test";

        // when
        String link = AuthLinkGenerator.generate(clientId, redirectURI);

        // then
        assertThat(link)
                .isEqualTo(String.format("https://accounts.spotify.com/authorize?client_id=%s&redirect_uri=%s", clientId, redirectURI));
    }

}