package advisor.core;

import advisor.core.abstraction.*;
import advisor.core.components.Album;
import advisor.core.components.Category;
import advisor.core.components.CategoryPlaylist;
import advisor.core.components.Playlist;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class AdvisorImpl implements Advisor {

    private final ServerFactory serverFactory;
    private final ClientFactory clientFactory;

    private RequestSender sender;
    private TokenManager tokenManager;
    private ServerHandler serverHandler;
    private ComponentProvider componentProvider;

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

    public ComponentProvider getComponentProvider() {
        return componentProvider;
    }

    public void setComponentProvider(ComponentProvider componentProvider) {
        this.componentProvider = componentProvider;
    }

    @Override
    public void initialize(int serverPort, String apiEndPoint, String resourceURI, String clientId, String clientSecret, String redirectURI) throws IOException {
        sender = new RequestSenderImpl(clientFactory.createClient());
        tokenManager = new TokenManagerImpl(new AuthenticatorImpl(sender, apiEndPoint, clientId, clientSecret, redirectURI));
        serverHandler = new ServerHandlerImpl(serverFactory.createServer(serverPort));
        componentProvider = new ComponentProviderImpl(sender, resourceURI);

        serverHandler.initServerContext();
    }

    @Override
    public String createTokenForUser(UUID uuid) throws IOException, InterruptedException {
        serverHandler.startServer();
        System.out.println("waiting for code...");
        String userCode = serverHandler.getNewUserCode(1L, TimeUnit.MINUTES);
        if (userCode == null) {
            throw new IOException();
        }
        System.out.println("code received");
        System.out.println("making http request for access_token...");
        String token = tokenManager.createTokenForUser(uuid, userCode);
        serverHandler.stopServer();
        return token;
    }

    @Override
    public boolean isUserAuthorized(UUID uuid) {
        return tokenManager.isTokenCreatedForUser(uuid);
    }

    @Override
    public List<Album> getNewReleases(UUID uuid) throws IOException, InterruptedException {
        String token = tokenManager.getUserToken(uuid);
        return componentProvider.getNewReleases(token);
    }

    @Override
    public List<Playlist> getFeatured(UUID uuid) throws IOException, InterruptedException {
        String token = tokenManager.getUserToken(uuid);
       return componentProvider.getFeatured(token);
    }

    @Override
    public List<Category> getCategories(UUID uuid) throws IOException, InterruptedException {
        String token = tokenManager.getUserToken(uuid);
        return componentProvider.getCategories(token);
    }

    @Override
    public List<CategoryPlaylist> getPlaylistsForCategory(UUID uuid, String categoryId) throws IOException, InterruptedException {
        String token = tokenManager.getUserToken(uuid);
        return componentProvider.getPlaylistsForCategory(token, categoryId);
    }
}
