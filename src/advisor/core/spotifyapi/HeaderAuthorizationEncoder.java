package advisor.core.spotifyapi;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HeaderAuthorizationEncoder implements Encoder {

    @Override
    public String encode(String clientId, String clientSecret) {
        String toEncode = String.format("%s:%s", clientId, clientSecret);

        return "Basic " + Base64.getEncoder().encodeToString(toEncode.getBytes(StandardCharsets.UTF_8));
    }

}
