package pickyeater.executors;

import pickyeater.managers.EaterManager;

public class SettingsExecutor {
    private final EaterManager eaterManager;
    public SettingsExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public void deleteUser(){
        eaterManager.getUserManager().deleteUser(eaterManager.getUserManager().getUser().get());
    }
}
