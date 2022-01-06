package advisor.core;

import advisor.core.abstraction.ComponentProvider;
import advisor.core.abstraction.RequestSender;
import advisor.core.components.*;

import java.util.List;

public class ComponentProviderImpl implements ComponentProvider {

    private final RequestSender sender;

    public ComponentProviderImpl(RequestSender sender) {
        this.sender = sender;
    }

    @Override
    public List<Album> getNewReleases() {
        return FakeApiResponses.getNew();
    }

    @Override
    public List<Playlist> getFeatured() {
        return FakeApiResponses.getFeatured();
    }

    @Override
    public List<Category> getCategories() {
        return FakeApiResponses.getCategories();
    }

    @Override
    public List<CategoryPlaylist> getPlaylistsForCategory(String categoryName) {
        return FakeApiResponses.getMoodCategory();
    }
}
