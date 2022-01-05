package advisor.core.abstraction;


import java.util.concurrent.TimeUnit;

public interface ServerHandler {

    void initServerContext();
    void startServer();
    void stopServer();
    String getNewUserCode(long time, TimeUnit unit);

}
