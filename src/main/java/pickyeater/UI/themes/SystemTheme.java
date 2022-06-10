package pickyeater.UI.themes;

import com.formdev.flatlaf.FlatLightLaf;
import pickyeater.UI.themes.mydarkerlaf.MyDarkerLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class SystemTheme {

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
        UIManager.put("Button.innerFocusWidth", 0);
        UIManager.put("TextField.arc", 0);
        UIManager.put("Component.borderWidth", 0);
        UIManager.put("TextComponent.arc", 0);
        UIManager.put("CheckBox.icon.borderWidth", 1);
        UIManager.put("CheckBox.icon.borderColor", Color.decode("#B1EA9D"));
        UIManager.put("CheckBox.icon.selectedBorderColor", Color.decode("#B1EA9D"));
        UIManager.put("CheckBox.icon.checkmarkColor", Color.decode("#B1EA9D"));
        UIManager.put("Component.focusColor", Color.decode("#B1EA9D"));
        UIManager.put("ProgressBar.foreground", Color.decode("#B1EA9D"));
        UIManager.put("TextField.selectionBackground", Color.decode("#B1EA9D"));
        UIManager.put("Button.hoverBorderColor", Color.decode("#B1EA9D"));
        UIManager.put("Button.focusedBorderColor", Color.decode("#B1EA9D"));
        UIManager.put("List.selectionForeground", Color.decode("#000000"));
        UIManager.put("List.selectionBackground", Color.decode("#B1EA9D"));
        UIManager.put("List.selectionInactiveForeground", Color.decode("#000000"));
        UIManager.put("List.selectionInactiveBackground", Color.decode("#83D078"));
        UIManager.put("Table.selectionForeground", Color.decode("#000000"));
        UIManager.put("Table.selectionBackground", Color.decode("#B1EA9D"));
        UIManager.put("Table.selectionInactiveForeground", Color.decode("#000000"));
        UIManager.put("Table.selectionInactiveBackground", Color.decode("#83D078"));
        UIManager.put("ComboBox.selectionForeground", Color.decode("#000000"));
        UIManager.put("ComboBox.selectionBackground", Color.decode("#B1EA9D"));
        UIManager.put("ComboBox.border", Color.decode("#FFFFFF"));
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
        UIManager.put("Button.innerFocusWidth", 0);
        UIManager.put("TextField.arc", 0);
        UIManager.put("Component.borderWidth", 0);
        UIManager.put("TextComponent.arc", 0);
        UIManager.put("CheckBox.icon.borderWidth", 1);
        UIManager.put("CheckBox.icon.borderColor", null);
        UIManager.put("CheckBox.icon.selectedBorderColor", null);
        UIManager.put("CheckBox.icon.checkmarkColor", null);
        UIManager.put("Component.focusColor", Color.decode("#32AB5E"));
        UIManager.put("ProgressBar.foreground", Color.decode("#32AB5E"));
        UIManager.put("TextField.selectionBackground", Color.decode("#32AB5E"));
        UIManager.put("Button.hoverBorderColor", Color.decode("#32AB5E"));
        UIManager.put("Button.focusedBorderColor", Color.decode("#32AB5E"));
        UIManager.put("List.selectionForeground", Color.decode("#FFFFFF"));
        UIManager.put("List.selectionBackground", Color.decode("#32AB5E"));
        UIManager.put("List.selectionInactiveForeground", Color.decode("#000000"));
        UIManager.put("List.selectionInactiveBackground", Color.decode("#83D078"));
        UIManager.put("Table.selectionForeground", Color.decode("#FFFFFF"));
        UIManager.put("Table.selectionBackground", Color.decode("#32AB5E"));
        UIManager.put("Table.selectionInactiveForeground", Color.decode("#000000"));
        UIManager.put("Table.selectionInactiveBackground", Color.decode("#83D078"));
        UIManager.put("ComboBox.selectionForeground", Color.decode("#FFFFFF"));
        UIManager.put("ComboBox.selectionBackground", Color.decode("#32AB5E"));
        UIManager.put("ComboBox.border", Color.decode("#000000"));

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

    public static Color getLabelColor(){
        return UIManager.getColor("Label.foreground");
    }
    public static Font getFont(){
        return new javax.swing.plaf.FontUIResource("Helvetica", Font.BOLD, 16);
    }
}

    /*
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


        //UIManager.put("Button.borderColor", Color.decode("#000000"));
        //UIManager.put("Component.borderColor", Color.decode("#000000"));
     */