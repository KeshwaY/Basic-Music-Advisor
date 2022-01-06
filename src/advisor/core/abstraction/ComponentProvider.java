package advisor.core.abstraction;

import advisor.core.components.Album;
import advisor.core.components.Category;
import advisor.core.components.CategoryPlaylist;
import advisor.core.components.Playlist;

import java.util.List;

public interface ComponentProvider {

    List<Album> getNewReleases();
    List<Playlist> getFeatured();
    List<Category> getCategories();
    List<CategoryPlaylist> getPlaylistsForCategory(String categoryName);

}
