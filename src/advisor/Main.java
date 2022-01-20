package advisor;

import advisor.core.AdvisorImpl;
import advisor.core.AuthLinkGenerator;
import advisor.core.abstraction.Advisor;
import advisor.core.components.ComponentPrinter;
import advisor.ui.controller.MainMenuController;
import advisor.ui.model.MainMenuModel;
import advisor.ui.model.MainMenuModelImpl;
import advisor.ui.view.MainMenuView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int posOfEndPointFlag = -1;
        int posOfResourceFlag = -1;
        int posOfPageFlag = -1;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-access")) {
                posOfEndPointFlag = i;
            } else if (args[i].equals("-resource")) {
                posOfResourceFlag = i;
            } else if (args[i].equals("-page")) {
                posOfPageFlag = i;
            }
        }
        /*
        int posOfClientIdFlag = Arrays.binarySearch(args, "-client_id");
        int posOfClientSecretFlag = Arrays.binarySearch(args, "-client_secret");
        int posOfRedirectURIFlag = Arrays.binarySearch(args, "-redirect_uri");
         */
        String apiEndPoint = posOfEndPointFlag >= 0 ? args[posOfEndPointFlag + 1] : "https://accounts.spotify.com";
        String resourceURI = posOfResourceFlag >= 0 ? args[posOfResourceFlag + 1] : "https://api.spotify.com";
        int maxNumberOfItemsPerPage = posOfPageFlag >= 0 ? Integer.parseInt(args[posOfPageFlag + 1]) : 5;
        /*
        String clientId = args[posOfClientIdFlag + 1];
        String clientSecret = args[posOfClientSecretFlag + 1];
        String redirectURI = args[posOfRedirectURIFlag + 1];
         */
        String clientId = "48a35748313f4858ac07ac8dd0845661";
        String clientSecret = "17a1d9564f124e9da2d066f103f74314";
        String redirectURI = "http://localhost:8080";

        Advisor advisor = new AdvisorImpl();
        try {
            advisor.initialize(8080, apiEndPoint, resourceURI, clientId, clientSecret, redirectURI);
            MainMenuModel model = new MainMenuModelImpl(advisor, AuthLinkGenerator.generate(clientId, redirectURI));
            MainMenuView view = new MainMenuView();
            MainMenuController controller = new MainMenuController(model, view, new ComponentPrinter(), maxNumberOfItemsPerPage);
            controller.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
