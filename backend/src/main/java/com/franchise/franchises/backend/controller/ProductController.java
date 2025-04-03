package com.franchise.franchises.backend.controller;

import com.franchise.franchises.backend.model.Product;
import com.franchise.franchises.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Crear un nuevo producto en una sucursal
    @PostMapping("/branch/{branchId}")
    public ResponseEntity<Product> createProduct(@PathVariable Long branchId, @RequestBody Product product) {
        return new ResponseEntity<>(productService.createProduct(branchId, product), HttpStatus.CREATED);
    }

    // Obtener todos los productos
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Modificar el stock de un producto
    @PutMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestParam int stock) {
        return new ResponseEntity<>(productService.updateStock(id, stock), HttpStatus.OK);
    }

    // Actualiza nombre de producto
    @PutMapping("/product/{productId}/update-name")
    public ResponseEntity<Product> updateProductName(@PathVariable Long productId, @RequestParam String name) {
        Optional<Product> product = productService.getProductById(productId);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product updatedProduct = product.get();
        updatedProduct.setName(name);
        productService.saveProduct(updatedProduct);

        return ResponseEntity.ok(updatedProduct);
    }
}
