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
import pickyeater.utils.StringToNumber;
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
        ingredient = buildIngredient("Fichi d India", "0.4736", "0.8", "13", "0.1", "160", "Pieces");
        System.out.println(ingredient);
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Fragole", "0.12", "0.7", "8", "0.3", "50", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Frutto drago Sml", "5", "4", "82", "11", "150", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Frutto drago Mid", "7.5", "4", "82", "11", "375", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Frutto drago Big", "10", "4", "82", "11", "600", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Frutto passione", "1", "2.2", "23", "0.7", "18", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Gelso", "0", "1.4", "10", "0.4", "2.1", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Giuggiole", "3.45", "1.2", "20.53", "0.2", "115", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Kiwi", "0.23", "1.1", "15", "0.5", "75", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Lamponi", "2.4", "1.2", "15", "0.7", "100", "Grams");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Limone", "0.10", "1.1", "9", "0.3", "110", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Mandarino", "0.18", "0.9", "17.6", "0.3", "100", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Mandorla", "2.5", "21.2", "21.69", "50", "1.2", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Mango", "2", "0.8", "15", "0.4", "500", "Pieces");
        saveInDatabase(ingredient);
        ingredient = buildIngredient("Mela", "0.38", "0.3", "14", "0.2", "225", "Pieces");
        saveInDatabase(ingredient);
        /**
         * "Grams", "Milliliters", "Pieces"
         */

    }

    private Ingredient buildIngredient(String selectedName, String selectedPrice, String selectedProteins, String selectedCarbs, String selectedFats, String selectedGramsPerQuantity, String selectedItem) {
        float price, proteins, carbs, fats;
        float gramsPerQuantity = 1;
        try {
            price = StringToNumber.convertPositiveFloatException(selectedPrice);
            proteins = StringToNumber.convertPositiveFloatException(selectedProteins);
            carbs = StringToNumber.convertPositiveFloatException(selectedCarbs);
            fats = StringToNumber.convertPositiveFloatException(selectedFats);
        } catch (NumberFormatException e) {
            System.err.println("PRASE FAIL");
            System.out.println(selectedName);
            return null;
        }
        if (selectedName.equals("") || selectedItem == null) {
            System.err.println("NAME VOID/SELECTED NAME = NULL");
            System.out.println(selectedName);
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
                gramsPerQuantity = StringToNumber.convertPositiveFloatException(selectedGramsPerQuantity);
                if(quantityType.equals(QuantityType.MILLILITERS)){
                    gramsPerQuantity/=100;
                }
            } catch (NumberFormatException e) {
                System.err.println("NumberFormatException");
                System.out.println(selectedName);
                return null;
            }
        }
        /**
         * Sistemo carbs, fats, proteins
         */
        carbs = carbs * gramsPerQuantity / 100;
        fats = fats * gramsPerQuantity / 100;
        proteins = proteins * gramsPerQuantity / 100;

        if (selectedName.length() > 20) {
            System.err.println("Name is too long!");
            System.out.println(selectedName);
            return null;
        }
        if (selectedName.length() < 3) {
            System.err.println("Name is too short!");
            System.out.println(selectedName);
            //JOptionPane.showMessageDialog(getParent(), "Name is too short!");
            return null;
        }
        if (!StringsUtils.isAlpha(selectedName)) {
            System.err.println("Name can only contain alphanumeric characters");
            System.out.println(selectedName);
            //JOptionPane.showMessageDialog(getParent(), "Name can only contain alphanumeric characters!");
            return null;
        }
        if (price < 0) {
            System.err.println("Price cannot be negative");
            System.out.println(selectedName);
            //JOptionPane.showMessageDialog(getParent(), "Price cannot be negative!");
            return null;
        }
        if (proteins < 0) {
            System.err.println("Proteins cannot be negative");
            System.out.println(selectedName);
            //JOptionPane.showMessageDialog(getParent(), "Proteins cannot be negative!");
            return null;
        }
        if (carbs < 0) {
            System.err.println("Carbs cannot be negative");
            System.out.println(selectedName);
            //JOptionPane.showMessageDialog(getParent(), "Carbs cannot be negative!");
            return null;
        }
        if (fats < 0) {
            System.err.println("Fats cannot be negative");
            System.out.println(selectedName);
            //JOptionPane.showMessageDialog(getParent(), "Fats cannot be negative!");
            return null;
        }
        if (fats + proteins + carbs > gramsPerQuantity*quantity) {
            System.err.println("The nutrients' sum cannot exceed the ingredient weight");
            System.out.println(selectedName);
            //JOptionPane.showMessageDialog(getParent(), "The nutrients' sum cannot exceed the ingredient weight!");
            return null;
        }
        if (gramsPerQuantity < 0) {
            System.err.println("Grams per quantity cannot be negative");
            System.out.println(selectedName);
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
            System.err.println(ingredient.getName() + " already exists!");
            return;
        }
        ExecutorProvider.getCreateIngredientExecutor().saveIngredient(ingredient);

        // If database needs to be cleaned:
        //deleteInDatabase(ingredient);
    }

    private void deleteInDatabase(Ingredient ingredient){
        if (ExecutorProvider.getCreateIngredientExecutor().existsIngredient(ingredient.getName())) {
            ExecutorProvider.getCreateIngredientExecutor().deleteIngredient(ingredient);
            System.out.println(ingredient.getName() + " Deleted!");
        }
    }
}
