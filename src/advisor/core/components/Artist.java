package advisor.core.components;

import java.net.URL;

public class Artist extends AbstractComponentWithURL {

    private String name;

    public String getName() {
        return name;
    }

    public static ArtistBuilder builder() {
        return new ArtistBuilder();
    }

    public static final class ArtistBuilder {
        private String name;
        private URL url;

        private ArtistBuilder() {
        }

        public static ArtistBuilder anArtist() {
            return new ArtistBuilder();
        }

        public ArtistBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ArtistBuilder withURL(URL url) {
            this.url = url;
            return this;
        }

        public Artist build() {
            Artist artist = new Artist();
            artist.name = name;
            artist.url = url;
            return artist;
        }
    }
}
