package advisor.ui.model;

import java.util.List;

public interface PageModel<T> extends Model {

    List<T> getPage(int pageNumber);
    void setNumberOfElementsPerPage(int numberOfElementsPerPage);
    int getNumberOfElementsPerPage();
    double getMaxNumberOfPages();

}
