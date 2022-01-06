package advisor.core.components;

import java.util.ArrayList;
import java.util.List;

public class Album extends AbstractComponent {

    private String title;
    private List<Artist> artists;

    public String getTitle() {
        return title;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public static AlbumBuilder builder() {
        return new AlbumBuilder();
    }

    public static final class AlbumBuilder {
        private String title;
        private List<Artist> artists = new ArrayList<>();

        private AlbumBuilder() {
        }

        public static AlbumBuilder anAlbum() {
            return new AlbumBuilder();
        }

        public AlbumBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public AlbumBuilder withArtists(List<Artist> artists) {
            this.artists = artists;
            return this;
        }

        public AlbumBuilder withArtist(Artist artist) {
            artists.add(artist);
            return this;
        }

        public Album build() {
            Album album = new Album();
            album.title = title;
            album.artists = artists;
            return album;
        }
    }
}
