//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pickyeater.builders;

import pickyeater.basics.food.Nutrients;

public interface NutrientsBuilder {

    void setSimpleCarbs(double simpleCarbs);
    double getSimpleCarbs();
    void setComplexCarbs(double complexCarbs);
    double getComplexCarbs();
    void setFibers(double fibers);
    double getFibers();
    void setProteins(double proteins);
    double getProteins();
    void setAlcohol(double alcohol);
    double getAlcohol();
    void setSaturatedFats(double saturatedFats);
    double getSaturatedFats();
    void setUnSaturatedFats(double unSaturatedFats);
    double getUnSaturatedFats();
    void setTransFats(double transFats);
    double getTransFats();

    double getCarbs();
    double getFats();


    float getCalories();

    Nutrients build();
}
