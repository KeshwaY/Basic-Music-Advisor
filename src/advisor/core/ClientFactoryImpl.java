package advisor.core;

import advisor.core.abstraction.ClientFactory;

import java.net.http.HttpClient;

public class ClientFactoryImpl implements ClientFactory {

    @Override
    public HttpClient createClient() {
        return HttpClient.newHttpClient();
    }

}
