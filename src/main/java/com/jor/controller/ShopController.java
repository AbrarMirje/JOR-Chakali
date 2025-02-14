package com.jor.controller;

import com.jor.entity.Shop;
import com.jor.response.CustomResponse;
import com.jor.service.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
@CrossOrigin("*")
@Tag(name = "Shop APIs")
public class ShopController {

    private final ShopService shopService;

    @PostMapping("/add")
    public ResponseEntity<Shop> saveShop(@Valid @RequestBody Shop shop){
        return ResponseEntity.ok(shopService.addShop(shop));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Shop>> getShops(){
        return ResponseEntity.ok(shopService.getShops());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable Long id){
        return ResponseEntity.ok(shopService.getShop(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteShop(@PathVariable Long id){
        Boolean isShopDeleted = shopService.deleteShop(id);
        if (isShopDeleted){
            return ResponseEntity.ok(new CustomResponse(true, "Shop deleted successfully"));
        }
        return ResponseEntity.ok(new CustomResponse(false, "Shop not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateShop(@PathVariable Long id, @Valid @RequestBody Shop shop){
        Boolean isShopUpdated = shopService.updateShop(shop, id);
        if (isShopUpdated){
            return ResponseEntity.ok(new CustomResponse(true, "Shop updated successfully"));
        }
        return ResponseEntity.ok(new CustomResponse(false, "Shop not found"));
    }

    @GetMapping("/by-location/{locationId}")
    public ResponseEntity<List<Shop>> getShopsByLocation(@PathVariable Long locationId) {
        List<Shop> shops = shopService.getShopsByLocationId(locationId);
        return ResponseEntity.ok(shops);
    }

}
