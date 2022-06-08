package pickyeater.utils.pagesutils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TriStateActionListener implements ActionListener {
    final protected Icon firstIcon;
    final protected Icon secondIcon;
    private State selectState = State.UNSELECTED;

    public TriStateActionListener(Icon firstIcon, Icon secondIcon) {
        this.firstIcon = firstIcon;
        this.secondIcon = secondIcon;
    }

    public static State getState(javax.swing.JCheckBox checkBox) {
        TriStateActionListener listener = null;
        for (ActionListener actionListener : checkBox.getActionListeners()) {
            if (actionListener instanceof TriStateActionListener) {
                listener = (TriStateActionListener) actionListener;
                break;
            }
        }
        if (listener == null)
            return null;
        return listener.selectState;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("A");
        javax.swing.JCheckBox checkBox = (javax.swing.JCheckBox) e.getSource();
        switch (selectState) {
            case SELECTED -> {
                selectState = State.CONFIRMED;
                checkBox.setIcon(secondIcon);
                checkBox.setSelected(true);
            }
            case CONFIRMED -> {
                selectState = State.UNSELECTED;
                checkBox.setIcon(null);
                checkBox.setSelected(false);
            }
            case UNSELECTED -> {
                selectState = State.SELECTED;
                checkBox.setIcon(firstIcon);
                checkBox.setSelected(true);
            }
            default -> throw new RuntimeException();
        }
        /*
        if (!checkBox.isSelected()){
            checkBox.setIcon(icon);
        }
        else if (checkBox.getIcon()!=null){
            checkBox.setIcon(null);
            checkBox.setSelected(false);
        }*/
    }

    public enum State {
        SELECTED, CONFIRMED, UNSELECTED
    }
}