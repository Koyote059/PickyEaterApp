import org.junit.jupiter.api.Test;
import pickyeater.basics.food.Ingredient;
import pickyeater.basics.food.PickyQuantity;
import pickyeater.basics.food.QuantityType;
import pickyeater.builders.IngredientBuilder;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.database.PickyEatersDatabase;
import pickyeater.database.SQLPickyEaterDB;
import pickyeater.executors.ExecutorProvider;
import pickyeater.managers.EaterManager;
import pickyeater.managers.PickyEaterManager;
import pickyeater.utils.StringsUtils;

public class PopulateDatabase {

    PickyEatersDatabase pickyEatersDB = new SQLPickyEaterDB("DatabasePickEater.sqlite");
    EaterManager eaterManager = new PickyEaterManager(pickyEatersDB.getUserDatabase(), pickyEatersDB.getIngredientsDatabase(), pickyEatersDB.getMealsDatabase(), pickyEatersDB.getGroceriesDatabase());

    /**
     * Fruits
     */
    @Test
    public void populate(){
        ExecutorProvider.setEaterManager(eaterManager);

        Ingredient ingredient;
        ingredient = buildIngredient("Avocado", "1.5", "2", "9", "15", "300", "Pieces");
        System.out.println(ingredient);
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Banana", "0.088", "1.1", "23", "0.3", "57.5", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Cachi", "0.135", "0.58", "18.9", "0.19", "300", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Castagne", "0.5", "3.7", "8.3", "2.8", "11", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Ciliegie", "0.25", "1", "12", "0.3", "8", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Cocco", "0.157", "3.3", "14", "33", "2000", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Cocomero", "0.1", "0.6", "8", "0.2", "15000", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Datteri", "0.1", "2.5", "75", "0.4", "7", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Durian", "4", "1.47", "27", "5", "3000", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Fichi", "0.6", "0.9", "11.2", "0.2", "50", "Pieces");
        saveInDatabase(ingredient);
        /**
         * "Grams", "Milliliters", "Pieces"
         */

    }

    @Test
    public void populate2(){
        ExecutorProvider.setEaterManager(eaterManager);

        Ingredient ingredient;
        ingredient = buildIngredient("", "", "", "", "", "", "Pieces");
        System.out.println(ingredient);
        saveInDatabase(ingredient);
        ingredient = buildIngredient("", "", "", "", "", "", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("", "", "", "", "", "", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("", "", "", "", "", "", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("", "", "", "", "", "", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("", "", "", "", "", "", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("", "", "", "", "", "", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("", "", "", "", "", "", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("", "", "", "", "", "", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("", "", "", "", "", "", "Pieces");
        saveInDatabase(ingredient);
        /**
         * "Grams", "Milliliters", "Pieces"
         */

    }

    private Ingredient buildIngredient(String selectedName, String selectedPrice, String selectedProteins, String selectedCarbs, String selectedFats, String selectedGramsPerQuantity, String selectedItem) {
        float price, proteins, carbs, fats;
        float gramsPerQuantity = 1;
        try {
            price = Float.parseFloat(selectedPrice);
            proteins = Float.parseFloat(selectedProteins);
            carbs = Float.parseFloat(selectedCarbs);
            fats = Float.parseFloat(selectedFats);
        } catch (NumberFormatException e) {
            return null;
        }
        if (selectedName.equals("") || selectedItem == null) {
            return null;
        }
        QuantityType quantityType;
        int quantity = 100;
        switch (selectedItem) {
            case "Grams" -> quantityType = QuantityType.GRAMS;
            case "Milliliters" -> quantityType = QuantityType.MILLILITERS;
            case "Pieces" -> {
                quantityType = QuantityType.PIECES;
                quantity = 1;
            }
            default -> throw new IllegalStateException("Unexpected value: " + selectedItem);
        }
        if (!quantityType.equals(QuantityType.GRAMS)) {
            try {
                gramsPerQuantity = Float.parseFloat(selectedGramsPerQuantity);
                if(quantityType.equals(QuantityType.MILLILITERS)){
                    gramsPerQuantity/=100;
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }
        if (selectedName.length() > 20) {
            //JOptionPane.showMessageDialog(getParent(), "Name is too long!");
            return null;
        }
        if (selectedName.length() < 3) {
            //JOptionPane.showMessageDialog(getParent(), "Name is too short!");
            return null;
        }
        if (!StringsUtils.isAlpha(selectedName)) {
            //JOptionPane.showMessageDialog(getParent(), "Name can only contain alphanumeric characters!");
            return null;
        }
        if (price < 0) {
            //JOptionPane.showMessageDialog(getParent(), "Price cannot be negative!");
            return null;
        }
        if (proteins < 0) {
            //JOptionPane.showMessageDialog(getParent(), "Proteins cannot be negative!");
            return null;
        }
        if (carbs < 0) {
            //JOptionPane.showMessageDialog(getParent(), "Carbs cannot be negative!");
            return null;
        }
        if (fats < 0) {
            //JOptionPane.showMessageDialog(getParent(), "Fats cannot be negative!");
            return null;
        }
        if (fats + proteins + carbs > gramsPerQuantity*quantity) {
            //JOptionPane.showMessageDialog(getParent(), "The nutrients' sum cannot exceed the ingredient weight!");
            return null;
        }
        if (gramsPerQuantity < 0) {
            //JOptionPane.showMessageDialog(getParent(), "Grams per quantity cannot be negative!");
            return null;
        }



        /* Getting values */
        IngredientBuilder ingredientBuilder = ExecutorProvider.getCreateIngredientExecutor().getIngredientBuilder();
        ingredientBuilder.setName(StringsUtils.capitalize(selectedName));
        ingredientBuilder.setQuantity(new PickyQuantity(quantity, quantityType, gramsPerQuantity));
        ingredientBuilder.setPrice(price);
        NutrientsBuilder nutrientsBuilder = ExecutorProvider.getCreateIngredientExecutor().getNutrientsBuilder();

        nutrientsBuilder.setComplexCarbs(carbs);
        nutrientsBuilder.setProteins(proteins);
        nutrientsBuilder.setUnSaturatedFats(fats);


        ingredientBuilder.setNutrients(nutrientsBuilder.build());
        return ingredientBuilder.build();
    }

    private void saveInDatabase(Ingredient ingredient) {
        if (ingredient == null)
            return;
        if (ExecutorProvider.getCreateIngredientExecutor().existsIngredient(ingredient.getName())) {
            //JOptionPane.showMessageDialog(parent, ingredient.getName() + " already exists!");
            return;
        }
        ExecutorProvider.getCreateIngredientExecutor().saveIngredient(ingredient);
    }
}
