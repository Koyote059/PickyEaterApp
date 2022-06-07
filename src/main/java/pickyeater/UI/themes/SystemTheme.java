package pickyeater.UI.themes;

import com.formdev.flatlaf.FlatLightLaf;
import pickyeater.UI.themes.mydarkerlaf.MyDarkerLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class SystemTheme {
    public static void theme0() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setPEFont();
    }

    public static void setPEFont() {
        setUIFont(new javax.swing.plaf.FontUIResource("Bauhaus 93", Font.PLAIN, 16));
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }

    public static void theme1() {
        //UIManager.put("Button.background", Color.decode("#FFFFFF"));
        UIManager.put("ProgressBar.foreground", Color.decode("#B1EA9D"));
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        setDefaultFont();
    }

    public static void setDefaultFont() {
        setUIFont(new javax.swing.plaf.FontUIResource("Helvetica", Font.BOLD, 16));
    }

    public static void theme2() {
        UIManager.put("ProgressBar.foreground", Color.decode("#32AB5E"));
        try {
            UIManager.setLookAndFeel(new MyDarkerLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        setDefaultFont();
    }

    public static Color getButtonColor(){
        return UIManager.getColor("Button.background");
    }

    public static Color getPanelColor(){
        return UIManager.getColor("Panel.background");
    }
}
