package advisor.core.components;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ComponentBuildersTest {

    @Test
    void itShouldBuildCategory() {
        // given
        String name = "test";

        // when
        Category category = Category.builder()
                .withName(name)
                .build();

        // then
        assertThat(category.getName())
                .isEqualTo(name);
    }

    @Test
    void itShouldBuildArtist() {
        // given
        String name = "TestName";

        // when
        Artist artist = Artist.builder()
                .withName(name)
                .build();

        // then
        assertThat(artist.getName())
                .isEqualTo(name);
    }

    @Test
    void itShouldBuildAlbum() {
        // given
        String title = "Test";
        String firstArtist = "TestArtist1";
        String secondArtist = "TestArtist2";
        List<Artist> artistList = new ArrayList<>();
        artistList.add(Artist.builder()
                .withName(firstArtist)
                .build());
        artistList.add(Artist.builder()
                .withName(secondArtist)
                .build());

        // when
        Album album = Album.builder()
                .withTitle(title)
                .withArtists(artistList)
                .build();

        // then
        assertThat(album.getArtists())
                .isEqualTo(artistList);
        assertThat(album.getTitle())
                .isEqualTo(title);
    }

    @Test
    void itShouldBuildPlaylist() {
        // given
        String title = "test";

        // when
        Playlist playlist = Playlist.builder()
                .withTitle(title)
                .build();

        // then
        assertThat(playlist.title)
                .isEqualTo(title);
    }

    @Test
    void itShouldBuildPlaylistForCategory() {
        // given
        String title = "test";
        Category category = Category.builder()
                .withName("TestCategory")
                .build();

        // when
        CategoryPlaylist categoryPlaylist = CategoryPlaylist.builder()
                .withCategory(category)
                .withTitle(title)
                .build();

        // then
        assertThat(categoryPlaylist.getTitle())
                .isEqualTo(title);
        assertThat(categoryPlaylist.getCategory())
                .isEqualTo(category);
    }

}
