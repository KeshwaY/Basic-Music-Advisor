package advisor.core.spotifyapi;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.Calendar;

public abstract class AccessToken implements Serializable {

    protected JsonObject plainJson;
    protected Calendar creationDate;

    protected AccessToken() {
        plainJson = new JsonObject();
    }

    public void setPlainJson(JsonObject plainJson) {
        this.plainJson = plainJson;
        creationDate = Calendar.getInstance();
    }

    public abstract String getToken();
    public abstract String getRefreshToken();
    public abstract String getType();
    public abstract Calendar expirationDate();

}
