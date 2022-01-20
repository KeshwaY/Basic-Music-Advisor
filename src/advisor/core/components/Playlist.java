package advisor.core.components;

import java.net.URL;

public class Playlist extends AbstractComponentWithURL {

    protected String title;

    public String getTitle() {
        return title;
    }

    public static PlaylistBuilder builder() {
        return new PlaylistBuilder();
    }

    public static class PlaylistBuilder {
        protected String title;
        protected URL url;

        protected PlaylistBuilder() {
        }

        public static PlaylistBuilder aPlaylist() {
            return new PlaylistBuilder();
        }

        public PlaylistBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public PlaylistBuilder withURL(URL url) {
            this.url = url;
            return this;
        }

        public Playlist build() {
            Playlist playlist = new Playlist();
            playlist.title = this.title;
            playlist.url = url;
            return playlist;
        }
    }
}
