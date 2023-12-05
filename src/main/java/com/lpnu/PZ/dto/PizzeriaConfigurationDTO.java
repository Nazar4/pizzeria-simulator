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
}
