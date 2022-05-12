//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.food.Nutrients;

public interface NutrientsBuilder {

    void setSimpleCarbs(float simpleCarbs);
    float getSimpleCarbs();
    void setComplexCarbs(float complexCarbs);
    float getComplexCarbs();
    void setFibers(float fibers);
    float getFibers();
    void setProteins(float proteins);
    float getProteins();
    void setAlcohol(float alcohol);
    float getAlcohol();
    void setSaturatedFats(float saturatedFats);
    float getSaturatedFats();
    void setUnSaturatedFats(float unSaturatedFats);
    float getUnSaturatedFats();
    void setTransFats(float transFats);
    float getTransFats();

    float getCarbs();
    float getFats();


    float getCalories();

    Nutrients build();
}
