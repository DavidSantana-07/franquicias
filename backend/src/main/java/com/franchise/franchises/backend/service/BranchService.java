package com.franchise.franchises.backend.service;

import com.franchise.franchises.backend.model.Branch;
import com.franchise.franchises.backend.model.Franchise;
import com.franchise.franchises.backend.repository.BranchRepository;
import com.franchise.franchises.backend.repository.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private FranchiseRepository franchiseRepository;

    // Crear una nueva sucursal dentro de una franquicia
    public Branch createBranch(Long franchiseId, Branch branch) {
        Optional<Franchise> franchise = franchiseRepository.findById(franchiseId);
        if (franchise.isPresent()) {
            branch.setFranchise(franchise.get());
            return branchRepository.save(branch);
        } else {
            throw new RuntimeException("Franchise not found");
        }
    }

    // Obtener todas las sucursales
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    // Obtener una sucursal por ID
    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    // Eliminar una sucursal por ID
    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }

    // Actualiza la sucursal
    public void saveBranch(Branch branch) {
        branchRepository.save(branch);
    }
}
