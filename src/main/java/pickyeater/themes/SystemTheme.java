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

    setPEFont();

    //UIManager.put("ProgressBar.foreground", Color.GREEN);
    //UIManager.put("Button.background", Color.GREEN);
    }

    public void theme1(){
        //UIManager.put("Button.background", Color.decode("#FFFFFF"));
        UIManager.put("ProgressBar.foreground", Color.decode("#B1EA9D"));
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        setDefaultFont();
    }

    public void theme2(){
        //UIManager.put("Button.background", Color.decode("#505454"));
        UIManager.put("ProgressBar.foreground", Color.decode("#32AB5E"));
        try {
            UIManager.setLookAndFeel( new MyDarkerLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        setDefaultFont();
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

    public static void setDefaultFont(){
        setUIFont(new javax.swing.plaf.FontUIResource("Helvetica",Font.BOLD,16));
    }

    public static void setPEFont(){
        setUIFont(new javax.swing.plaf.FontUIResource("Bauhaus 93",Font.PLAIN,16));
    }
}
