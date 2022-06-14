package pickyeater.UI.themes.filehandler;

import org.apache.pdfbox.pdmodel.ResourceCache;
import pickyeater.UI.settings.SettingsDatabase;
import pickyeater.UI.themes.SystemTheme;
import pickyeater.utils.Resources;

import java.awt.geom.RectangularShape;

public class ThemeHandler {
    public static void ThemesHandler(ReadWriteEnum readWriteEnum, ThemesEnum themesEnum) {
        if (readWriteEnum == ReadWriteEnum.READ) {
            ReadTheme();
        } else if (readWriteEnum == ReadWriteEnum.WRITE) {
            ChangeTheme(themesEnum);
        } else {
            System.out.println("Error in ThemeHandler -> ReadWriteEnum not found");
        }
    }

    public static ThemesEnum ReadTheme1() {
        String t = FileHandler.readFile();
        if (t.equals("")) {
            FileHandler.createFile();
            FileHandler.writeFile(ThemesEnum.LIGHT_THEME);
            SystemTheme.theme1();
            return ThemesEnum.LIGHT_THEME;
        } else {
            if (t.equals(ThemesEnum.LIGHT_THEME.toString())) {
                SystemTheme.theme1();
                return ThemesEnum.LIGHT_THEME;
            } else if (t.equals(ThemesEnum.DARK_THEME.toString())) {
                SystemTheme.theme2();
                return ThemesEnum.DARK_THEME;
            } else {
                System.out.println("Error in ThemeHandler -> ThemesEnum not found");
                FileHandler.deleteFile();
                SystemTheme.theme1();
                return null;
            }
        }
    }

    public static ThemesEnum ReadTheme() {
       ThemesEnum themesEnum = SettingsDatabase.getInstance(Resources.getSettings()).getTheme();
       switch (themesEnum){
           case DARK_THEME:
               SystemTheme.theme2();
               break;
           case LIGHT_THEME:
               SystemTheme.theme1();
               break;
       }
       return themesEnum;
    }

    public static void ChangeTheme(ThemesEnum themesEnum) {
        SettingsDatabase.getInstance(Resources.getSettings()).setTheme(themesEnum);
        ReadTheme();
    }
}