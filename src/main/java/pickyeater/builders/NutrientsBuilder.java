//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.food.Nutrients;

public interface NutrientsBuilder {

    void setSimpleCarbs(float simpleCarbs);

    void setComplexCarbs(float complexCarbs);

    void setFibers(float fibers);

    void setProteins(float proteins);

    void setAlcohol(float alcohol);

    void setSaturatedFats(float saturatedFats);

    void setUnSaturatedFats(float unSaturatedFats);

    void setTransFats(float transFats);

    Nutrients build();
}
