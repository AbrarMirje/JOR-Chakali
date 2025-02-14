package com.jor.controller;

import com.jor.entity.Product;
import com.jor.response.CustomResponse;
import com.jor.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product APIs")
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public ResponseEntity<Product> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("price") Double price,
//            @RequestParam("date") String date, // Accept as String and parse to LocalDate
            @RequestParam("quantity") String quantity,
            @RequestParam("file") MultipartFile file) {

        // Convert date String to LocalDate
//        LocalDate localDate = LocalDate.parse(date);

        // Create Product object
        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
//        product.setDate(localDate);
        product.setQuantity(quantity);

        return ResponseEntity.ok(productService.addProduct(product, file));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    // ✅ Update Product (with optional image update)
    @PutMapping(value = "/update", consumes = {"multipart/form-data"})
    public ResponseEntity<CustomResponse> updateProduct(
            @RequestParam("id") Long id,
            @RequestParam("productName") String productName,
            @RequestParam("price") Double price,
//            @RequestParam("date") String date,
            @RequestParam("quantity") String quantity,
            @RequestParam(value = "file", required = false) MultipartFile file) {

//        LocalDate localDate = LocalDate.parse(date);

        Product product = new Product();
        product.setProductId(id);
        product.setProductName(productName);
        product.setPrice(price);
        product.setQuantity(quantity);

        Boolean isProductUpdated = productService.updateProduct(product, file);
        if (isProductUpdated) {
            return ResponseEntity.ok(new CustomResponse(true, "Product updated successfully"));
        }
        return ResponseEntity.ok(new CustomResponse(false, "Product not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteProduct(@PathVariable Long id) {
        Boolean isProductDeleted = productService.deleteProduct(id);
        if (isProductDeleted) {
            return ResponseEntity.ok(new CustomResponse(true, "Product deleted successfully"));
        }
        return ResponseEntity.ok(new CustomResponse(false, "Product not found"));
    }

}
