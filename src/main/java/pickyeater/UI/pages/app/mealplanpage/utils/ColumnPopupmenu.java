package pickyeater.UI.pages.app.mealplanpage.utils;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ColumnPopupmenu extends JPopupMenu {
    private final JMenuItem remove = new JMenuItem("Remove");
    private final JMenuItem takeUp = new JMenuItem("Take up");
    private final JMenuItem takeDown = new JMenuItem("Take down");


    public ColumnPopupmenu(){
        add(remove);
        add(takeUp);
        add(takeDown);
    }

    public void addRemoveListener(ActionListener l) {
        remove.addActionListener(l);
    }

    public void addTakeUpListener(ActionListener l) {
        takeUp.addActionListener(l);
    }

    public void addTakeDownListener(ActionListener l) {
        takeDown.addActionListener(l);
    }

}
