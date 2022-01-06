package advisor.core;

import advisor.core.abstraction.RequestSender;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class RequestSenderImpl implements RequestSender {

    private HttpClient client;

    public RequestSenderImpl(HttpClient client) {
        this.client = client;
    }

    @Override
    public HttpResponse<String> sendPostRequest(URI uri, List<String> headers, String bodyParameters) throws IOException, InterruptedException {
        return client.send(
                createPostRequest(uri, headers, bodyParameters), HttpResponse.BodyHandlers.ofString()
        );
    }

    @Override
    public HttpResponse<String> sendGetRequestWithHeaders(URI uri, List<String> headers) throws IOException, InterruptedException {
        return client.send(
                createGetRequestWithHeaders(uri, headers), HttpResponse.BodyHandlers.ofString()
        );
    }

    private HttpRequest createPostRequest(URI uri, List<String> headers, String bodyParameters) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .headers(headers.toArray(new String[0]))
                .POST(HttpRequest.BodyPublishers.ofString(bodyParameters))
                .build();
    }

    private HttpRequest createGetRequestWithHeaders(URI uri, List<String> headers) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .headers(headers.toArray(new String[0]))
                .GET()
                .build();
    }

}
