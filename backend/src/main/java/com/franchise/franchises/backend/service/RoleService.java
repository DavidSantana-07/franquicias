package com.franchise.franchises.backend.service;

import com.franchise.franchises.backend.model.Role;
import com.franchise.franchises.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Crear un nuevo rol
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    // Obtener un rol por nombre
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
