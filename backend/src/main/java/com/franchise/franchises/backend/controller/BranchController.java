package com.franchise.franchises.backend.controller;

import com.franchise.franchises.backend.model.Branch;
import com.franchise.franchises.backend.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    // Crear una nueva sucursal dentro de una franquicia
    @PostMapping("/franchise/{franchiseId}")
    public ResponseEntity<Branch> createBranch(@PathVariable Long franchiseId, @RequestBody Branch branch) {
        return new ResponseEntity<>(branchService.createBranch(franchiseId, branch), HttpStatus.CREATED);
    }

    // Obtener todas las sucursales
    @GetMapping
    public List<Branch> getAllBranches() {
        return branchService.getAllBranches();
    }

    // Obtener una sucursal por ID
    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        Optional<Branch> branch = branchService.getBranchById(id);
        return branch.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una sucursal por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }

    // Actualiza nombre de sucursal
    @PutMapping("/branch/{branchId}/update-name")
    public ResponseEntity<Branch> updateBranchName(@PathVariable Long branchId, @RequestParam String name) {
        Optional<Branch> branch = branchService.getBranchById(branchId);
        if (branch.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Branch updatedBranch = branch.get();
        updatedBranch.setName(name);
        branchService.saveBranch(updatedBranch);

        return ResponseEntity.ok(updatedBranch);
    }
}
