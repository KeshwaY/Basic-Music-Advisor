package advisor.core;

import java.net.http.HttpClient;

public class ClientFactoryImpl implements ClientFactory {

    @Override
    public HttpClient createClient() {
        return HttpClient.newHttpClient();
    }

}
