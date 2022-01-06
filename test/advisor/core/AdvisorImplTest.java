package advisor.core;

import advisor.core.abstraction.ComponentProvider;
import advisor.core.abstraction.ServerHandler;
import advisor.core.abstraction.TokenManager;
import advisor.core.components.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class AdvisorImplTest {

    @Mock private ServerHandler serverHandler;
    @Mock private TokenManager tokenManager;
    @Mock private ComponentProvider componentProvider;
    private AutoCloseable closeable;

    private AdvisorImpl underTest;

    @BeforeEach
    void setUp() throws IOException {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new AdvisorImpl();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void itShouldCreateToken() throws IOException, InterruptedException {
        // given
        underTest.initialize(8084, "http://localhost:8081", "testId", "testSecret", "http://localhost:8082");
        UUID uuid = UUID.randomUUID();
        String accessToken = "asd";
        String token = "NgCXRK...MzYjw";

        given(serverHandler.getNewUserCode(1L, TimeUnit.MINUTES))
                .willReturn(accessToken);

        given(tokenManager.createTokenForUser(any(), any()))
                .willReturn(token);

        underTest.setTokenManager(tokenManager);
        underTest.setServerHandler(serverHandler);

        // when
        String underTestToken = underTest.createTokenForUser(uuid);

        // then
        verify(serverHandler)
                .getNewUserCode(1L, TimeUnit.MINUTES);
        verify(tokenManager)
                .createTokenForUser(uuid, accessToken);

        assertThat(underTestToken)
                .isEqualTo(token);
        assertThat(underTest.getTokenManager())
                .isEqualTo(tokenManager);
        assertThat(underTest.getServerHandler())
                .isEqualTo(serverHandler);
    }

    @Test
    void itShouldFailCheckingIfUserIsAuthorized() throws IOException, InterruptedException {
        // given
        UUID userUUID = UUID.randomUUID();
        given(tokenManager.isTokenCreatedForUser(userUUID))
                .willReturn(false);

        // when
        underTest.setTokenManager(tokenManager);
        boolean userAuthorized = underTest.isUserAuthorized(userUUID);

        // then
        assertThat(userAuthorized)
                .isEqualTo(false);
    }

    @Test
    void itShouldCheckingIfUserIsAuthorized() throws IOException, InterruptedException {
        // given
        UUID userUUID = UUID.randomUUID();
        given(tokenManager.isTokenCreatedForUser(userUUID))
                .willReturn(true);

        // when
        underTest.setTokenManager(tokenManager);
        boolean userAuthorized = underTest.isUserAuthorized(userUUID);

        // then
        assertThat(userAuthorized)
                .isEqualTo(true);
    }

    @Test
    void itShouldGetNewReleases() {
        // given
        Artist testArtis = Artist.builder()
                .withName("TestArtist")
                .build();
        Album testAlbum = Album.builder()
                .withArtist(testArtis)
                .withTitle("TestAlbum")
                .build();

        given(componentProvider.getNewReleases())
                .willReturn(List.of(testAlbum));

        // when
        underTest.setComponentProvider(componentProvider);
        List<Album> albums = underTest.getNewReleases();


        // then
        verify(componentProvider).getNewReleases();
        assertThat(albums)
                .isEqualTo(List.of(testAlbum));
    }

    @Test
    void itShouldGetFeatured() {
        // given
        Playlist playlist = Playlist.builder()
                .withTitle("TestPlaylist")
                .build();

        given(componentProvider.getFeatured())
                .willReturn(List.of(playlist));

        // when
        underTest.setComponentProvider(componentProvider);
        List<Playlist> featured = underTest.getFeatured();


        // then
        verify(componentProvider).getFeatured();
        assertThat(featured)
                .isEqualTo(List.of(playlist));
    }

    @Test
    void itShouldGetCategories() {
        // given
        Category category = Category.builder()
                .withName("TestCategory")
                .build();

        given(componentProvider.getCategories())
                .willReturn(List.of(category));

        // when
        underTest.setComponentProvider(componentProvider);
        List<Category> categories = underTest.getCategories();


        // then
        verify(componentProvider).getCategories();
        assertThat(categories)
                .isEqualTo(List.of(category));
    }

    @Test
    void itShouldGetPlaylistForCategory() {
        // given
        Category category = Category.builder()
                .withName("TestCategory")
                .build();

        CategoryPlaylist playlist = CategoryPlaylist.builder()
                .withCategory(category)
                .build();

        given(componentProvider.getPlaylistsForCategory(any()))
                .willReturn(List.of(playlist));

        // when
        underTest.setComponentProvider(componentProvider);
        List<CategoryPlaylist> playlists = underTest.getPlaylistsForCategory(any());


        // then
        verify(componentProvider).getPlaylistsForCategory(any());
        assertThat(playlists)
                .isEqualTo(List.of(playlist));
    }


}