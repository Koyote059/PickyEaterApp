package pickyeater.themes;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class SystemTheme {

    public void theme1(){
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

    /*
    UIManager.put("ProgressBar.background", Color.ORANGE);
    UIManager.put("ProgressBar.foreground", Color.BLUE);
    UIManager.put("ProgressBar.selectionBackground", Color.RED);
    UIManager.put("ProgressBar.selectionForeground", Color.GREEN);
    */

    }

    public void theme2(){
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        UIManager.put("ProgressBar.foreground", Color.GREEN);
    }
}
