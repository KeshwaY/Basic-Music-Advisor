package advisor.core.spotifyapi;

public enum GrantType {
    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token");

    private final String message;

    GrantType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
