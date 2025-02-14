package com.jor.service;

import com.jor.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product, MultipartFile image);
    Product getProduct(Long id);
    Boolean updateProduct(Product product, MultipartFile image);
    List<Product> getAllProducts();
    Boolean deleteProduct(Long id);
}
