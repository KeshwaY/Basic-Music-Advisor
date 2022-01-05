package advisor.core;

import advisor.core.abstraction.ServerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerFactoryImpl implements ServerFactory {

    @Override
    public HttpServer createServer(int port) throws IOException {
        return HttpServer.create(createInetSocket(port), 0);
    }

    private InetSocketAddress createInetSocket(int port) {
        return new InetSocketAddress(port);
    }

}
