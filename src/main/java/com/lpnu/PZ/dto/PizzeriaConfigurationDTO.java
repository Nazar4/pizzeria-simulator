package com.lpnu.PZ.dto;

import com.lpnu.PZ.domain.PizzaType;
import com.lpnu.PZ.utils.GlobalConstants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PizzeriaConfigurationDTO {
    private int cooksNumber;
    private int pizzasNumber;
    private int minimalPizzaCreationTime;
    private boolean randomGenerationStrategy;
    private boolean intervalGenerationStrategy;

    public boolean isValid() {
        return cooksNumber >= GlobalConstants.MIN_COOKS_NUMBER && cooksNumber < GlobalConstants.MAX_COOKS_NUMBER
                && pizzasNumber > 0 && pizzasNumber < PizzaType.values().length
                && minimalPizzaCreationTime > GlobalConstants.MINIMUM_TIME_TO_CREATE_PIZZA
                && minimalPizzaCreationTime < GlobalConstants.MAXIMUM_TIME_TO_CREATE_PIZZA;
    }
}
