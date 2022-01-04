package advisor.core;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

public interface RequestSender {

    HttpResponse<String> sendPostRequest(URI uri, List<String> headers, String bodyParameters) throws IOException, InterruptedException;

}
