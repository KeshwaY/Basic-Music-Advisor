package advisor.core.spotifyapi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RedirectHttpHandler implements HttpHandler {

    public interface Observer {
        void codeReceived(String code);
    }

    private final Observer observer;

    public RedirectHttpHandler(Observer observers) {
        this.observer = observers;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (validateAndGetCode(query)) {
            setResponseHeadersAndWriteBody(exchange, 200, "Got the code. Return back to your program.");
        } else {
            setResponseHeadersAndWriteBody(exchange, 400, "Authorization code not found. Try again.");
        }
    }

    private boolean validateAndGetCode(String query) {
        if (query == null || !query.contains("code=")) {
            return false;
        }
        return getCodeFromQuery(query);
    }

    private boolean getCodeFromQuery(String query) {
        String code = query.substring(query.indexOf("=") + 1, (query.indexOf("&") > 0 ? query.indexOf("&") : query.length()));
        if (code.equals("")) {
            return false;
        }
        this.observer.codeReceived(code);
        return true;
    }

    private void setResponseHeadersAndWriteBody(HttpExchange exchange, int code, String response) throws IOException {
        exchange.sendResponseHeaders(code, response.length());
        exchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
        exchange.getResponseBody().close();
    }
}
