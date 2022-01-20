package advisor.ui.controller;

import advisor.core.components.*;
import advisor.ui.UserNotAuthorizedException;
import advisor.ui.model.MainMenuModel;
import advisor.ui.model.PageModelImpl;
import advisor.ui.view.MainMenuView;
import advisor.ui.view.PageView;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

public class MainMenuController implements Controller<MainMenuModel, MainMenuView> {

    private MainMenuModel model;
    private MainMenuView view;
    private ComponentPrinter printer;
    private int numberOfElementsPerPage;

    public MainMenuController(MainMenuModel model, MainMenuView view, ComponentPrinter printer, int numberOfElementsPerPage) {
        this.model = model;
        this.view = view;
        this.printer = printer;
        this.numberOfElementsPerPage = numberOfElementsPerPage;
    }

    @Override
    public void setModel(MainMenuModel model) {
        this.model = model;
    }

    @Override
    public MainMenuModel getModel() {
        return model;
    }

    @Override
    public void setView(MainMenuView view) {
        this.view = view;
    }

    @Override
    public MainMenuView getView() {
        return this.view;
    }

    public ComponentPrinter getPrinter() {
        return printer;
    }

    public void setPrinter(ComponentPrinter printer) {
        this.printer = printer;
    }

    public int getNumberOfElementsPerPage() {
        return numberOfElementsPerPage;
    }

    public void setNumberOfElementsPerPage(int numberOfElementsPerPage) {
        this.numberOfElementsPerPage = numberOfElementsPerPage;
    }

    @Override
    public void start() {
        while (true) {
            String[] split = view.getInput().split(" ");
            String command = split[0];
            if (command.equals("exit")) {
                view.render("---GOODBYE!---");
                break;
            }
            try {
                if (command.equals("auth")) {
                    handleAuth();
                    continue;
                }
                handleCommand(split);
            } catch (UserNotAuthorizedException e) {
                view.render(e.getMessage());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAuth() throws IOException, InterruptedException {
        view.render("use this link to request the access code:");
        view.render(model.getAuthLink());
        String token = model.authorizeUser();
        if(token != null) {
            view.render("Success!");
        }
    }

    private void handleCommand(String[] split) throws UserNotAuthorizedException, IOException, InterruptedException {
        String command = split[0];
        switch (command) {
            case "new":
                List<Album> albums = model.getNew();
                prepareAndStartPageController(albums, printer::printAlbums);
                break;
            case "featured":
                List<Playlist> playlists = model.getFeatured();
                prepareAndStartPageController(playlists, printer::printPlaylist);
                break;
            case "categories":
                List<Category> categories = model.getCategories();
                prepareAndStartPageController(categories, printer::printCategories);
                break;
            case "playlists":
                List<CategoryPlaylist> categoryPlaylists = model.getPlaylistsForCategory(split[1]);
                prepareAndStartPageController(categoryPlaylists, printer::printPlaylist);
                break;
            default:
                view.render("Unknown command.");
        }
    }

    private <ComponentT extends AbstractComponent> void prepareAndStartPageController(List<ComponentT> modelList, BiConsumer<String, List<ComponentT>> printerFunction) {
        PageController<ComponentT> pageController = prepareController(modelList, printerFunction);
        pageController.start();
    }

    private <ComponentT extends AbstractComponent> PageController<ComponentT> prepareController(List<ComponentT> modelList, BiConsumer<String, List<ComponentT>> printerFunction) {
        return new PageController<>(new PageModelImpl<>(modelList, numberOfElementsPerPage), new PageView<>(printerFunction));
    }

}
