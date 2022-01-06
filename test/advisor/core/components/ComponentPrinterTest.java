package advisor.core.components;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ComponentPrinterTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private ComponentPrinter underTest;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        underTest = new ComponentPrinter();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void itShouldPrintAlbumsAndTitle() {
        // given
        String testAlbumTitle = "TestTitle";
        String anotherTestAlbumTitle = "TestTitle2";
        Artist testArtist = Artist.builder()
                .withName("TestArtist")
                .build();
        Album testAlbum = Album.builder()
                .withTitle(testAlbumTitle)
                .withArtist(testArtist)
                .build();
        Album anotherTestAlbum = Album.builder()
                .withTitle(anotherTestAlbumTitle)
                .withArtist(testArtist)
                .build();

        List<Album> albumList = List.of(testAlbum, anotherTestAlbum);

        String expected = "---TestTitle---\n" +
                "TestTitle [TestArtist]\n" +
                "TestTitle2 [TestArtist]\n";

        // when
        underTest.printAlbums("---TestTitle---", albumList);

        // then
        assertThat(outputStreamCaptor.toString())
                .isEqualTo(expected);
    }

    @Test
    void itShouldPrintCategories() {
        // given
        Category testCategory = Category.builder()
                .withName("TestCategory")
                .build();
        Category anotherTestCategory = Category.builder()
                .withName("AnotherTestCategory")
                .build();
        List<Category> categories = List.of(testCategory, anotherTestCategory);

        String expected = "---Test---\n" + "TestCategory\n" + "AnotherTestCategory\n";

        // when
        underTest.printCategories("---Test---", categories);

        // then
        assertThat(outputStreamCaptor.toString())
                .isEqualTo(expected);
    }

    @Test
    void itShouldPrintPlaylist() {
        // given
        Playlist testPlaylist = Playlist.builder()
                .withTitle("TestPlaylist")
                .build();
        Playlist anotherTestPlaylist = Playlist.builder()
                .withTitle("AnotherTestPlaylist")
                .build();
        List<Playlist> playlists = List.of(testPlaylist, anotherTestPlaylist);

        String expected = "---Test---\n" + "TestPlaylist\n" + "AnotherTestPlaylist\n";

        // when
        underTest.printPlaylist("---Test---", playlists);

        // then
        assertThat(outputStreamCaptor.toString())
                .isEqualTo(expected);
    }

    @Test
    void itShouldPrintCategoryPlaylist() {
        // given
        Category category = Category.builder()
                .withName("TestCategory")
                .build();
        CategoryPlaylist testPlaylist = CategoryPlaylist.builder()
                .withCategory(category)
                .withTitle("TestPlaylist")
                .build();
        CategoryPlaylist anotherTestPlaylist = CategoryPlaylist.builder()
                .withTitle("AnotherTestPlaylist")
                .withCategory(category)
                .build();
        List<CategoryPlaylist> playlists = List.of(testPlaylist, anotherTestPlaylist);

        String expected = "---Test---\n" + "TestPlaylist\n" + "AnotherTestPlaylist\n";

        // when
        underTest.printPlaylist("---Test---", playlists);

        // then
        assertThat(outputStreamCaptor.toString())
                .isEqualTo(expected);
    }

}