package advisor.ui.model;

import advisor.core.components.Album;
import advisor.core.components.Category;
import advisor.core.components.CategoryPlaylist;
import advisor.core.components.Playlist;
import advisor.ui.UserNotAuthorizedException;

import java.io.IOException;
import java.util.List;

public interface MainMenuModel extends Model {

    List<Album> getNew() throws IOException, InterruptedException, UserNotAuthorizedException;
    List<Playlist> getFeatured() throws IOException, InterruptedException, UserNotAuthorizedException;
    List<Category> getCategories() throws IOException, InterruptedException, UserNotAuthorizedException;
    List<CategoryPlaylist> getPlaylistsForCategory(String categoryName) throws IOException, InterruptedException, UserNotAuthorizedException;
    String authorizeUser() throws IOException, InterruptedException;
    String getAuthLink();
}
