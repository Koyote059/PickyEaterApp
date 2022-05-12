//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.food.NutrientType;
import pickyeater.basics.food.Nutrients;
import pickyeater.basics.food.PickyNutrients;

public class PickyNutrientsBuilder implements NutrientsBuilder {
    private float proteins;
    private float complexCarbs;
    private float simpleCarbs;
    private float fibers;
    private float saturatedFats;
    private float unSaturatedFats;
    private float transFats;
    private float alcohol;

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public void setComplexCarbs(float complexCarbs) {
        this.complexCarbs = complexCarbs;
    }

    public void setSimpleCarbs(float simpleCarbs) {
        this.simpleCarbs = simpleCarbs;
    }

    public void setFibers(float fibers) {
        this.fibers = fibers;
    }

    public void setSaturatedFats(float saturatedFats) {
        this.saturatedFats = saturatedFats;
    }

    public void setUnSaturatedFats(float unSaturatedFats) {
        this.unSaturatedFats = unSaturatedFats;
    }

    public void setTransFats(float transFats) {
        this.transFats = transFats;
    }

    public void setAlcohol(float alcohol) {
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
    public double getCarbs() {
        return complexCarbs + simpleCarbs + fibers;
    }

    @Override
    public double getFats() {
        return saturatedFats + unSaturatedFats + transFats;
    }

    @Override
    public double getAlcohol() {
        return alcohol;
    }

    @Override
    public float getCalories() {
        NutrientType p = NutrientType.PROTEIN;
        NutrientType c = NutrientType.CARBOHYDRATE;
        NutrientType f = NutrientType.FAT;
        NutrientType a = NutrientType.ALCOHOL;

        return (float) (getProteins() * p.getCaloriesPerGram() + getCarbs() * c.getCaloriesPerGram() + getFats() * f.getCaloriesPerGram() + getAlcohol() * a.getCaloriesPerGram());
    }


    public Nutrients build() {
        return new PickyNutrients(this.proteins, this.complexCarbs, this.simpleCarbs, this.fibers, this.saturatedFats, this.unSaturatedFats, this.transFats, this.alcohol);
    }
}
