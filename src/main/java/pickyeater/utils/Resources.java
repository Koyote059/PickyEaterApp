package pickyeater.utils;

import pickyeater.UI.themes.filehandler.ThemeHandler;
import pickyeater.UI.themes.filehandler.ThemesEnum;

import java.io.File;

public class Resources {

    public static String getDatabase(){ return "DatabasePickEater.sqlite"; }
    public static String getMPGeneratorSettings() { return "res" + File.separator + "MPGeneratorSettings";}
    public static String getSedentaryLSPic() { return "res" + File.separator + "images" + File.separator + "lifestyle" + File.separator + "S.png";}
    public static String getSlightlyActiveLSPic() { return "res" + File.separator + "images" + File.separator + "lifestyle" + File.separator + "SA.png";}
    public static String getActiveLSPic() { return "res" + File.separator + "images" + File.separator + "lifestyle" + File.separator + "A.png";}
    public static String getVeryActiveLSPic() { return "res" + File.separator + "images" + File.separator + "lifestyle" + File.separator + "VA.png";}
    public static String getLoseWeightPic() { return "res" + File.separator + "images" + File.separator + "goal" + File.separator + "LW.png";}
    public static String getMaintainWeightPic() { return "res" + File.separator + "images" + File.separator + "goal" + File.separator + "MW.png";}
    public static String getGainWeightPic() { return "res" + File.separator + "images" + File.separator + "goal" + File.separator + "GW.png";}
    public static String get404Pic() { return "res" + File.separator + "images" + File.separator + "404.png";}
    public static String getPELPic() { return "res" + File.separator + "images" + File.separator + "PEL.png";}
    public static String getEditAccountPic(){
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkEditAccountPic();
        } else {
            return getLightEditAccountPic();
        }
    }
    public static String getBinIcon(){
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkBinIcon();
        } else {
            return getLightBinIcon();
        }
    }
    public static String getPlusPic(){
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkPlusPic();
        } else {
            return getLightPlusPic();
        }
    }
    public static String getUpArrowPic(){
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkUpArrowPic();
        } else {
            return getLightUpArrowPic();
        }
    }

    public static String getDownArrowPic(){
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkDownArrowPic();
        } else {
            return getLightDownArrowPic();
        }
    }

    public static String getPencilPic(){
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkPencilPic();
        } else {
            return getLightPencilPic();
        }
    }

    public static String getUpdatePic(){
        if (ThemeHandler.ReadTheme() == ThemesEnum.LIGHT_THEME) {
            return getDarkUpdatePic();
        } else {
            return getLightUpdatePic();
        }
    }

    private static String getDarkEditAccountPic() { return "res" + File.separator + "images" + File.separator +
            "accountEditB.png";}
    private static String getLightEditAccountPic() { return "res" + File.separator + "images" + File.separator +
            "accountEditW.png";}
    private static String getDarkBinIcon() { return "res" + File.separator + "images" + File.separator + "binIcon.png";}
    private static String getLightBinIcon() { return "res" + File.separator + "images" + File.separator + "binIconW.png";}
    private static String getDarkPlusPic() { return "res" + File.separator + "images" + File.separator + "plusIcon.png";}
    private static String getLightPlusPic() { return "res" + File.separator + "images" + File.separator +
            "plusIconW.png";}
    private static String getDarkUpArrowPic() { return "res" + File.separator + "images" + File.separator +
            "upSmallArrow.png";}
    private static String getLightUpArrowPic() { return "res" + File.separator + "images" + File.separator +
            "upSmallArrowW.png";}
    private static String getDarkDownArrowPic() { return "res" + File.separator + "images" + File.separator +
            "downSmallArrow.png";}
    private static String getLightDownArrowPic() { return "res" + File.separator + "images" + File.separator +
            "downSmallArrowW.png";}
    private static String getDarkPencilPic() { return "res" + File.separator + "images" + File.separator +
            "pencilIcon.png";}
    private static String getLightPencilPic() { return "res" + File.separator + "images" + File.separator +
            "pencilIconW.png";}
    private static String getDarkUpdatePic() { return "res" + File.separator + "images" + File.separator +
            "updateIcon.png";}
    private static String getLightUpdatePic() { return "res" + File.separator + "images" + File.separator +
            "updateIconW.png";}
}
