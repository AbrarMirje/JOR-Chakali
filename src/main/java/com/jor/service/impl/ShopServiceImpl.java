package com.jor.service.impl;

import com.jor.entity.Locations;
import com.jor.entity.Product;
import com.jor.entity.Shop;
import com.jor.repository.LocationsRepository;
import com.jor.repository.ProductRepository;
import com.jor.repository.ShopRepository;
import com.jor.service.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final LocationsRepository locationsRepository;
    private final ProductRepository productRepository;

    @Override
    public Shop addShop(Shop shop) {
        if (shop.getLocation() == null || shop.getLocation().getLocationId() == null) {
            throw new RuntimeException("Location is required");
        }

        // Fetch and set the existing location
        Locations existingLocation = locationsRepository.findById(shop.getLocation().getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found with id " + shop.getLocation().getLocationId()));
        shop.setLocation(existingLocation);

        // Ensure that existing products are attached to the persistence context
        if (shop.getProducts() != null && !shop.getProducts().isEmpty()) {
            List<Product> managedProducts = shop.getProducts().stream()
                    .map(product -> {
                        if (product.getProductId() != null) {
                            // Fetch product from DB to ensure it's managed
                            Product existingProduct = productRepository.findById(product.getProductId())
                                    .orElseThrow(() -> new RuntimeException("Product not found with id " + product.getProductId()));
                            existingProduct.setShop(shop);  // 🔥 Set shop reference explicitly
                            return existingProduct;
                        }
                        product.setShop(shop);  // 🔥 Ensure new products also reference the shop
                        return product;
                    })
                    .toList();

            shop.setProducts(managedProducts);
        }

        return shopRepository.save(shop);
    }



    @Override
    public Shop getShop(Long id) {
        return shopRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Shop not found with id " + id)
        );
    }

    @Override
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @Override
    @Transactional
    public Boolean deleteShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found with id " + id));

        // Unlink all products associated with this shop
        if (shop.getProducts() != null && !shop.getProducts().isEmpty()) {
            shop.getProducts().forEach(product -> product.setShop(null));
        }

        // Remove shop reference from location
        shop.setLocation(null);

        // Save changes before deleting shop
        shopRepository.save(shop);

        // Now delete the shop safely
        shopRepository.deleteById(id);
        return true;
    }


    @Override
    @Transactional
    public Boolean updateShop(Shop shop, Long id) {
        Shop oldShop = shopRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Shop not found with id " + id)
        );

        if (shop.getShopName() != null) oldShop.setShopName(shop.getShopName());
        if (shop.getLocation() != null) oldShop.setLocation(shop.getLocation());
        if (shop.getContact() != null) oldShop.setContact(shop.getContact());

        shopRepository.save(oldShop);
        return true;
    }

    @Override
    public List<Shop> getShopsByLocationId(Long locationId) {
        return shopRepository.findByLocation_LocationId(locationId);
    }
}
