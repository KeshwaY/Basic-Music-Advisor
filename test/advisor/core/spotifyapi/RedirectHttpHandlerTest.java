package advisor.core.spotifyapi;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RedirectHttpHandlerTest {

    @Mock private HttpExchange exchange;
    private AutoCloseable closeable;

    private RedirectHttpHandler underTest;
    private String code = null;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new RedirectHttpHandler(authCode -> code = authCode);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void itShouldHandleRedirect() throws IOException {
        // given
        String code = "123";
        String response = "Got the code. Return back to your program.";

        // when
        when(exchange.getRequestURI()).thenReturn(URI.create("http://localhost:8080?code=" + code));
        when(exchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());

        underTest.handle(exchange);
        // then
        verify(exchange).sendResponseHeaders(200, response.length());
        assertThat(this.code).isEqualTo(code);
    }

    @ParameterizedTest
    @ValueSource(strings = {"code=", "asd&", "test"})
    void itShouldFailHandlingRedirect(String query) throws IOException {
        // given
        String response = "Authorization code not found. Try again.";

        // when
        when(exchange.getRequestURI()).thenReturn(URI.create("http://localhost:8080?" + query));
        when(exchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());

        underTest.handle(exchange);
        // then
        verify(exchange).sendResponseHeaders(400, response.length());
        assertThat(this.code).isEqualTo(null);
    }

}