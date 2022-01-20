package advisor.core.components;

import advisor.core.ComponentProviderImpl;
import advisor.core.abstraction.RequestSender;
import advisor.core.spotifyapi.JsonConverter;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import static org.assertj.swing.assertions.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class ComponentProviderImplTest {

    @Mock private RequestSender requestSender;
    @Mock private JsonConverter jsonConverter;
    @Mock private JsonComponentConverter jsonComponentConverter;
    @Mock private HttpResponse<String> httpResponse;
    private AutoCloseable closeable;

    private ComponentProviderImpl underTest;
    private final String resourceURI = "localhost:8080";
    private final String accessToken = "123456789";
    private final JsonObject testJsonObject = new JsonObject();

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new ComponentProviderImpl(requestSender, resourceURI);

        String testJsonString = "{}";
        underTest.setComponentConverter(jsonComponentConverter);
        underTest.setJsonConverter(jsonConverter);
        given(requestSender.sendGetRequestWithHeaders(any(), any()))
                .willReturn(httpResponse);
        given(httpResponse.body()).willReturn(testJsonString);
        given(jsonConverter.convert(testJsonString))
                .willReturn(testJsonObject);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void itShouldGetNewReleases() throws IOException, InterruptedException {
        // given
        Album testAlbum = Album.builder()
                .build();
        given(jsonComponentConverter.jsonToAlbums(testJsonObject))
                .willReturn(List.of(testAlbum));

        // when
        List<Album> albums = underTest.getNewReleases(accessToken);

        // then
        assertThat(albums).isEqualTo(List.of(testAlbum));
    }

    @Test
    void itShouldGetFeatured() throws IOException, InterruptedException {
        // given
        Playlist testPlaylist = Playlist.builder()
                .build();
        given(jsonComponentConverter.jsonToPlaylist(testJsonObject))
                .willReturn(List.of(testPlaylist));

        // when
        List<Playlist> featured = underTest.getFeatured(accessToken);

        // then
        assertThat(featured).isEqualTo(List.of(testPlaylist));
    }

    @Test
    void itShouldGetCategories() throws IOException, InterruptedException {
        // given
        Category testCategory = Category.builder()
                .build();
        given(jsonComponentConverter.jsonToCategories(testJsonObject))
                .willReturn(List.of(testCategory));

        // when
        List<Category> categories = underTest.getCategories(accessToken);

        // then
        assertThat(categories).isEqualTo(List.of(testCategory));
    }

    @Test
    void itShouldGetPlaylistForCategory() throws IOException, InterruptedException {
        // given
        String categoryId = "ABC";
        CategoryPlaylist testCategory = CategoryPlaylist.builder()
                .build();
        given(jsonComponentConverter.jsonToCategoryPlaylist(categoryId, testJsonObject))
                .willReturn(List.of(testCategory));

        // when
        List<CategoryPlaylist> categories = underTest.getPlaylistsForCategory(accessToken, categoryId);

        // then
        assertThat(categories).isEqualTo(List.of(testCategory));
    }

}