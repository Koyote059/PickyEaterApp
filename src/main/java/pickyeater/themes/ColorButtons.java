package pickyeater.themes;

import pickyeater.themes.filehandler.ThemeHandler;
import pickyeater.themes.filehandler.ThemesEnum;

import javax.swing.*;
import java.awt.*;

public class ColorButtons {
    public void ColorLeftButtons(JButton btSelected, JButton bt1, JButton bt2, JButton bt3, JButton bt4) {
        ThemesEnum te = new ThemeHandler().ReadTheme();
        if (te == ThemesEnum.LIGHT_THEME) {
            btSelected.setBackground(Color.decode("#B1EA9D"));
            bt1.setBackground(Color.decode("#FFFFFF"));
            bt2.setBackground(Color.decode("#FFFFFF"));
            bt3.setBackground(Color.decode("#FFFFFF"));
            bt4.setBackground(Color.decode("#FFFFFF"));
        } else if (te == ThemesEnum.DARK_THEME) {
            btSelected.setBackground(Color.decode("#32AB5E"));
            bt1.setBackground(Color.decode("#484C4C"));
            bt2.setBackground(Color.decode("#484C4C"));
            bt3.setBackground(Color.decode("#484C4C"));
            bt4.setBackground(Color.decode("#484C4C"));
        } else if (te == ThemesEnum.GREEN_THEME) {

        }
    }

    public void ColorButtonGreen(JButton btSelected) {
        ThemesEnum te = new ThemeHandler().ReadTheme();
        if (te == ThemesEnum.LIGHT_THEME) {
            btSelected.setBackground(Color.decode("#B1EA9D"));
        } else if (te == ThemesEnum.DARK_THEME) {
            btSelected.setBackground(Color.decode("#32AB5E"));
        } else if (te == ThemesEnum.GREEN_THEME) {

        }
    }

    public void ColorButtonWhite(JButton btSelected) {
        ThemesEnum te = new ThemeHandler().ReadTheme();
        if (te == ThemesEnum.LIGHT_THEME) {
            btSelected.setBackground(Color.decode("#FFFFFF"));
        } else if (te == ThemesEnum.DARK_THEME) {
            btSelected.setBackground(Color.decode("#484C4C"));
        } else if (te == ThemesEnum.GREEN_THEME) {

        }
    }
}
