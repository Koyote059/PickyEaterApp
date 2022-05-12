package pickyeater.UI.leftbuttons;

public class PanelButtonsConverter {

    String command;

    public PanelButtonsConverter(String command) {
        this.command = command;
    }

    public PanelButtons Convert(){
        if (command.equals("Daily Progress")){
            return PanelButtons.PROGRESS;
        } else if (command.equals("Meal Plan (Diet)")){
            return PanelButtons.DIET;
        } else if (command.equals("Food")){
            return PanelButtons.FOOD;
        } else if (command.equals("Groceries")){
            return PanelButtons.GROCERIES;
        } else if (command.equals("User")){
            return PanelButtons.USER;
        } else if (command.equals("Settings")){
            return PanelButtons.SETTINGS;
        } else {
            System.out.println("Error, check PanelButtonConverter");
            return PanelButtons.PROGRESS;
        }
    }
}
