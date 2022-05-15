package pickyeater.executors;

import pickyeater.algorithms.BodyFatCalculator;
import pickyeater.algorithms.DeurenbergCalculator;
import pickyeater.algorithms.HarrisBenedictCalculator;
import pickyeater.algorithms.NutrientsRequirementCalculator;
import pickyeater.basics.user.User;
import pickyeater.managers.EaterManager;
import pickyeater.managers.UserManager;

public class UserEditModeExecutor {
    private final EaterManager eaterManager;
    User user = null;
    public UserEditModeExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public User getUser() {
        if(user==null){
            user = eaterManager.getUserManager().getUser().get();
        }
        return user;
    }

    public void saveUser(User user) {
        UserManager userManager = eaterManager.getUserManager();
        if (!userManager.getUser().isEmpty()) {
            userManager.deleteUser(userManager.getUser().get());
        }
        userManager.saveUser(user);
    }

    public BodyFatCalculator getBFCalculator(){
        return new DeurenbergCalculator();
    }

    public NutrientsRequirementCalculator getNutrientsCalculator(){
        return new HarrisBenedictCalculator();
    }
}
