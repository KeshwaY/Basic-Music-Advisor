package advisor.core.components;

import java.io.Serializable;

public class Artist implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public static ArtistBuilder builder() {
        return new ArtistBuilder();
    }

    public static final class ArtistBuilder {
        private String name;

        private ArtistBuilder() {
        }

        public static ArtistBuilder anArtist() {
            return new ArtistBuilder();
        }

        public ArtistBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Artist build() {
            Artist artist = new Artist();
            artist.name = name;
            return artist;
        }
    }
}
