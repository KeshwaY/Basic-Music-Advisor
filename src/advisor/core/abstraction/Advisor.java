package advisor.core.abstraction;

import advisor.core.components.Album;
import advisor.core.components.Category;
import advisor.core.components.CategoryPlaylist;
import advisor.core.components.Playlist;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface Advisor {
    void initialize(int serverPort, String apiEndPoint, String resourceURI, String clientId, String clientSecret, String redirectURI) throws IOException;
    String createTokenForUser(UUID uuid) throws IOException, InterruptedException;
    boolean isUserAuthorized(UUID uuid);

    List<Album> getNewReleases(UUID uuid) throws IOException, InterruptedException;
    List<Playlist> getFeatured(UUID uuid) throws IOException, InterruptedException;
    List<Category> getCategories(UUID uuid) throws IOException, InterruptedException;
    List<CategoryPlaylist> getPlaylistsForCategory(UUID uuid, String categoryId) throws IOException, InterruptedException;
}
