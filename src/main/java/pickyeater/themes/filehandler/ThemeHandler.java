package pickyeater.themes.filehandler;

import pickyeater.themes.SystemTheme;

public class ThemeHandler {
    public void ThemesHandler(ReadWriteEnum readWriteEnum, ThemesEnum themesEnum) {
        if (readWriteEnum == ReadWriteEnum.READ) {
            ReadTheme();
        } else if (readWriteEnum == ReadWriteEnum.WRITE) {
            ChangeTheme(themesEnum);
        } else {
            System.out.println("Error in ThemeHandler -> ReadWriteEnum not found");
        }
    }

    public void ReadTheme(){
        FileHandler fileHandler = new FileHandler();
        String t = fileHandler.readFile();
        if (t == ""){
            fileHandler.createFile();
            fileHandler.writeFile(ThemesEnum.LIGHT_THEME);
            new SystemTheme().theme1();
        } else {
            if (t.equals(ThemesEnum.LIGHT_THEME.toString())){
                new SystemTheme().theme1();
            } else if (t.equals(ThemesEnum.DARK_THEME.toString())){
                new SystemTheme().theme2();
            } else if (t.equals(ThemesEnum.GREEN_THEME.toString())){
                new SystemTheme().theme0();
            } else {
                System.out.println("Error in ThemeHandler -> ThemesEnum not found");
                fileHandler.deleteFile();
                new SystemTheme().theme1();
            }
        }
    }

    public void ChangeTheme(ThemesEnum themesEnum) {
        FileHandler fileHandler = new FileHandler();

        fileHandler.deleteFile();
        fileHandler.createFile();
        fileHandler.writeFile(themesEnum);

        ReadTheme();
    }

}