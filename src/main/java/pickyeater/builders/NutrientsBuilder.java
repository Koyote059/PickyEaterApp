//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package pickyeater.builders;

import pickyeater.basics.food.Nutrients;

public interface NutrientsBuilder {
    float getSimpleCarbs();
    void setSimpleCarbs(float simpleCarbs);
    float getComplexCarbs();
    void setComplexCarbs(float complexCarbs);
    float getFibers();
    void setFibers(float fibers);
    float getProteins();
    void setProteins(float proteins);
    float getAlcohol();
    void setAlcohol(float alcohol);
    float getSaturatedFats();
    void setSaturatedFats(float saturatedFats);
    float getUnSaturatedFats();
    void setUnSaturatedFats(float unSaturatedFats);
    float getTransFats();
    void setTransFats(float transFats);
    float getCarbs();
    float getFats();
    float getCalories();
    Nutrients build();
}
