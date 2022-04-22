package pickyeater.basics.food;

import java.util.Objects;

/**
 * @author Claudio Di Maio
 */

public class PickyNutrients implements Nutrients{
    private double proteins;
    private double complexCarbs;
    private double simpleCarbs;
    private double fibers;
    private double saturatedFats;
    private double unSaturatedFats;
    private double transFats;
    private double alcohol;

    public PickyNutrients(double proteins, double complexCarbs, double simpleCarbs, double fibers,
                          double saturatedFats, double unSaturatedFats, double transFats, double alcohol) {
        this.proteins = proteins;
        this.complexCarbs = complexCarbs;
        this.simpleCarbs = simpleCarbs;
        this.fibers = fibers;
        this.saturatedFats = saturatedFats;
        this.unSaturatedFats = unSaturatedFats;
        this.transFats = transFats;
        this.alcohol = alcohol;
    }

    @Override
    public double getProteins() {
        return proteins;
    }

    @Override
    public double getComplexCarbs() {
        return complexCarbs;
    }

    @Override
    public double getSimpleCarbs() {
        return simpleCarbs;
    }

    @Override
    public double getFibers() {
        return fibers;
    }

    @Override
    public double getSaturatedFats() {
        return saturatedFats;
    }

    @Override
    public double getUnSaturatedFats() {
        return unSaturatedFats;
    }

    @Override
    public double getTransFats() {
        return transFats;
    }

    @Override
    public double getAlcohol() {
        return alcohol;
    }

    @Override
    public double getCarbs() {
        return getComplexCarbs() + getSimpleCarbs() + getFibers();
    }

    @Override
    public double getFats() {
        return getSaturatedFats() + getUnSaturatedFats() + getTransFats();
    }

    @Override
    public double getCalories() {
        NutrientType p = NutrientType.PROTEIN;
        NutrientType c = NutrientType.CARBOHYDRATE;
        NutrientType f = NutrientType.FAT;
        NutrientType a = NutrientType.ALCOHOL;

        return getProteins() * p.getCaloriesPerGram() + getCarbs() * c.getCaloriesPerGram() + getFats() * f.caloriesPerGram + getAlcohol() * a.caloriesPerGram;
    }

    @Override
    public String toString() {
        return "PickyNutrients{" + "proteins=" + proteins + ", complexCarbs=" + complexCarbs + ", simpleCarbs=" + simpleCarbs + ", fibers=" + fibers + ", saturatedFats=" + saturatedFats + ", unSaturatedFats=" + unSaturatedFats + ", transFats=" + transFats + ", alcohol=" + alcohol + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PickyNutrients that = (PickyNutrients) o;
        return Double.compare(that.proteins, proteins) == 0 && Double.compare(that.complexCarbs, complexCarbs) == 0 && Double.compare(that.simpleCarbs, simpleCarbs) == 0 && Double.compare(that.fibers, fibers) == 0 && Double.compare(that.saturatedFats, saturatedFats) == 0 && Double.compare(that.unSaturatedFats, unSaturatedFats) == 0 && Double.compare(that.transFats, transFats) == 0 && Double.compare(that.alcohol, alcohol) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(proteins, complexCarbs, simpleCarbs, fibers, saturatedFats, unSaturatedFats, transFats, alcohol);
    }
}