package pickyeater.themes;

import pickyeater.themes.filehandler.ThemeHandler;
import pickyeater.themes.filehandler.ThemesEnum;

import javax.swing.*;
import java.awt.*;

public class ColorButtons {
    private ThemesEnum te;
    public void ColorLeftButtons(JButton btSelected, JButton bt1, JButton bt2, JButton bt3, JButton bt4) {
        this.te = new ThemeHandler().ReadTheme();
        ColorButtonGreen(btSelected);
        ColorButtonWhite(bt1);
        ColorButtonWhite(bt2);
        ColorButtonWhite(bt3);
        ColorButtonWhite(bt4);
    }

    public void ColorButtonGreen(JButton btSelected) {
        this.te = new ThemeHandler().ReadTheme();
        if (te == ThemesEnum.LIGHT_THEME) {
            btSelected.setBackground(Color.decode("#B1EA9D"));
        } else if (te == ThemesEnum.DARK_THEME) {
            btSelected.setBackground(Color.decode("#32AB5E"));
        } else if (te == ThemesEnum.GREEN_THEME) {

        }
    }

    public void ColorButtonWhite(JButton btSelected) {
        this.te = new ThemeHandler().ReadTheme();
        if (te == ThemesEnum.LIGHT_THEME) {
            btSelected.setBackground(Color.decode("#FFFFFF"));
        } else if (te == ThemesEnum.DARK_THEME) {
            btSelected.setBackground(Color.decode("#505454"));
        } else if (te == ThemesEnum.GREEN_THEME) {

        }
    }
}
