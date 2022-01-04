package advisor.core.spotifyapi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
    void formatForBodyPublisher() throws IOException, InterruptedException {
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
}