package advisor.ui.controller;

import advisor.core.components.AbstractComponent;
import advisor.ui.model.PageModel;
import advisor.ui.view.PageView;

import java.util.List;
public class PageController<ComponentT extends AbstractComponent> implements Controller<PageModel<ComponentT>, PageView<ComponentT>> {

    private PageModel<ComponentT> model;
    private PageView<ComponentT> view;

    public PageController(PageModel<ComponentT> model, PageView<ComponentT> view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void start() {
        int currentPage = 0;
        getPageAndRender(currentPage);
        label:
        while (true) {
            String userInput = view.getInput();
            switch (userInput) {
                case "exit":
                    break label;
                case "next":
                    if (currentPage == model.getMaxNumberOfPages() - 1) {
                        view.render("No more pages.");
                        break;
                    }
                    currentPage++;
                    getPageAndRender(currentPage);
                    break;
                case "prev":
                    if (currentPage == 0) {
                        view.render("No more pages.");
                        break;
                    }
                    currentPage--;
                    getPageAndRender(currentPage);
                    break;
                default:
            }
        }
    }

    private void getPageAndRender(int pageNumber) {
        List<ComponentT> componentTS = model.getPage(pageNumber);
        view.render(String.format("---PAGE %d OF %d---", pageNumber + 1, (int) getModel().getMaxNumberOfPages()), componentTS);
    }

    @Override
    public void setModel(PageModel<ComponentT> model) {
        this.model = model;
    }

    @Override
    public PageModel<ComponentT> getModel() {
        return model;
    }

    @Override
    public void setView(PageView<ComponentT> view) {
        this.view = view;
    }

    @Override
    public PageView getView() {
        return view;
    }
}
