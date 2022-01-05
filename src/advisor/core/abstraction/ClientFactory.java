package advisor.core.abstraction;

import java.net.http.HttpClient;

public interface ClientFactory {

    HttpClient createClient();

}
