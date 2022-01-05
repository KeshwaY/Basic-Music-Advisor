package advisor.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TokenManagerImplTest {

    @Mock private Authenticator authenticator;
    @Mock private HttpResponse<String> response;
    private AutoCloseable closeable;

    private TokenManager underTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new TokenManagerImpl(authenticator);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void itShouldCreateAndStoreTokenForUserWithGivenUUID() throws IOException, InterruptedException {
        // given
        UUID uuid = UUID.randomUUID();
        String code = "test";
        String redirectURI = "http://localhost:8080";

        given(authenticator.authorizeUser(code, redirectURI))
                .willReturn(response);


        given(response.body()).willReturn("{\n" +
                "   \"access_token\": \"NgCXRK...MzYjw\",\n" +
                "   \"token_type\": \"Bearer\",\n" +
                "   \"scope\": \"user-read-private user-read-email\",\n" +
                "   \"expires_in\": 3600,\n" +
                "   \"refresh_token\": \"NgAagA...Um_SHo\"\n" +
                "}");

        // when
        String token = underTest.createTokenForUser(uuid, code, redirectURI);

        // then
        verify(authenticator).authorizeUser(code, redirectURI);
        assertThat(token).isEqualTo("NgCXRK...MzYjw");
    }

    @Test
    void itShouldReturnNonExpiredTokenForUserWithUUID() throws IOException, InterruptedException {
        // given
        UUID uuid = UUID.randomUUID();
        String code = "test";
        String redirectURI = "http://localhost:8080";

        given(authenticator.authorizeUser(code, redirectURI))
                .willReturn(response);


        given(response.body()).willReturn("{\n" +
                "   \"access_token\": \"NgCXRK...MzYjw\",\n" +
                "   \"token_type\": \"Bearer\",\n" +
                "   \"scope\": \"user-read-private user-read-email\",\n" +
                "   \"expires_in\": 3600,\n" +
                "   \"refresh_token\": \"NgAagA...Um_SHo\"\n" +
                "}");

        // when
        underTest.createTokenForUser(uuid, code, redirectURI);
        String token = underTest.getUserToken(uuid);

        // then
        assertThat(token).isEqualTo("NgCXRK...MzYjw");
    }

    @Test
    void itShouldAskForNewTokenIfCurrentExpired() throws IOException, InterruptedException {
        // given
        UUID uuid = UUID.randomUUID();
        String refreshToken = "NgAagA...Um_SHo";

        given(authenticator.authorizeUser(any(), any()))
                .willReturn(response);
        given(response.body()).willReturn("{\n" +
                "   \"access_token\": \"NgCXRK...MzYjw\",\n" +
                "   \"token_type\": \"Bearer\",\n" +
                "   \"scope\": \"user-read-private user-read-email\",\n" +
                "   \"expires_in\": -1,\n" +
                "   \"refresh_token\": \"NgAagA...Um_SHo\"\n" +
                "}");

        // when
        underTest.createTokenForUser(uuid, any(), any());
        when(authenticator.refreshToken(refreshToken))
                .thenReturn(response);
        when(response.body())
                .thenReturn("{\n" +
                        "   \"access_token\": \"NgA6ZcYI...ixn8bUQ\",\n" +
                        "   \"token_type\": \"Bearer\",\n" +
                        "   \"scope\": \"user-read-private user-read-email\",\n" +
                        "   \"expires_in\": 3600\n" +
                        "}");
        String token = underTest.getUserToken(uuid);

        // then
        verify(authenticator).refreshToken(refreshToken);
        assertThat(token).isEqualTo("NgA6ZcYI...ixn8bUQ");
    }
}