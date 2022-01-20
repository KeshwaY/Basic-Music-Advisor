package advisor.ui.view;

import java.io.Serializable;
import java.util.Scanner;

public abstract class ConsoleView implements View, Serializable {

    @Override
    public void render(String message) {
        System.out.println(message);
    }

    @Override
    public String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
