//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.food.Nutrients;

public interface NutrientsBuilder {

    void setSimpleCarbs(double simpleCarbs);

    void setComplexCarbs(double complexCarbs);

    void setFibers(double fibers);

    void setProteins(double proteins);

    void setAlcohol(double alcohol);

    void setSaturatedFats(double saturatedFats);

    void setUnSaturatedFats(double unSaturatedFats);

    void setTransFats(double transFats);

    Nutrients build();
}
