package advisor.core.components;

public class CategoryPlaylist extends Playlist {

    private Category category;

    public Category getCategory() {
        return category;
    }

    public static CategoryPlaylistBuilder builder() {
        return new CategoryPlaylistBuilder();
    }

    public static final class CategoryPlaylistBuilder extends Playlist.PlaylistBuilder {
        private Category category;

        public CategoryPlaylistBuilder withCategory(Category category) {
            this.category = category;
            return this;
        }

        @Override
        public CategoryPlaylistBuilder withTitle(String title) {
            return (CategoryPlaylistBuilder) super.withTitle(title);
        }

        @Override
        public CategoryPlaylist build() {
            CategoryPlaylist playlist = new CategoryPlaylist();
            playlist.title = this.title;
            playlist.category = category;
            return playlist;
        }
    }

}
