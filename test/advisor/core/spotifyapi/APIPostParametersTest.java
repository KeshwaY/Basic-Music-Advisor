package advisor.core.spotifyapi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;

class APIPostParametersTest {

    @Mock private HttpClient client;
    @Mock private HttpResponse<Object> response;
    private AutoCloseable closeable;

    private BodyPublishable underTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void itShouldBuildParametersForAuthorization() throws IOException, InterruptedException {
        // given
        underTest = APIPostParameters.builder()
                .grantType(GrantType.AUTHORIZATION_CODE)
                .userCode("1234")
                .redirectURI("http://localhost:8080")
                .build();

        // when
        when(client.send(any(), any()))
                .thenReturn(response);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn(underTest.formatForBodyPublisher());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080"))
                .POST(HttpRequest.BodyPublishers.ofString(underTest.formatForBodyPublisher()))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo(underTest.formatForBodyPublisher());
    }

    @Test
    void itShouldBuildParametersForRefresh() throws IOException, InterruptedException {
        // given
        underTest = APIPostParameters.builder()
                .grantType(GrantType.REFRESH_TOKEN)
                .tokenToRefresh("123")
                .build();

        // when
        when(client.send(any(), any()))
                .thenReturn(response);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn(underTest.formatForBodyPublisher());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080"))
                .POST(HttpRequest.BodyPublishers.ofString(underTest.formatForBodyPublisher()))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo(underTest.formatForBodyPublisher());
    }

    @Test
    void itShouldFailBuilding() {
        assertThatThrownBy(() -> APIPostParameters.builder().build())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Grant type have to be specified!");
    }

    static class AuthorizationArgumentProvider implements ArgumentsProvider {

        static final GrantType grantType = GrantType.AUTHORIZATION_CODE;

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(grantType, null, null),
                    Arguments.of(grantType, null, "test"),
                    Arguments.of(grantType, "test", null),
                    Arguments.of(grantType, "test", " "),
                    Arguments.of(grantType, "", ""),
                    Arguments.of(grantType, " ", "")
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(AuthorizationArgumentProvider.class)
    void itShouldFailBuildingAuthorizationParameters(GrantType grantType, String userCode, String redirectURI) {

        assertThatThrownBy(() -> APIPostParameters.builder()
                .redirectURI(redirectURI)
                .grantType(grantType)
                .userCode(userCode)
                .build()).isInstanceOf(IllegalStateException.class).hasMessage("When using authorization_code you need to provide Builder with a valid userCode and valid redirectURI!");

    }

    static class RefreshArgumentProvider implements ArgumentsProvider {

        static final GrantType grantType = GrantType.REFRESH_TOKEN;

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(grantType, null),
                    Arguments.of(grantType, ""),
                    Arguments.of(grantType, " ")
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(RefreshArgumentProvider.class)
    void itShouldFailBuildingRefreshParameters(GrantType grantType, String code) {

        assertThatThrownBy(() -> APIPostParameters.builder()
                .grantType(grantType)
                .userCode(code)
                .build()).isInstanceOf(IllegalStateException.class).hasMessage("When using refresh_token you need to provide Builder with a valid userToken!");

    }

    @Test
    void itShouldReturnNullFormat() {
        // given


    }
}