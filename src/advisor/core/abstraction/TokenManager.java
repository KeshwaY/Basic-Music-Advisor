package advisor.core.abstraction;

import java.io.IOException;
import java.util.UUID;

public interface TokenManager {

    String createTokenForUser(UUID user, String code) throws IOException, InterruptedException;
    String getUserToken(UUID userUUID) throws IOException, InterruptedException;
    boolean isTokenCreatedForUser(UUID userUUID);
}
