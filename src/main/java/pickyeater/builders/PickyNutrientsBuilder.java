//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.food.Nutrients;
import pickyeater.food.PickyNutrients;

public class PickyNutrientsBuilder implements NutrientsBuilder {
    private double proteins;
    private double complexCarbs;
    private double simpleCarbs;
    private double fibers;
    private double saturatedFats;
    private double unSaturatedFats;
    private double transFats;
    private double alcohol;

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public void setComplexCarbs(double complexCarbs) {
        this.complexCarbs = complexCarbs;
    }

    public void setSimpleCarbs(double simpleCarbs) {
        this.simpleCarbs = simpleCarbs;
    }

    public void setFibers(double fibers) {
        this.fibers = fibers;
    }

    public void setSaturatedFats(double saturatedFats) {
        this.saturatedFats = saturatedFats;
    }

    public void setUnSaturatedFats(double unSaturatedFats) {
        this.unSaturatedFats = unSaturatedFats;
    }

    public void setTransFats(double transFats) {
        this.transFats = transFats;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public Nutrients build() {
        return new PickyNutrients(this.proteins, this.complexCarbs, this.simpleCarbs, this.fibers, this.saturatedFats, this.unSaturatedFats, this.transFats, this.alcohol);
    }
}
