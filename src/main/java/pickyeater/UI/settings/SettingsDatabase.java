package pickyeater.UI.settings;

import pickyeater.UI.themes.filehandler.ThemesEnum;
import pickyeater.utils.MealPlanGeneratorBundle;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SettingsDatabase {

    private static SettingsDatabase instance = null;
    private Connection connection;

    private SettingsDatabase(String dbPath) {
        DBManager.setConnection(DBManager.JDBC_Driver_SQLite, String.format("jdbc:sqlite:%s", dbPath));

        try {
            DBManager.execute("CREATE TABLE IF NOT EXISTS Theme ( " +
                    "theme_name VARCHAR(16))");
            DBManager.execute("CREATE TABLE IF NOT EXISTS MPSettings (" +
                    "days INTEGER , meals INTEGER)");
            CachedRowSet mpSettingsRS = DBManager.executeStatement("SELECT * FROM MPSettings");
            if(!mpSettingsRS.next()){
                DBManager.execute("INSERT INTO MPSettings VALUES (7, 4)");
            }

            CachedRowSet themeRS = DBManager.executeStatement("SELECT * FROM Theme");
            if(!themeRS.next()){
                DBManager.execute("INSERT INTO Theme VALUES ('LIGHT_THEME')");
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static SettingsDatabase getInstance(String name) {
        if(instance==null) instance = new SettingsDatabase(name);
        return instance;
    }

    public ThemesEnum getTheme(){
        try {
            ResultSet resultSet = DBManager.executeStatement("SELECT * FROM Theme");
            resultSet.next();
            return ThemesEnum.valueOf(resultSet.getString("theme_name"));
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void setTheme(ThemesEnum theme){
        try {
            DBManager.execute("DELETE FROM Theme");
            DBManager.execute(String.format("INSERT INTO Theme VALUES ('%s')",theme.name()));
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public MealPlanGeneratorBundle getMPSettings(){
        MealPlanGeneratorBundle bundle = new MealPlanGeneratorBundle();
        try{
            ResultSet resultSet = DBManager.executeStatement("SELECT days, meals FROM MPSettings");
            if(!resultSet.next()) throw new SQLException();
            bundle.setDays(resultSet.getInt("days"));
            bundle.setMealsInADay(resultSet.getInt("meals"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bundle;
    }

    public void setMPSettings(MealPlanGeneratorBundle bundle){
        try{
            DBManager.execute("DELETE FROM MPSettings");
            DBManager.execute(String.format("INSERT INTO MPSettings VALUES (%d, %d)",bundle.getDays(),bundle.getMealsInADay()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
