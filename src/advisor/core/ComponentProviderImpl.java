package advisor.core;

import advisor.core.abstraction.ComponentProvider;
import advisor.core.abstraction.RequestSender;
import advisor.core.components.*;
import advisor.core.spotifyapi.JsonConverter;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

public class ComponentProviderImpl implements ComponentProvider {

    private final RequestSender sender;
    private final String resourceURI;
    private JsonConverter jsonConverter;
    private JsonComponentConverter componentConverter;

    public ComponentProviderImpl(RequestSender sender, String resourceURI) {
        this.jsonConverter = new JsonConverter();
        this.componentConverter = new JsonComponentConverter();
        this.resourceURI = resourceURI;
        this.sender = sender;
    }

    public void setJsonConverter(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    public void setComponentConverter(JsonComponentConverter componentConverter) {
        this.componentConverter = componentConverter;
    }

    @Override
    public List<Album> getNewReleases(String accessToken) throws IOException, InterruptedException {
        HttpResponse<String> response = sender.sendGetRequestWithHeaders(
                URI.create(resourceURI + "/browse/new-releases"),
                createHeaders(accessToken)
        );
        JsonObject jsonObject = jsonConverter.convert(response.body());
        return componentConverter.jsonToAlbums(jsonObject);
    }

    @Override
    public List<Playlist> getFeatured(String accessToken) throws IOException, InterruptedException {
        HttpResponse<String> response = sender.sendGetRequestWithHeaders(
                URI.create(resourceURI + "/browse/featured-playlists"),
                createHeaders(accessToken)
        );
        JsonObject jsonObject = jsonConverter.convert(response.body());
        return componentConverter.jsonToPlaylist(jsonObject);
    }

    @Override
    public List<Category> getCategories(String accessToken) throws IOException, InterruptedException {
        HttpResponse<String> response = sender.sendGetRequestWithHeaders(
                URI.create(resourceURI + "/browse/categories"),
                createHeaders(accessToken)
        );
        JsonObject jsonObject = jsonConverter.convert(response.body());
        return componentConverter.jsonToCategories(jsonObject);
    }

    @Override
    public List<CategoryPlaylist> getPlaylistsForCategory(String accessToken, String categoryId) throws IOException, InterruptedException {
        HttpResponse<String> response = sender.sendGetRequestWithHeaders(
                URI.create(resourceURI + String.format("/browse/categories/%s/playlists", categoryId)),
                createHeaders(accessToken)
        );
        JsonObject jsonObject = jsonConverter.convert(response.body());
        return componentConverter.jsonToCategoryPlaylist(categoryId, jsonObject);
    }

    private List<String> createHeaders(String token) {
        return List.of(
                "Content-Type", "application/json",
                "Authorization", "Bearer " + token
        );
    }
}
