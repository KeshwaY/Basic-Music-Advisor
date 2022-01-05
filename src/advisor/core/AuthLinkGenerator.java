package advisor.core;

public interface AuthLinkGenerator {

    static String generate(String clientId, String redirectURI) {
        return String.format("https://accounts.spotify.com/authorize?client_id=%s&redirect_uri=%s", clientId, redirectURI);
    }
}