package pickyeater.UI.pages.registerpage;

import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RegisterChangeTheme {
    public RegisterChangeTheme(JLabel txtChangeTheme) {
        txtChangeTheme.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (ThemeHandler.ReadTheme() == ThemesEnum.DARK_THEME) {
            try {
                BufferedImage binImage = ImageIO.read(ClassLoader.getSystemResourceAsStream("res/images/sun.png"));
                txtChangeTheme.setIcon(new ImageIcon(binImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
                txtChangeTheme.setText("");
            } catch (IOException | NullPointerException ignored) {
            }
        } else {
            try {
                ThemeHandler.ReadTheme();
                BufferedImage binImage = ImageIO.read(ClassLoader.getSystemResourceAsStream("res/images/moon.png"));
                txtChangeTheme.setIcon(new ImageIcon(binImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
                txtChangeTheme.setText("");
            } catch (IOException | NullPointerException ignored) {
            }
        }
    }
}
