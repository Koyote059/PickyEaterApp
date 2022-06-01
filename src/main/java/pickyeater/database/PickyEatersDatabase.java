package pickyeater.database;

public interface PickyEatersDatabase {
    IngredientsDatabase getIngredientsDatabase();
    MealsDatabase getMealsDatabase();
    UserDatabase getUserDatabase();
    GroceriesDatabase getGroceriesDatabase();
}
