package com.lpnu.PZ.dto;

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
        return cooksNumber>0&&cooksNumber<128&&pizzasNumber>0&&pizzasNumber<128&&minimalPizzaCreationTime>0&&minimalPizzaCreationTime<32384;
    }
}
