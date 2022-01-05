package advisor.core;

import advisor.core.abstraction.ServerHandler;
import advisor.core.spotifyapi.RedirectHttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.util.concurrent.*;

public class ServerHandlerImpl implements ServerHandler {

    private final HttpServer server;

    private volatile String newUserCode;

    private final ExecutorService executorService;

    public ServerHandlerImpl(HttpServer server) {
        this.server = server;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void initServerContext() {
        server.createContext("/", new RedirectHttpHandler((code -> newUserCode = code)));
    }

    @Override
    public void startServer() {
        server.start();
    }

    @Override
    public void stopServer() {
        newUserCode = null;
        server.stop(1);
    }

    @Override
    public String getNewUserCode(long time, TimeUnit unit) {
        Future<String> f = executorService.submit(() -> {
            while (newUserCode == null) {
                Thread.onSpinWait();
            }
            return newUserCode;
        });
        try {
            f.get(time, unit);
        } catch (InterruptedException | TimeoutException | ExecutionException ignored) {
            return null;
        }
        return newUserCode;
    }

}
