package advisor.core;

import advisor.core.spotifyapi.APIPostParameters;
import advisor.core.spotifyapi.GrantType;
import advisor.core.spotifyapi.HeaderAuthorizationEncoder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

public class AuthenticatorImpl implements Authenticator {

    private final RequestSender requestSender;
    private final HeaderAuthorizationEncoder encoder;

    private final URI apiEndPoint;
    private final String clientId;
    private final String clientSecret;

    public AuthenticatorImpl(RequestSender requestSender, String apiEndPoint, String clientId, String clientSecret) {
        this.requestSender = requestSender;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.apiEndPoint = URI.create(apiEndPoint);
        this.encoder = new HeaderAuthorizationEncoder();
    }

    @Override
    public HttpResponse<String> authorizeUser(String code, String redirectURI) throws IOException, InterruptedException {
        List<String> headers = prepareHeaders();
        APIPostParameters postParameters = APIPostParameters.builder()
                .grantType(GrantType.AUTHORIZATION_CODE)
                .userCode(code)
                .redirectURI(redirectURI)
                .build();
        return requestSender.sendPostRequest(
                apiEndPoint,
                headers,
                postParameters.formatForBodyPublisher()
                );
    }

    @Override
    public HttpResponse<String> refreshToken(String token) throws IOException, InterruptedException {
        List<String> headers = prepareHeaders();
        APIPostParameters postParameters = APIPostParameters.builder()
                .grantType(GrantType.REFRESH_TOKEN)
                .tokenToRefresh(token)
                .build();
        return requestSender.sendPostRequest(
                apiEndPoint,
                headers,
                postParameters.formatForBodyPublisher()
        );
    }

    private List<String> prepareHeaders() {
        return List.of(
                "Content-Type", "application/x-www-form-urlencoded",
                "Authorization", encoder.encode(clientId, clientSecret)
                );
    }
}
