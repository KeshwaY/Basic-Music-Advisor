package advisor.ui.model;

import java.util.List;

public class PageModelImpl<T> implements PageModel<T> {

    private final List<T> content;
    private int numberOfElementsPerPage;
    private double maxNumberOfPages;

    public PageModelImpl(List<T> content, int numberOfElementsPerPage) {
        this.content = content;
        this.numberOfElementsPerPage = numberOfElementsPerPage;
        calculateMaxNumberOfPages();
    }

    public int getNumberOfElementsPerPage() {
        return numberOfElementsPerPage;
    }

    public double getMaxNumberOfPages() {
        return maxNumberOfPages;
    }

    @Override
    public void setNumberOfElementsPerPage(int numberOfElementsPerPage) {
        this.numberOfElementsPerPage = numberOfElementsPerPage;
        calculateMaxNumberOfPages();
    }

    private void calculateMaxNumberOfPages() {
        this.maxNumberOfPages = Math.ceil(content.size() / (float) numberOfElementsPerPage);
    }

    @Override
    public List<T> getPage(int pageNumber) {
        return prepareContent(pageNumber);
    }

    private List<T> prepareContent(int pageNumber) {
        if (maxNumberOfPages < pageNumber) {
            return null;
        }
        int firstIndex = numberOfElementsPerPage * pageNumber;
        int secondIndex = firstIndex + numberOfElementsPerPage;
        return content.subList(Math.max(firstIndex, 0), Math.min(secondIndex, content.size()));
    }

}
