package advisor.core.components;

import java.util.List;

public interface FakeApiResponses {

    static List<Playlist> getFeatured() {
        Playlist first = Playlist.builder()
                .withTitle("Mellow Morning")
                .build();
        Playlist second = Playlist.builder()
                .withTitle("Wake Up and Smell the Coffee")
                .build();
        Playlist third = Playlist.builder()
                .withTitle("Monday Motivation")
                .build();
        Playlist fourth = Playlist.builder()
                .withTitle("Songs to Sing in the Shower")
                .build();
        return List.of(first, second, third, fourth);
    }


    static List<Album> getNew() {
        Album first = Album.builder()
                .withTitle("Mountains")
                .withArtist(
                        Artist.builder()
                                .withName("Sia")
                                .build()
                )
                .withArtist(
                        Artist.builder()
                                .withName("Diplo")
                                .build()
                )
                .withArtist(
                        Artist.builder()
                                .withName("Labrinth")
                                .build()
                )
                .build();
        Album second = Album.builder()
                .withTitle("Runaway")
                .withArtist(
                        Artist.builder()
                                .withName("Lil Peep")
                                .build()
                )
                .build();
        Album third = Album.builder()
                .withTitle("The Greatest Show")
                .withArtist(
                        Artist.builder()
                                .withName("Panic! At The Disco")
                                .build()
                )
                .build();
        Album fourth = Album.builder()
                .withTitle("All Out Life")
                .withArtist(
                        Artist.builder()
                                .withName("Slipknot")
                                .build()
                )
                .build();
        return List.of(first, second, third, fourth);
    }

    static List<Category> getCategories() {
        Category first = Category.builder()
                .withName("Top Lists")
                .build();
        Category second = Category.builder()
                .withName("Pop")
                .build();
        Category third = Category.builder()
                .withName("Mood")
                .build();
        Category fourth = Category.builder()
                .withName("Latin")
                .build();
        return List.of(first, second, third, fourth);
    }

    static List<CategoryPlaylist> getMoodCategory() {
        Category category = Category.builder()
                .withName("Mood")
                .build();

        CategoryPlaylist first = CategoryPlaylist.builder()
                .withTitle("Walk Like A Badass")
                .withCategory(category)
                .build();
        CategoryPlaylist second = CategoryPlaylist.builder()
                .withTitle("Rage Beats")
                .withCategory(category)
                .build();
        CategoryPlaylist third = CategoryPlaylist.builder()
                .withTitle("Arab Mood Booster")
                .withCategory(category)
                .build();
        CategoryPlaylist fourth = CategoryPlaylist.builder()
                .withTitle("Sunday Stroll")
                .withCategory(category)
                .build();
        return List.of(first, second, third, fourth);
    }

}
