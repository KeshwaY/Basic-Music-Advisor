package advisor.core;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.*;

class ServerFactoryImplTest {

    private ServerFactory underTest;
    private HttpServer server;

    @BeforeEach
    void setUp() {
        underTest = new ServerFactoryImpl();
    }

    @AfterEach
    void tearDown() {
        server.stop(0);
    }

    @Test
    void itShouldCreateTheSever() throws IOException, InterruptedException {
        // given
        server = underTest.createServer(8080);
        server.start();
        server.createContext("/", (exchange) -> {
           exchange.sendResponseHeaders(200, -1);
        });
        HttpClient client = HttpClient.newHttpClient();

        // when
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        assertThat(response.statusCode())
                .isEqualTo(200);
    }


    @Test
    void itShouldFailCreatingTheServerBecausePortIsAlreadyInUse() throws IOException {
        // given
        server = underTest.createServer(8080);

        // when
        // then
        assertThatThrownBy(() -> underTest.createServer(8080))
                .isInstanceOf(BindException.class);

    }
}