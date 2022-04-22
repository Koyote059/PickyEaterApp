package pickyeater.basics.food;

/**
 * @author Claudio Di Maio
 */

public class PickyNutrientsAccumulator implements NutrientsAccumulator {
    private double proteins = 0;
    private double complexCarbs = 0;
    private double simpleCarbs = 0;
    private double fibers = 0;
    private double saturatedFats = 0;
    private double unSaturatedFats = 0;
    private double transFats = 0;
    private double alcohol = 0;

    private double calories = 0;

    @Override
    public Nutrients generateNutrients() {
        return new PickyNutrients(proteins, complexCarbs, simpleCarbs, fibers, saturatedFats, unSaturatedFats, transFats, alcohol);
    }

    @Override
    public void sumNutrients(Nutrients nutrients) {
        proteins += nutrients.getProteins();
        complexCarbs += nutrients.getComplexCarbs();
        simpleCarbs += nutrients.getSimpleCarbs();
        fibers += nutrients.getFibers();
        saturatedFats += nutrients.getSaturatedFats();
        unSaturatedFats += nutrients.getUnSaturatedFats();
        transFats += nutrients.getTransFats();
        alcohol += nutrients.getAlcohol();
        calories += nutrients.getCalories();
    }

    @Override
    public String toString() {
        return "PickyAccumulator{" + "proteins=" + proteins + ", complexCarbs=" + complexCarbs + ", simpleCarbs=" + simpleCarbs + ", fibers=" + fibers + ", saturatedFats=" + saturatedFats + ", unSaturatedFats=" + unSaturatedFats + ", transFats=" + transFats + ", alcohol=" + alcohol + ", calories=" + calories + '}';
    }
}