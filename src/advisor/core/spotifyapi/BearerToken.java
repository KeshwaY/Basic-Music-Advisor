package advisor.core.spotifyapi;

import advisor.core.spotifyapi.abstraction.AccessToken;
import com.google.gson.JsonObject;

import java.util.Calendar;

public class BearerToken extends AccessToken {

    private String token;
    private String tokenType;
    private String refreshToken;

    @Override
    public void setPlainJson(JsonObject plainJson) {
        if (plainJson.get("refresh_token") != null) {
            refreshToken = plainJson.get("refresh_token").getAsString();
        }
        token = plainJson.get("access_token").getAsString();
        tokenType = plainJson.get("token_type").getAsString();
        super.setPlainJson(plainJson);
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getType() {
        return tokenType;
    }

    @Override
    public Calendar expirationDate() {
        Calendar calendar = (Calendar) creationDate.clone();
        calendar.add(Calendar.SECOND, plainJson.get("expires_in").getAsInt());
        return calendar;
    }

}
