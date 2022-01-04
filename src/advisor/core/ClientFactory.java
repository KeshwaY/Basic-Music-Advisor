package advisor.core;

import java.net.http.HttpClient;

public interface ClientFactory {

    HttpClient createClient();

}
