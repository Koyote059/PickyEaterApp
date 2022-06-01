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

    @Override
    public float getSimpleCarbs() {
        return simpleCarbs;
    }

    public void setSimpleCarbs(float simpleCarbs) {
        this.simpleCarbs = simpleCarbs;
    }

    @Override
    public float getComplexCarbs() {
        return complexCarbs;
    }

    public void setComplexCarbs(float complexCarbs) {
        this.complexCarbs = complexCarbs;
    }

    @Override
    public float getFibers() {
        return fibers;
    }

    public void setFibers(float fibers) {
        this.fibers = fibers;
    }

    @Override
    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    @Override
    public float getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(float alcohol) {
        this.alcohol = alcohol;
    }

    @Override
    public float getSaturatedFats() {
        return saturatedFats;
    }

    public void setSaturatedFats(float saturatedFats) {
        this.saturatedFats = saturatedFats;
    }

    @Override
    public float getUnSaturatedFats() {
        return unSaturatedFats;
    }

    public void setUnSaturatedFats(float unSaturatedFats) {
        this.unSaturatedFats = unSaturatedFats;
    }

    @Override
    public float getTransFats() {
        return transFats;
    }

    public void setTransFats(float transFats) {
        this.transFats = transFats;
    }

    @Override
    public float getCarbs() {
        return complexCarbs + simpleCarbs + fibers;
    }

    @Override
    public float getFats() {
        return saturatedFats + unSaturatedFats + transFats;
    }

    @Override
    public float getCalories() {
        NutrientType p = NutrientType.PROTEIN;
        NutrientType c = NutrientType.CARBOHYDRATE;
        NutrientType f = NutrientType.FAT;
        NutrientType a = NutrientType.ALCOHOL;
        return getProteins() * p.getCaloriesPerGram() + getCarbs() * c.getCaloriesPerGram() + getFats() * f.getCaloriesPerGram() + getAlcohol() * a.getCaloriesPerGram();
    }

    public Nutrients build() {
        return new PickyNutrients(this.proteins, this.complexCarbs, this.simpleCarbs, this.fibers, this.saturatedFats, this.unSaturatedFats, this.transFats, this.alcohol);
    }
}
