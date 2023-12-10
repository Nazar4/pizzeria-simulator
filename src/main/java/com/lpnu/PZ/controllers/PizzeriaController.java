package com.lpnu.PZ.controllers;

import com.lpnu.PZ.dto.CookDTO;
import com.lpnu.PZ.dto.PizzeriaConfigurationDTO;
import com.lpnu.PZ.services.Pizzeria;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/pizzeria/cooks")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCooks() {
        List<CookDTO> cooks = getPizzeria().getCooks();
        return ResponseEntity.ok(cooks);
    }

    @GetMapping("/pizzeria/cooks/{cookId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCooks(@PathVariable(name = "cookId") final String cookId) {
        Optional<CookDTO> cook = getPizzeria().getCookById(cookId);
        return cook.isPresent() ? ResponseEntity.ok(cook.get()) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/pizzeria/cooks/{cookId}/stop")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> stopCook(@PathVariable(name = "cookId") final String cookId) {
        return getPizzeria().stopCookById(cookId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/pizzeria/cooks/{cookId}/resume")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> resumeCook(@PathVariable(name = "cookId") final String cookId) {
        return getPizzeria().resumeCookById(cookId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @Autowired()
    public void setPizzeria(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }
}
