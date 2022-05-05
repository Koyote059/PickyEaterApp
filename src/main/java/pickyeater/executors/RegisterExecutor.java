//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.executors;

import pickyeater.algorithms.BodyFatCalculator;
import pickyeater.algorithms.BodyFatCaluclatorWrong;
import pickyeater.algorithms.NutrientsRequiremenetCalculatorWrong;
import pickyeater.algorithms.NutrientsRequirementCalculator;
import pickyeater.builders.PickyUserBuilder;
import pickyeater.builders.UserBuilder;
import pickyeater.managers.EaterManager;
import pickyeater.basics.user.User;
import pickyeater.managers.UserManager;

public class RegisterExecutor {
    private final EaterManager eaterManager;
    UserBuilder userBuilder = null;
    public RegisterExecutor(EaterManager eaterManager) {
        this.eaterManager = eaterManager;
    }

    public UserBuilder getUserBuilder() {
        if(userBuilder==null){
            userBuilder = new PickyUserBuilder();
        }
        return userBuilder;
    }

    public void saveUser(User user) {
        UserManager userManager = eaterManager.getUserManager();
        userManager.saveUser(user);
    }

    public BodyFatCalculator getBFCalculator(){
        return new BodyFatCaluclatorWrong();
    }

    public NutrientsRequirementCalculator getNutrientsCalculator(){
        return new NutrientsRequiremenetCalculatorWrong();
    }
}
