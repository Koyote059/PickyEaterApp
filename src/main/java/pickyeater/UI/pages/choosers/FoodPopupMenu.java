package pickyeater.UI.pages.choosers;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FoodPopupMenu extends JPopupMenu {

    private final JMenuItem deleteItem = new JMenuItem("Delete");
    private final JMenuItem editItem = new JMenuItem("Edit");

    public FoodPopupMenu(){
        add(deleteItem);
        add(editItem);
    }

    public void addDeleteListener(ActionListener l) {
        deleteItem.addActionListener(l);
    }

    public void addEditListener(ActionListener l) {
        editItem.addActionListener(l);
    }
}
