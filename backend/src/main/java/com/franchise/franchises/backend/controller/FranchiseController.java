package com.franchise.franchises.backend.controller;

import com.franchise.franchises.backend.model.Franchise;
import com.franchise.franchises.backend.service.FranchiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
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
        return franchise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
