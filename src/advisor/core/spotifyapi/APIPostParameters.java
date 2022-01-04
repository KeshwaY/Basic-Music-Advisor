package advisor.core.spotifyapi;

public class APIPostParameters implements BodyPublishable {

    private GrantType grantType;
    private String code;
    private String token;
    private String redirectURI;

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String formatForBodyPublisher() {
        if(grantType.equals(GrantType.AUTHORIZATION_CODE)) {
            return String.format("grant_type=%s&code=%s&redirect_uri=%s", grantType.getMessage(), code, redirectURI);
        }
        if (grantType.equals(GrantType.REFRESH_TOKEN)) {
            return String.format("grant_type=%s&refresh_token=%s", grantType, token);
        }
        return null;
    }

    public static class Builder {
        private GrantType grantType;
        private String code;
        private String token;
        private String redirectURI;

        public Builder grantType(GrantType grantType) {
            this.grantType = grantType;
            return this;
        }

        public Builder userCode(String code) {
            this.code = code;
            return this;
        }

        public Builder tokenToRefresh(String token) {
            this.token = token;
            return this;
        }

        public Builder redirectURI(String redirectURI) {
            this.redirectURI = redirectURI;
            return this;
        }

        public APIPostParameters build() {
            if (grantType.equals(GrantType.AUTHORIZATION_CODE) && (code.isEmpty() || redirectURI.isEmpty())) {
                throw new IllegalStateException(String.format("When using %s you need to provide Builder with userCode and redirectURI!", grantType.getMessage()));
            }

            if (grantType.equals(GrantType.REFRESH_TOKEN) && (token.isEmpty())) {
                throw new IllegalStateException(String.format("When using %s you need to provide Builder with userCode and redirectURI!", grantType.getMessage()));
            }

            APIPostParameters request = new APIPostParameters();
            request.grantType = grantType;
            request.code = code;
            request.redirectURI = redirectURI;
            request.token = token;

            return request;
        }

    }

}
