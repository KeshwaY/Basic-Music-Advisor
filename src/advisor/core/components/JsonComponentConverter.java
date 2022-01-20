package advisor.core.components;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonComponentConverter {

    public List<Album> jsonToAlbums(JsonObject jsonObject) {
        List<Album> albums = new ArrayList<>();

        JsonArray items = jsonObject.get("albums").getAsJsonObject().get("items").getAsJsonArray();
        items.forEach(item -> {
            JsonObject object = item.getAsJsonObject();
            String name = object.get("name").getAsString();
            String stringURI = object.get("uri").getAsString();
            try {
                URL url = urlConverter(stringURI);
                JsonArray artists = object.get("artists").getAsJsonArray();
                List<Artist> artistList = new ArrayList<>();
                artists.forEach(artist -> {
                    artistList.add(
                            Artist.builder()
                                    .withName(artist.getAsJsonObject().get("name").getAsString())
                                    .build()
                    );
                });
                albums.add(
                        Album.builder()
                                .withArtists(artistList)
                                .withTitle(name)
                                .withURL(url)
                                .build()
                );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        return albums;
    }

    public List<Playlist> jsonToPlaylist(JsonObject jsonObject) {
        List<Playlist> playlists = new ArrayList<>();

        JsonArray items = jsonObject.get("playlists").getAsJsonObject().get("items").getAsJsonArray();
        items.forEach(item -> {
            JsonObject object = item.getAsJsonObject();
            String name = object.get("name").getAsString();
            String uriString = object.get("uri").getAsString();
            try {
                playlists.add(
                        Playlist.builder()
                                .withTitle(name)
                                .withURL(urlConverter(uriString))
                                .build()
                );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        return playlists;
    }

    public List<Category> jsonToCategories(JsonObject jsonObject) {
        List<Category> categories = new ArrayList<>();

        JsonArray items = jsonObject.get("categories").getAsJsonObject().get("items").getAsJsonArray();
        items.forEach(item -> {
            JsonObject object = item.getAsJsonObject();
            String name = object.get("name").getAsString();
            String id = object.get("id").getAsString();
            categories.add(
                    Category.builder()
                            .withId(id)
                            .withName(name)
                            .build()
            );
        });
        return categories;
    }

    public List<CategoryPlaylist> jsonToCategoryPlaylist(String categoryId, JsonObject jsonObject) {
        List<CategoryPlaylist> playlists = new ArrayList<>();
        Category category = Category.builder()
                .withId(categoryId)
                .build();
        JsonArray items = jsonObject.get("playlists").getAsJsonObject().get("items").getAsJsonArray();
        items.forEach(item -> {
            JsonObject object = item.getAsJsonObject();
            String name = object.get("name").getAsString();
            String uriString = object.get("uri").getAsString();
            try {
                playlists.add(
                        CategoryPlaylist.builder()
                                .withTitle(name)
                                .withCategory(category)
                                .withURL(urlConverter(uriString))
                                .build()
                );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        return playlists;
    }

    private URL urlConverter(String stringURI) throws MalformedURLException {
        String[] split = stringURI.split(":");
        String path = split[1];
        String resource = split[2];
        return new URL(String.format("https://open.spotify.com/%s/%s", path, resource));
    };

}
