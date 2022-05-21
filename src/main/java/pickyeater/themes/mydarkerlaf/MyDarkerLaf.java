package pickyeater.themes.mydarkerlaf;

import com.formdev.flatlaf.FlatDarkLaf;

public class MyDarkerLaf extends FlatDarkLaf {
    public static boolean setup() {
        return setup( new MyDarkerLaf() );
    }

    @Override
    public String getName() {
        return "MyDarkerLaf";
    }
}
