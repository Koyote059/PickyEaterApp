package pickyeater.UI.pages.app.groceriespage.utils;

import pickyeater.basics.food.Ingredient;

import javax.swing.*;
import java.awt.*;

public class CheckBoxListRenderer extends TCheckBox implements ListCellRenderer<Ingredient> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Ingredient> list, Ingredient value, int index, boolean isSelected, boolean cellHasFocus) {
        setEnabled(list.isEnabled());
        setFont(Font.getFont(Font.DIALOG));
        setText(value.getName());
        if (isSelected) {
            setSelectionState((getSelectionState() + 1) % 3);
        }
        return this;
    }
}
