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
        return grantType.equals(GrantType.AUTHORIZATION_CODE) ? getStringForAuthorization() : getStringForRefresh();
    }

    private String getStringForAuthorization() {
        return String.format("grant_type=%s&code=%s&redirect_uri=%s", grantType.getMessage(), code, redirectURI);
    }

    private String getStringForRefresh() {
        return String.format("grant_type=%s&refresh_token=%s", grantType, token);
    }

    public static class Builder {
        private GrantType grantType = null;
        private String code = null;
        private String token = null;
        private String redirectURI = null;

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

        public APIPostParameters build() throws IllegalStateException {
            if (grantType == null) {
                throw new IllegalStateException("Grant type have to be specified!");
            }
            if (grantType.equals(GrantType.AUTHORIZATION_CODE) && !validateFieldsForAuthorizationType()) {
                throw new IllegalStateException(String.format("When using %s you need to provide Builder with a valid userCode and valid redirectURI!", grantType.getMessage()));
            }

            if (grantType.equals(GrantType.REFRESH_TOKEN) && !validateFieldsForRefreshType()) {
                throw new IllegalStateException(String.format("When using %s you need to provide Builder with a valid userToken!", grantType.getMessage()));
            }

            APIPostParameters request = new APIPostParameters();
            request.grantType = grantType;
            request.code = code;
            request.redirectURI = redirectURI;
            request.token = token;

            return request;
        }

        private boolean validateFieldsForAuthorizationType() {
            return (code != null && redirectURI != null) && (!code.trim().isEmpty() && !redirectURI.trim().isEmpty());
        }

        private boolean validateFieldsForRefreshType() {
            return (token != null) && (!token.trim().isEmpty());
        }

    }

}
