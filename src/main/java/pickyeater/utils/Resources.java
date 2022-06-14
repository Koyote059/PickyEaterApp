package pickyeater.utils;

import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;

import java.io.File;

public class Resources {
    public static String getDatabase() {
        return ":resource:DatabasePickEater.sqlite";
    }

    public static String getSettings(){
        return ":resource:Settings.sqlite";
    }

    public static String getMPGeneratorSettings() {
        return "res"+ File.separator + " MPGeneratorSettings";
    }

    public static String getSedentaryLSPic() {
        return "res/images/lifestyle/S.png";
    }

    public static String getSlightlyActiveLSPic() {
        return "res/images/lifestyle/SA.png";
    }

    public static String getActiveLSPic() {
        return "res/images/lifestyle/A.png";
    }

    public static String getVeryActiveLSPic() {
        return "res/images/lifestyle/VA.png";
    }

    public static String getLoseWeightPic() {
        return "res/images/goal/LW.png";
    }

    public static String getMaintainWeightPic() {
        return "res/images/goal/MW.png";
    }

    public static String getGainWeightPic() {
        return "res/images/goal/GW.png";
    }

    public static String get404Pic() {
        return "res/images/404.png";
    }

    public static String getPELPic() {
        return "res/images/PEL.png";
    }

    public static String getEditAccountPic() {
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkEditAccountPic();
        } else {
            return getLightEditAccountPic();
        }
    }

    private static String getDarkEditAccountPic() {
        return "res/images/accountEditB.png";
    }

    private static String getLightEditAccountPic() {
        return "res/images/accountEditW.png";
    }

    public static String getBinIcon() {
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkBinIcon();
        } else {
            return getLightBinIcon();
        }
    }

    private static String getDarkBinIcon() {
        return "res/images/binIcon.png";
    }

    private static String getLightBinIcon() {
        return "res/images/binIconW.png";
    }

    public static String getPlusPic() {
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkPlusPic();
        } else {
            return getLightPlusPic();
        }
    }

    private static String getDarkPlusPic() {
        return "res/images/plusIcon.png";
    }

    private static String getLightPlusPic() {
        return "res/images/plusIconW.png";
    }

    public static String getUpArrowPic() {
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkUpArrowPic();
        } else {
            return getLightUpArrowPic();
        }
    }

    private static String getDarkUpArrowPic() {
        return "res/images/upSmallArrow.png";
    }

    private static String getLightUpArrowPic() {
        return "res/images/upSmallArrowW.png";
    }

    public static String getDownArrowPic() {
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkDownArrowPic();
        } else {
            return getLightDownArrowPic();
        }
    }

    private static String getDarkDownArrowPic() {
        return "res/images/downSmallArrow.png";
    }

    private static String getLightDownArrowPic() {
        return "res/images/downSmallArrowW.png";
    }

    public static String getPencilPic() {
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkPencilPic();
        } else {
            return getLightPencilPic();
        }
    }

    private static String getDarkPencilPic() {
        return "res/images/pencilIcon.png";
    }

    private static String getLightPencilPic() {
        return "res/images/pencilIconW.png";
    }

    public static String getUpdatePic() {
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkUpdatePic();
        } else {
            return getLightUpdatePic();
        }
    }

    private static String getDarkUpdatePic() {
        return "res/images/updateIcon.png";
    }

    private static String getLightUpdatePic() {
        return "res/images/updateIconW.png";
    }

}
