package pickyeater.basics.mealplan;

import java.time.LocalDate;
import java.util.List;

public interface MealPlan {
    List<DailyMealPlan> getDailyMealPlans();
    LocalDate getBeginningDay();
}
