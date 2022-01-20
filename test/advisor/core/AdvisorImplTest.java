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
import static org.mockito.Mockito.when;


// is compared field by field, excluding

class AdvisorImplTest {

    @Mock private ServerHandler serverHandler;
    @Mock private TokenManager tokenManager;
    @Mock private ComponentProvider componentProvider;
    private AutoCloseable closeable;

    private AdvisorImpl underTest;

    private final String testToken = "123456789";

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
        underTest.initialize(8084, "http://localhost:8081", "http://localhost:8082", "testId", "testSecret", "http://localhost:8083");
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
    void itShouldFailCreatingToken() {
        // given
        UUID uuid = UUID.randomUUID();
        underTest.setServerHandler(serverHandler);
        given(serverHandler.getNewUserCode(1L, TimeUnit.MINUTES))
                .willReturn(null);

        // when
        // then
        assertThatThrownBy(() -> underTest.createTokenForUser(uuid)).isInstanceOf(IOException.class);
        verify(serverHandler)
                .getNewUserCode(1L, TimeUnit.MINUTES);

    }

    @Test
    void itShouldFailCheckingIfUserIsAuthorized() throws IOException, InterruptedException {
        // given
        UUID userUUID = UUID.randomUUID();
        given(tokenManager.isTokenCreatedForUser(userUUID))
                .willReturn(false);

        // when
        underTest.setTokenManager(tokenManager); // given
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
    void itShouldGetNewReleases() throws IOException, InterruptedException {
        // given
        Artist testArtis = Artist.builder()
                .withName("TestArtist")
                .build();
        Album testAlbum = Album.builder()
                .withArtist(testArtis)
                .withTitle("TestAlbum")
                .build();

        underTest.setComponentProvider(componentProvider);
        underTest.setTokenManager(tokenManager);

        given(tokenManager.getUserToken(any())).willReturn(testToken);
        given(componentProvider.getNewReleases(testToken))
                .willReturn(List.of(testAlbum));


        // when
        List<Album> albums = underTest.getNewReleases(any());


        // then
        assertThat(albums)
                .isEqualTo(List.of(testAlbum));
    }

    @Test
    void itShouldGetFeatured() throws IOException, InterruptedException {
        // given
        Playlist playlist = Playlist.builder()
                .withTitle("TestPlaylist")
                .build();

        underTest.setTokenManager(tokenManager);
        underTest.setComponentProvider(componentProvider);

        given(tokenManager.getUserToken(any())).willReturn(testToken);
        given(componentProvider.getFeatured(any()))
                .willReturn(List.of(playlist));

        // when
        List<Playlist> featured = underTest.getFeatured(any());


        // then
        assertThat(featured)
                .isEqualTo(List.of(playlist));
    }

    @Test
    void itShouldGetCategories() throws IOException, InterruptedException {
        // given
        Category category = Category.builder()
                .withName("TestCategory")
                .build();

        underTest.setTokenManager(tokenManager);
        underTest.setComponentProvider(componentProvider);

        given(tokenManager.getUserToken(any())).willReturn(testToken);
        given(componentProvider.getCategories(any()))
                .willReturn(List.of(category));

        // when
        List<Category> categories = underTest.getCategories(any());


        // then
        assertThat(categories)
                .isEqualTo(List.of(category));
    }

    @Test
    void itShouldGetPlaylistForCategory() throws IOException, InterruptedException {
        // given
        Category category = Category.builder()
                .withName("TestCategory")
                .build();

        CategoryPlaylist playlist = CategoryPlaylist.builder()
                .withCategory(category)
                .build();

        underTest.setTokenManager(tokenManager);
        underTest.setComponentProvider(componentProvider);

        given(tokenManager.getUserToken(any())).willReturn(testToken);
        given(componentProvider.getPlaylistsForCategory(any(), any()))
                .willReturn(List.of(playlist));

        // when
        List<CategoryPlaylist> playlists = underTest.getPlaylistsForCategory(UUID.randomUUID(), "Test category");


        // then
        assertThat(playlists)
                .isEqualTo(List.of(playlist));
    }

    @Test
    void itShouldSetComponentProvider() {
        // given
        // when
        underTest.setComponentProvider(componentProvider);

        // then
        assertThat(underTest.getComponentProvider()).isEqualTo(componentProvider);
    }


}