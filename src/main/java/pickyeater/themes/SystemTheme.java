package pickyeater.themes;

import com.formdev.flatlaf.FlatLightLaf;
import pickyeater.themes.mydarkerlaf.MyDarkerLaf;

import javax.swing.*;
import java.awt.*;

public class SystemTheme {

    public void theme0(){
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    } catch (InstantiationException e) {
        throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
    } catch (UnsupportedLookAndFeelException e) {
        throw new RuntimeException(e);
    }

    UIManager.put("ProgressBar.foreground", Color.GREEN);
    UIManager.put("Button.background", Color.GREEN);
    }

    public void theme1(){
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        UIManager.put("Button.background", Color.decode("#FFFFFF"));
        UIManager.put("ProgressBar.foreground", Color.decode("#B1EA9D"));
//        UIManager.put("Panel.background", Color.decode("#B1EA9D"));
//        UIManager.put("List.background", Color.decode("#B1EA9D"));
//        UIManager.put("Table.background", Color.decode("#B1EA9D"));

        setUIFont(new javax.swing.plaf.FontUIResource("Helvetica",Font.BOLD,16));

        Color defaultGreen = Color.decode("#B1EA9D");
        Color defaultWhite = Color.decode("#FFFFFF");
    }

    public void theme2(){
        try {
            UIManager.setLookAndFeel( new MyDarkerLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        UIManager.put("Button.background", Color.decode("#000000"));
        UIManager.put("ProgressBar.foreground", Color.decode("#32AB5E"));
        //        UIManager.put("Panel.background", Color.decode("#B1EA9D"));
        //        UIManager.put("List.background", Color.decode("#B1EA9D"));
        //        UIManager.put("Table.background", Color.decode("#B1EA9D"));

        setUIFont(new javax.swing.plaf.FontUIResource("Helvetica",Font.BOLD,12));

        Color defaultGreen = Color.decode("#32AB5E");
        Color defaultWhite = Color.decode("#000000");
    }

    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }
}
