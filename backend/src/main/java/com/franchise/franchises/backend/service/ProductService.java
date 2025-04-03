package com.franchise.franchises.backend.service;

import com.franchise.franchises.backend.model.Branch;
import com.franchise.franchises.backend.model.Product;
import com.franchise.franchises.backend.repository.ProductRepository;
import com.franchise.franchises.backend.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    // Crear un nuevo producto en una sucursal
    public Product createProduct(Long branchId, Product product) {
        Optional<Branch> branch = branchRepository.findById(branchId);
        if (branch.isPresent()) {
            product.setBranch(branch.get());
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Branch not found");
        }
    }

    // Obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Obtener un producto por ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Eliminar un producto por ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Modificar el stock de un producto
    public Product updateStock(Long id, int stock) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            product.get().setStock(stock);
            return productRepository.save(product.get());
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    // Actualiza el producto
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

}
