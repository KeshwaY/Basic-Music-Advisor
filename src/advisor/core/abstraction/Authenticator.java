package advisor.core.abstraction;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface Authenticator {

    HttpResponse<String> authorizeUser(String code) throws IOException, InterruptedException;
    HttpResponse<String> refreshToken(String token) throws IOException, InterruptedException;

}
