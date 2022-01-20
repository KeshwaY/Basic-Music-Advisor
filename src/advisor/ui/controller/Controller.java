package advisor.ui.controller;


import advisor.ui.model.Model;
import advisor.ui.view.View;

public interface Controller<ModelT extends Model, ViewT extends View> {

    void start();
    void setModel(ModelT model);
    ModelT getModel();
    void setView(ViewT view);
    ViewT getView();

}
