package pickyeater.executors.user;

import pickyeater.algorithms.BodyFatCalculator;
import pickyeater.algorithms.DeurenbergCalculator;
import pickyeater.algorithms.HarrisBenedictCalculator;
import pickyeater.algorithms.NutrientsRequirementCalculator;
import pickyeater.basics.user.User;
import pickyeater.managers.EaterManager;

public class UserExecutor {
    private final EaterManager eaterManager;
    User user = null;

    public UserExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public User getUser() {
        if (user == null) {
            user = eaterManager.getUserManager().getUser().get();
        }
        return user;
    }

    public BodyFatCalculator getBFCalculator() {
        return new DeurenbergCalculator();
    }

    public NutrientsRequirementCalculator getNutrientsCalculator() {
        return new HarrisBenedictCalculator();
    }
}
