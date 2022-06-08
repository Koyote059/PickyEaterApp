package pickyeater.utils.pagesutils;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EatenMealsPopupMenu extends JPopupMenu {
    private final JMenuItem removeEatenMeal = new JMenuItem("Remove");

    public EatenMealsPopupMenu() {
        add(removeEatenMeal);
    }

    public void addRemoveListener(ActionListener l) {
        removeEatenMeal.addActionListener(l);
    }
}
