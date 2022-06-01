package pickyeater.UI.themes.filehandler;

import pickyeater.UI.themes.SystemTheme;

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

    public static ThemesEnum ReadTheme() {
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
            } else if (t.equals(ThemesEnum.GREEN_THEME.toString())) {
                SystemTheme.theme0();
                return ThemesEnum.GREEN_THEME;
            } else {
                System.out.println("Error in ThemeHandler -> ThemesEnum not found");
                FileHandler.deleteFile();
                SystemTheme.theme1();
                return null;
            }
        }
    }

    public static void ChangeTheme(ThemesEnum themesEnum) {
        FileHandler.deleteFile();
        FileHandler.createFile();
        FileHandler.writeFile(themesEnum);
        ReadTheme();
    }
}