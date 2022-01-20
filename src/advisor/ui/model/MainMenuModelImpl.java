package advisor.ui.model;

import advisor.core.AdvisorUser;
import advisor.core.abstraction.Advisor;
import advisor.core.abstraction.User;
import advisor.core.components.Album;
import advisor.core.components.Category;
import advisor.core.components.CategoryPlaylist;
import advisor.core.components.Playlist;
import advisor.ui.UserNotAuthorizedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMenuModelImpl implements MainMenuModel {

    private List<Category> lastSearchedCategories;
    private User user;
    private Advisor advisor;
    private String authLink;

    public MainMenuModelImpl(Advisor advisor, String authLink) {
        this.user = new AdvisorUser();
        this.lastSearchedCategories = new ArrayList<>();
        this.advisor = advisor;
        this.authLink = authLink;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public String getAuthLink() {
        return authLink;
    }

    public void setAuthLink(String authLink) {
        this.authLink = authLink;
    }

    public void setLastSearchedCategories(List<Category> lastSearchedCategories) {
        this.lastSearchedCategories = lastSearchedCategories;
    }

    public List<Category> getLastSearchedCategories() {
        return lastSearchedCategories;
    }

    @Override
    public String authorizeUser() throws IOException, InterruptedException {
        return advisor.createTokenForUser(user.getUuid());
    }

    private boolean isUserUnauthorized() {
        return !advisor.isUserAuthorized(user.getUuid());
    }

    @Override
    public List<Album> getNew() throws IOException, InterruptedException, UserNotAuthorizedException {
        if (isUserUnauthorized()) {
            throw new UserNotAuthorizedException();
        }
        return advisor.getNewReleases(user.getUuid());
    }

    @Override
    public List<Playlist> getFeatured() throws IOException, InterruptedException, UserNotAuthorizedException {
        if (isUserUnauthorized()) {
            throw new UserNotAuthorizedException();
        }
        return advisor.getFeatured(user.getUuid());
    }

    @Override
    public List<Category> getCategories() throws IOException, InterruptedException, UserNotAuthorizedException {
        if (isUserUnauthorized()) {
            throw new UserNotAuthorizedException();
        }
        lastSearchedCategories = advisor.getCategories(user.getUuid());
        return lastSearchedCategories;
    }

    @Override
    public List<CategoryPlaylist> getPlaylistsForCategory(String categoryName) throws IOException, InterruptedException, UserNotAuthorizedException {
        if (isUserUnauthorized()) {
            throw new UserNotAuthorizedException();
        }
        Category category = lastSearchedCategories.stream()
                .filter(c -> c.getName().equals(categoryName))
                .findFirst()
                .orElse(null);
        if (category == null) {
            return null;
        }
        return advisor.getPlaylistsForCategory(user.getUuid(), category.getId());
    }
}
