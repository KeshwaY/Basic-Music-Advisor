package advisor.ui.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ConsoleListenerView extends ConsoleView implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getNewValue().toString());
    }

}
