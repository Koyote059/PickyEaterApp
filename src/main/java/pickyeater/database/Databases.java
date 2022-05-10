package pickyeater.database;

public class Databases {
    UserDatabase userDatabase;
    IngredientsDatabase ingredientsDatabase;
    MealsDatabase mealsDatabase;

    public Databases(UserDatabase userDatabase, IngredientsDatabase ingredientsDatabase, MealsDatabase mealsDatabase) {
        this.userDatabase = userDatabase;
        this.ingredientsDatabase = ingredientsDatabase;
        this.mealsDatabase = mealsDatabase;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public IngredientsDatabase getIngredientsDatabase() {
        return ingredientsDatabase;
    }

    public MealsDatabase getMealsDatabase() {
        return mealsDatabase;
    }
}
