package pickyeater.UI.app.mealplanpage.utils;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MealRowPopupMenu extends JPopupMenu {

    private final JMenuItem addToEatenMealsItem = new JMenuItem("Add to eaten meals");

    public MealRowPopupMenu() {
        add(addToEatenMealsItem);
    }

    public void addEatenMealsItemListener(ActionListener l){
        addToEatenMealsItem.addActionListener(l);
    }
}
