package pickyeater.UI.themes;

import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;

import javax.swing.*;
import java.awt.*;

public class ColorButtons {
    public static void ColorLeftButtons(JButton btSelected, JButton bt1, JButton bt2, JButton bt3, JButton bt4) {
        ThemesEnum te = ThemeHandler.ReadTheme();
        ColorButtonGreen(btSelected);
        ColorButtonWhite(bt1);
        ColorButtonWhite(bt2);
        ColorButtonWhite(bt3);
        ColorButtonWhite(bt4);
    }

    public static void ColorButtonGreen(JButton btSelected) {
        ThemesEnum te = ThemeHandler.ReadTheme();
        if (te == ThemesEnum.LIGHT_THEME) {
            btSelected.setBackground(Color.decode("#B1EA9D"));
        } else { // if (te == ThemesEnum.DARK_THEME)
            btSelected.setBackground(Color.decode("#32AB5E"));
        }
    }

    public static void ColorButtonWhite(JButton btSelected) {
        ThemesEnum te = ThemeHandler.ReadTheme();
        if (te == ThemesEnum.LIGHT_THEME) {
            btSelected.setBackground(Color.decode("#FFFFFF"));
        } else { // if (te == ThemesEnum.DARK_THEME)
            btSelected.setBackground(Color.decode("#505454"));
        }
    }

    public static Color getWhite() {
        ThemesEnum te = ThemeHandler.ReadTheme();
        if (te == ThemesEnum.LIGHT_THEME) {
            return (Color.decode("#FFFFFF"));
        } else { // if (te == ThemesEnum.DARK_THEME)
            return (Color.decode("#505454"));
        }
    }

    public static Color getGreen() {
        ThemesEnum te = ThemeHandler.ReadTheme();
        if (te == ThemesEnum.LIGHT_THEME) {
            return (Color.decode("#B1EA9D"));
        } else { // if (te == ThemesEnum.DARK_THEME)
            return (Color.decode("#32AB5E"));
        }
    }
}
