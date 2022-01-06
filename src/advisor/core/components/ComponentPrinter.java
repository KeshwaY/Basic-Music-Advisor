package advisor.core.components;

import java.util.List;

public class ComponentPrinter {

    public void printAlbums(String title, List<Album> albums) {
        StringBuilder builder = new StringBuilder(title);
        builder.append("\n");
        albums.forEach(album -> {
            builder.append(album.getTitle()).append(" [");
            album.getArtists().forEach(artist -> {
                builder.append(artist.getName()).append(", ");
            });
            builder.replace(
                    builder.lastIndexOf(","),
                    builder.lastIndexOf(" ") + 1,
                    ""
            );
            builder.append("]\n");
        });
        fixLastSpace(builder);
        System.out.println(builder);
    }

    public void printCategories(String title, List<Category> categories) {
        StringBuilder builder = new StringBuilder(title);
        builder.append("\n");
        categories.forEach(category -> {
            builder.append(category.getName()).append("\n");
        });
        fixLastSpace(builder);
        System.out.println(builder);
    }

    public <PlaylistT extends Playlist> void printPlaylist(String title, List<PlaylistT> playlists) {
        StringBuilder builder = new StringBuilder(title);
        builder.append("\n");
        playlists.forEach(playlist -> {
            builder.append(playlist.getTitle()).append("\n");
        });
        fixLastSpace(builder);
        System.out.println(builder);
    }

    private void fixLastSpace(StringBuilder builder) {
        builder.replace(builder.lastIndexOf("\n"), builder.lastIndexOf("\n") + 1, "");
    }

}
