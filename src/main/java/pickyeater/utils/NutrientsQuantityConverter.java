package pickyeater.utils;

import pickyeater.basics.food.Nutrients;
import pickyeater.builders.NutrientsBuilder;
import pickyeater.builders.PickyNutrientsBuilder;

public class NutrientsQuantityConverter {

    public Nutrients convert(Nutrients nutrients, int newWeight){
        float weightRatio = newWeight/100f;
        NutrientsBuilder nutrientsBuilder = new PickyNutrientsBuilder();
        nutrientsBuilder.setAlcohol(nutrients.getAlcohol()*weightRatio);
        nutrientsBuilder.setTransFats(nutrients.getTransFats()*weightRatio);
        nutrientsBuilder.setSaturatedFats(nutrients.getUnSaturatedFats()*weightRatio);
        nutrientsBuilder.setUnSaturatedFats(nutrients.getUnSaturatedFats()*weightRatio);
        nutrientsBuilder.setProteins(nutrients.getProteins()*weightRatio);
        nutrientsBuilder.setSimpleCarbs(nutrients.getSimpleCarbs()*weightRatio);
        nutrientsBuilder.setComplexCarbs(nutrients.getComplexCarbs()*weightRatio);
        nutrientsBuilder.setFibers(nutrients.getFibers()*weightRatio);
        return nutrientsBuilder.build();
    }
}
