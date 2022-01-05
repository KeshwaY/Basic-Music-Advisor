package advisor.core;

import advisor.core.abstraction.Authenticator;
import advisor.core.abstraction.TokenManager;
import advisor.core.spotifyapi.abstraction.AccessToken;
import advisor.core.spotifyapi.BearerToken;
import advisor.core.spotifyapi.JsonConverter;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenManagerImpl implements TokenManager {

    private final Authenticator authenticator;
    private final JsonConverter jsonConverter;

    private final Map<UUID, AccessToken> tokenMap;

    public TokenManagerImpl(Authenticator authenticator) {
        this.authenticator = authenticator;
        this.jsonConverter = new JsonConverter();
        tokenMap = new HashMap<>();
    }

    @Override
    public String createTokenForUser(UUID uuid, String code) throws IOException, InterruptedException {
        HttpResponse<String> response = authenticator.authorizeUser(code);
        return getAccessTokenFromResponseAndAddToMap(uuid, response).getToken();
    }

    @Override
    public String getUserToken(UUID userUUID) throws IOException, InterruptedException {
        AccessToken token = tokenMap.get(userUUID);
        if (validateIfTokenIsExpired(token)) {
            String refreshToken = token.getRefreshToken();
            HttpResponse<String> response = authenticator.refreshToken(refreshToken);
            token = getAccessTokenFromResponseAndAddToMap(userUUID, response);
        }
        return token.getToken();
    }

    private AccessToken getAccessTokenFromResponseAndAddToMap(UUID uuid, HttpResponse<String> response) {
        JsonObject token = jsonConverter.convert(response.body());
        AccessToken accessToken = new BearerToken();
        accessToken.setPlainJson(token);
        tokenMap.put(uuid, accessToken);
        return accessToken;
    }

    private boolean validateIfTokenIsExpired(AccessToken token) {
        return token.expirationDate().getTime().before(Calendar.getInstance().getTime());
    }

}
