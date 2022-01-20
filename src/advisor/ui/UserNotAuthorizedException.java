package advisor.ui;

public class UserNotAuthorizedException extends Exception {
    public UserNotAuthorizedException() {
        super("Please, provide access for application.");
    }
}
