package advisor.ui.view;

import advisor.core.components.AbstractComponent;

import java.util.List;
import java.util.function.BiConsumer;

public class PageView<ComponentT extends AbstractComponent> extends ConsoleView {

    private final BiConsumer<String, List<ComponentT>> printerFunction;

    public PageView(BiConsumer<String, List<ComponentT>> printerFunction) {
        this.printerFunction = printerFunction;
    }

    public void render(String title, List<ComponentT> componentTS) {
        printerFunction.accept(title, componentTS);
    }

}
