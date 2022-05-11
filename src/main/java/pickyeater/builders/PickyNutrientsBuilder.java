//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

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

    public Nutrients build() {
        return new PickyNutrients(this.proteins, this.complexCarbs, this.simpleCarbs, this.fibers, this.saturatedFats, this.unSaturatedFats, this.transFats, this.alcohol);
    }
}
