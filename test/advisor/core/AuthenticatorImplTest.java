package advisor.core;

import advisor.core.spotifyapi.APIPostParameters;
import advisor.core.spotifyapi.GrantType;
import advisor.core.spotifyapi.HeaderAuthorizationEncoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.mockito.Mockito.verify;

class AuthenticatorImplTest {

    @Mock private RequestSender requestSender;
    private AutoCloseable closeable;

    private AuthenticatorImpl underTest;
    private String apiEndPoint;
    private String clientId;
    private String clientSecret;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        clientId = "123";
        clientSecret = "test";
        apiEndPoint = "http://localhost:8081";
        underTest = new AuthenticatorImpl(requestSender, apiEndPoint, clientId, clientSecret);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void itShouldSendAuthorizationRequest() throws IOException, InterruptedException {
        // given
        String code = "123456789";
        String redirectURI = "http://localhost:8080";

        List<String> neededHeaders = List.of(
                "Content-Type", "application/x-www-form-urlencoded",
                "Authorization", new HeaderAuthorizationEncoder().encode(clientId, clientSecret)
        );

        // when
        underTest.authorizeUser(code, redirectURI);

        // then
        verify(requestSender).sendPostRequest(
                URI.create("https://accounts.spotify.com/api/token"),
                neededHeaders,
                APIPostParameters.builder()
                        .grantType(GrantType.AUTHORIZATION_CODE)
                        .userCode(code)
                        .redirectURI(redirectURI)
                        .build()
                        .formatForBodyPublisher()
        );
    }

    @Test
    void itShouldSendRefreshRequest() throws IOException, InterruptedException {
        // given
        String token = "987654321";

        List<String> neededHeaders = List.of(
                "Content-Type", "application/x-www-form-urlencoded",
                "Authorization", new HeaderAuthorizationEncoder().encode(clientId, clientSecret)
        );

        // when
        underTest.refreshToken(token);

        // then
        verify(requestSender).sendPostRequest(
                URI.create("https://accounts.spotify.com/api/token"),
                neededHeaders,
                APIPostParameters.builder()
                        .grantType(GrantType.REFRESH_TOKEN)
                        .tokenToRefresh(token)
                        .build()
                        .formatForBodyPublisher()
        );
    }
}