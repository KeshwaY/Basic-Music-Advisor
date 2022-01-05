package advisor.core;

import advisor.core.abstraction.*;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class AdvisorImpl implements Advisor {

    private final ServerFactory serverFactory;
    private final ClientFactory clientFactory;

    private RequestSender sender;
    private TokenManager tokenManager;
    private ServerHandler serverHandler;

    public AdvisorImpl() {
        this.serverFactory = new ServerFactoryImpl();
        this.clientFactory = new ClientFactoryImpl();
    }

    public TokenManager getTokenManager() {
        return tokenManager;
    }

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public ServerHandler getServerHandler() {
        return serverHandler;
    }

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void initialize(int serverPort, String apiEndPoint, String clientId, String clientSecret, String redirectURI) throws IOException {
        sender = new RequestSenderImpl(clientFactory.createClient());
        tokenManager = new TokenManagerImpl(new AuthenticatorImpl(sender, apiEndPoint, clientId, clientSecret, redirectURI));
        serverHandler = new ServerHandlerImpl(serverFactory.createServer(serverPort));

        serverHandler.initServerContext();
    }

    @Override
    public String createTokenForUser(UUID uuid) throws IOException, InterruptedException {
        serverHandler.startServer();
        String userCode = serverHandler.getNewUserCode(1L, TimeUnit.MINUTES);
        System.out.println(userCode);
        String token = tokenManager.createTokenForUser(uuid, userCode);
        serverHandler.stopServer();
        return token;
    }

    @Override
    public boolean isUserAuthorized(UUID uuid) {
        return tokenManager.isTokenCreatedForUser(uuid);
    }

}
