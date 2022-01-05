package advisor.core;

import advisor.core.abstraction.ServerHandler;
import advisor.core.spotifyapi.RedirectHttpHandler;
import com.sun.net.httpserver.HttpServer;
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
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class ServerHandlerImplTest {

    @Mock private HttpServer server;
    private AutoCloseable closeable;

    private ServerHandler underTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new ServerHandlerImpl(server);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void itShouldInitServerContext() {
        // given
        // when
        underTest.initServerContext();

        // then
        verify(server)
                .createContext(any(), any(RedirectHttpHandler.class));
    }

    @Test
    void itShouldStartServer() {
        // given
        // when
        underTest.startServer();

        // then
        verify(server)
                .start();
    }

    @Test
    void itShouldStopServer() {
        // given
        // when
        underTest.stopServer();

        // then
        verify(server)
                .stop(1);
    }

    @Test
    void itShouldReturnNullUserCode() {
        // given
        // when
        String code = underTest.getNewUserCode(1L, TimeUnit.MILLISECONDS);

        // then
        assertThat(code)
                .isEqualTo(null);
    }

    @Test
    void itShouldReturnUserCode() throws IOException, InterruptedException {
        // given
        underTest = new ServerHandlerImpl(new ServerFactoryImpl().createServer(8080));
        String code = "123";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080?code=" + code))
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        // when
        underTest.initServerContext();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        String userCode = underTest.getNewUserCode(10L, TimeUnit.MINUTES);
        underTest.startServer();

        // then
        assertThat(userCode)
                .isEqualTo(code);
    }
}