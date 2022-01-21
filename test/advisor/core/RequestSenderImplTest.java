package advisor.core;

import advisor.core.abstraction.RequestSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RequestSenderImplTest {

    @Mock private HttpClient client;
    @Mock private HttpResponse<Object> response;
    private AutoCloseable closeable;

    private RequestSender underTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new RequestSenderImpl(client);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void itShouldSendPostRequest() throws IOException, InterruptedException {
        // given
        given(client.send(any(), any()))
                .willReturn(response);
        URI uri = URI.create("http://localhost:8080");
        List<String> headersList = List.of(
                "Content-Type", "application/x-www-form-urlencoded"
        );

        // when
        underTest.sendPostRequest(uri, headersList, "body_parameter=test");

        // then
        verify(client).send(HttpRequest.newBuilder()
                        .uri(uri)
                        .headers(headersList.toArray(new String[0]))
                        .POST(HttpRequest.BodyPublishers.ofString("body_parameter=test"))
                        .build(),
                HttpResponse.BodyHandlers.ofString());
    }


    @Test
    void itShouldFailSendingPostRequest() throws IOException, InterruptedException {
        // given
        given(client.send(any(), any()))
                .willReturn(response);
        URI uri = URI.create("");
        List<String> headersList = List.of(
                "test", "test"
        );

        // when
        // then
        assertThatThrownBy(() -> underTest.sendPostRequest(uri, headersList, ""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void itShouldSendGetRequest() throws IOException, InterruptedException {
        // given
        given(client.send(any(), any()))
                .willReturn(response);
        URI uri = URI.create("http://localhost:8080");
        List<String> headersList = List.of(
                "Content-Type", "application/x-www-form-urlencoded"
        );

        // when
        underTest.sendGetRequestWithHeaders(uri, headersList);

        // then
        verify(client).send(HttpRequest.newBuilder()
                        .uri(uri)
                        .headers(headersList.toArray(new String[0]))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void itShouldFailSendingGetRequest() throws IOException, InterruptedException {
        // given
        given(client.send(any(), any()))
                .willReturn(response);
        URI uri = URI.create("");
        List<String> headersList = List.of(
                "test", "test"
        );

        // when
        // then
        assertThatThrownBy(() -> underTest.sendGetRequestWithHeaders(uri, headersList))
                .isInstanceOf(IllegalArgumentException.class);
    }
}