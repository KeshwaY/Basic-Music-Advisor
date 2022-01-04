package advisor.core;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public interface ServerFactory {

    HttpServer createServer(int port) throws IOException;

}
