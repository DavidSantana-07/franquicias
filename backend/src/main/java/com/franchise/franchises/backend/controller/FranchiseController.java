package com.franchise.franchises.backend.controller;

import com.franchise.franchises.backend.model.Branch;
import com.franchise.franchises.backend.model.Franchise;
import com.franchise.franchises.backend.service.FranchiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/franchises")
public class FranchiseController {

    @Autowired
    private FranchiseService franchiseService;

    // Crear una nueva franquicia
    @PostMapping
    public ResponseEntity<Franchise> createFranchise(@RequestBody Franchise franchise) {
        return new ResponseEntity<>(franchiseService.createFranchise(franchise), HttpStatus.CREATED);
    }

    // Obtener todas las franquicias
    @GetMapping
    public List<Franchise> getAllFranchises() {
        return franchiseService.getAllFranchises();
    }

    // Obtener una franquicia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchiseById(@PathVariable Long id) {
        Optional<Franchise> franchise = franchiseService.getFranchiseById(id);
        return franchise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una franquicia por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchise(@PathVariable Long id) {
        franchiseService.deleteFranchise(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mayor/{id}")
    public ResponseEntity<Franchise> getLargerStockBranchesFranchise (@PathVariable Long id) {
        Optional<Franchise> franchise = franchiseService.getFranchiseById(id);

        if (franchise.isEmpty()) {
            return ResponseEntity.notFound().build(); // Manejo seguro si la franquicia no existe
        }

        Franchise originalFranchise = franchise.get();
        // Filtrar para obtener solo el producto con mayor stock en cada sucursal
        Optional<Franchise> filteredFranchise = Optional.of(new Franchise(
                originalFranchise.getId(),
                originalFranchise.getName(),
                originalFranchise.getBranches().stream()
                        .map(branch -> branch.getMaxStockProduct()
                                .map(product -> new Branch(branch.getId(), branch.getName(), franchise.get(), Collections.singletonList(product))))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList())
        ));
        return filteredFranchise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualiza nombre de franquicia
    @PutMapping("/{id}/update-name")
    public ResponseEntity<Franchise> updateFranchiseName(@PathVariable Long id, @RequestParam String name) {
        Optional<Franchise> franchise = franchiseService.getFranchiseById(id);
        if (franchise.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Franchise updatedFranchise = franchise.get();
        updatedFranchise.setName(name);
        franchiseService.save(updatedFranchise);

        return ResponseEntity.ok(updatedFranchise);
    }
}
