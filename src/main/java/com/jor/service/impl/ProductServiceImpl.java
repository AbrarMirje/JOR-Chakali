package com.jor.service.impl;

import com.jor.entity.Product;
import com.jor.repository.ProductRepository;
import com.jor.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final S3Client s3Client;

    @Value("${app.s3.bucket}")
    private String bucketName;

    @Override
    public Product addProduct(Product product, MultipartFile image) {

        String originalFilename = image.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        try {
            // Upload file to S3
            PutObjectResponse putObjectResponse = s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    RequestBody.fromBytes(image.getBytes())
            );

            // Store S3 image URL in product entity
            String imageUrl = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
            product.setProductImage(imageUrl);

            // Save product with image URL
            product.setDate(LocalDate.now());
            return productRepository.save(product);

        } catch (IOException e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Boolean updateProduct(Product product, MultipartFile image) {
        Optional<Product> existingProductOpt = productRepository.findById(product.getProductId());

        if (existingProductOpt.isEmpty()) {
            throw new RuntimeException("Product not found with ID: " + product.getProductId());
        }

        Product existingProduct = existingProductOpt.get();
        existingProduct.setProductName(product.getProductName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDate(LocalDate.now());
        existingProduct.setQuantity(product.getQuantity());

        try {
            if (image != null && !image.isEmpty()) {
                // Delete old image from S3
                String oldImageUrl = existingProduct.getProductImage();
                if (oldImageUrl != null) {
                    String oldFileName = oldImageUrl.substring(oldImageUrl.lastIndexOf("/") + 1);
                    s3Client.deleteObject(DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(oldFileName)
                            .build());
                }

                // Upload new image
                String originalFilename = image.getOriginalFilename();
                String newFileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
                s3Client.putObject(
                        PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(newFileName)
                                .build(),
                        RequestBody.fromBytes(image.getBytes())
                );

                // Update product with new image URL
                String newImageUrl = "https://" + bucketName + ".s3.amazonaws.com/" + newFileName;
                existingProduct.setProductImage(newImageUrl);
            }

            // Save updated product
            productRepository.save(existingProduct);
            return true;

        } catch (IOException e) {
            throw new RuntimeException("Error updating product image", e);
        }
    }

    @Override
    public Boolean deleteProduct(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found with ID: " + id);
        }

        Product product = productOpt.get();

        // Delete image from S3 if exists
        String imageUrl = product.getProductImage();
        if (imageUrl != null) {
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build());
        }

        // Delete product from database
        productRepository.delete(product);
        return true;
    }
}
