package com.franchise.franchises.backend.service;

import com.franchise.franchises.backend.model.Franchise;
import com.franchise.franchises.backend.repository.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FranchiseService {

    @Autowired
    private FranchiseRepository franchiseRepository;

    // Crear una nueva franquicia
    public Franchise createFranchise(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }

    // Obtener todas las franquicias
    public List<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }

    // Obtener una franquicia por ID
    public Optional<Franchise> getFranchiseById(Long id) {
        return franchiseRepository.findById(id);
    }

    // Obtener una lista de productos con mayor stock por sucursal de franquicia
    public Optional<Franchise> getLargerStockBranchesFranchise(Long id) {
        return franchiseRepository.findById(id);
    }

    // Eliminar una franquicia por ID
    public void deleteFranchise(Long id) {
        franchiseRepository.deleteById(id);
    }
}
