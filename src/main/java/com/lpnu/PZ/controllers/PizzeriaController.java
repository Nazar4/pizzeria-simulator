package com.lpnu.PZ.controllers;

import com.lpnu.PZ.dto.PizzeriaConfigurationDTO;
import com.lpnu.PZ.services.Kitchen;
import com.lpnu.PZ.services.Pizzeria;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PizzeriaController {

    @Getter
    private Pizzeria pizzeria;

    @PostMapping("/pizzeria")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> configurePizzeria(@RequestBody final PizzeriaConfigurationDTO configuration) {
        if (configuration.isValid()) {
            getPizzeria().configurePizzeria(configuration);
            return ResponseEntity.ok(configuration);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/pizzeria/cooks/{cookId}/stop")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> stopCook(@PathVariable(name = "cookId") final String cookId) {
        return getPizzeria().stopCookById(cookId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/pizzeria/cooks/{cookId}/resume")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> resumeCook(@PathVariable(name = "cookId") final String cookId) {
        return getPizzeria().resumeCookById(cookId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @Autowired()
    public void setPizzeria(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }
}
