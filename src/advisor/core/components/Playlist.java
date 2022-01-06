package advisor.core.components;

public class Playlist {

    protected String title;

    public String getTitle() {
        return title;
    }

    public static PlaylistBuilder builder() {
        return new PlaylistBuilder();
    }

    public static class PlaylistBuilder {
        protected String title;

        protected PlaylistBuilder() {
        }

        public static PlaylistBuilder aPlaylist() {
            return new PlaylistBuilder();
        }

        public PlaylistBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Playlist build() {
            Playlist playlist = new Playlist();
            playlist.title = this.title;
            return playlist;
        }
    }
}
