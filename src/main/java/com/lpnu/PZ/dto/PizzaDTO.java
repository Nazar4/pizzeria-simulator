package com.lpnu.PZ.dto;

import com.lpnu.PZ.domain.Pizza;
import com.lpnu.PZ.domain.PizzaType;
import com.lpnu.PZ.utils.GlobalConstants;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
@Setter
public class PizzaDTO {
    private PizzaType pizzaType;
    private double timeToCreate;
    private double price;
    private String pizzaState;
    private String cookingStartTime;
    private String cookingEndTime;

    public static PizzaDTO mapToPizzaDTO(final Pizza pizza) {
        final PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setTimeToCreate(Math.round(pizza.getAdjustedTimeToCreate()));
        pizzaDTO.setPizzaType(pizza.getPizzaType());
        pizzaDTO.setPrice(pizza.getPizzaType().getPrice());
        pizzaDTO.setPizzaState(pizza.getPizzaState().getCookOperation().toString());
        pizzaDTO.setCookingStartTime(Optional.ofNullable(pizza.getLog().getStartDate())//
                .map(localDateTime -> localDateTime.format(DateTimeFormatter.ofPattern(GlobalConstants.PIZZA_LOG_FORMAT)))//
                .orElse("Not started yet"));
        pizzaDTO.setCookingEndTime(Optional.ofNullable(pizza.getLog().getEndDate())//
                .map(localDateTime -> localDateTime.format(DateTimeFormatter.ofPattern(GlobalConstants.PIZZA_LOG_FORMAT)))//
                .orElse("Not finished yet"));
        return pizzaDTO;
    }
}
