package pickyeater.basics.food;

import java.util.Objects;

/**
 * @author Claudio Di Maio
 */

public class PickyNutrients implements Nutrients{
    private float proteins;
    private float complexCarbs;
    private float simpleCarbs;
    private float fibers;
    private float saturatedFats;
    private float unSaturatedFats;
    private float transFats;
    private float alcohol;

    public PickyNutrients(float proteins, float complexCarbs, float simpleCarbs, float fibers,
                          float saturatedFats, float unSaturatedFats, float transFats, float alcohol) {
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
    public float getProteins() {
        return proteins;
    }

    @Override
    public float getComplexCarbs() {
        return complexCarbs;
    }

    @Override
    public float getSimpleCarbs() {
        return simpleCarbs;
    }

    @Override
    public float getFibers() {
        return fibers;
    }

    @Override
    public float getSaturatedFats() {
        return saturatedFats;
    }

    @Override
    public float getUnSaturatedFats() {
        return unSaturatedFats;
    }

    @Override
    public float getTransFats() {
        return transFats;
    }

    @Override
    public float getAlcohol() {
        return alcohol;
    }

    @Override
    public float getCarbs() {
        return complexCarbs+simpleCarbs+fibers;
    }

    @Override
    public float getFats() {
        return saturatedFats+unSaturatedFats+transFats;
    }

    @Override
    public float getCalories() {
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