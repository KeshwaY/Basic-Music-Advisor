package advisor.core.abstraction;

import java.io.IOException;
import java.util.UUID;

public interface Advisor {
    void initialize(int serverPort, String apiEndPoint, String clientId, String clientSecret, String redirectURI) throws IOException;
    String createTokenForUser(UUID uuid) throws IOException, InterruptedException;
    boolean isUserAuthorized(UUID uuid);
}
