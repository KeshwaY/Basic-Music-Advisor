package advisor.core.abstraction;

import advisor.core.components.Album;
import advisor.core.components.Category;
import advisor.core.components.CategoryPlaylist;
import advisor.core.components.Playlist;

import java.io.IOException;
import java.util.List;

public interface ComponentProvider {

    List<Album> getNewReleases(String accessToken) throws IOException, InterruptedException;
    List<Playlist> getFeatured(String accessToken) throws IOException, InterruptedException;
    List<Category> getCategories(String accessToken) throws IOException, InterruptedException;
    List<CategoryPlaylist> getPlaylistsForCategory(String accessToken, String categoryId) throws IOException, InterruptedException;

}
