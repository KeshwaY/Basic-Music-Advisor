package advisor.core;

import java.io.IOException;
import java.util.UUID;

public interface TokenManager {

    String createTokenForUser(UUID user, String code, String redirectURI) throws IOException, InterruptedException;
    String getUserToken(UUID userUUID) throws IOException, InterruptedException;

}
