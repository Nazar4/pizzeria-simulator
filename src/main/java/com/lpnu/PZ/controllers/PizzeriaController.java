package com.lpnu.PZ.controllers;

import com.lpnu.PZ.dto.ClientDTO;
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
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pizzeria/clients")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientDTO>> getClients() {
        return ResponseEntity.ok(pizzeria.getAllClients());
    }

    @GetMapping("/pizzeria/clients/{clientName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientDTO> getClientByName(@PathVariable(name = "clientName") final String clientName) {
        return getPizzeria().getClientByName(clientName)//
                .map(client -> ResponseEntity.ok(ClientDTO.mapToClientDTO(client)))//
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pizzeria/cooks")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CookDTO>> getCooks() {
        return ResponseEntity.ok(getPizzeria().getCooks());
    }

    @GetMapping("/pizzeria/cooks/{cookId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CookDTO> getCooks(@PathVariable(name = "cookId") final String cookId) {
        return getPizzeria().getCookById(cookId)//
                .map(ResponseEntity::ok)//
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/pizzeria/cooks/{cookId}/stop")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> stopCook(@PathVariable(name = "cookId") final String cookId) {
        return getPizzeria().stopCookById(cookId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/pizzeria/cooks/{cookId}/resume")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> resumeCook(@PathVariable(name = "cookId") final String cookId) {
        return getPizzeria().resumeCookById(cookId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @Autowired()
    public void setPizzeria(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }
}
