package pickyeater.UI.pages.leftbuttons;

public class PanelButtonsConverter {
    String command;

    public PanelButtonsConverter(String command) {
        this.command = command;
    }

    public PanelButtons Convert() {
        switch (command) {
            case "Daily Progress":
                return PanelButtons.PROGRESS;
            case "Meal Plan (Diet)":
                return PanelButtons.DIET;
            case "Groceries":
                return PanelButtons.GROCERIES;
            case "User":
                return PanelButtons.USER;
            case "Settings":
                return PanelButtons.SETTINGS;
            default:
                System.out.println("Error, check PanelButtonConverter");
                return PanelButtons.PROGRESS;
        }
    }
}
