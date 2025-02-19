package com.jor.controller;

import com.jor.entity.UserProductPrices;
import com.jor.response.CustomResponse;
import com.jor.service.UserProductPricesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Product Prices API")
@RequestMapping("/user-product-prices")
public class UserProductPricesController {

    private final UserProductPricesService userProductPricesService;

    @PostMapping("/add")
    public ResponseEntity<UserProductPrices> addUserProductPrice(@Valid @RequestBody UserProductPrices userProductPrices){
        UserProductPrices addedUserProductPrices = userProductPricesService.addUserProductPrices(userProductPrices);
        return ResponseEntity.ok(addedUserProductPrices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProductPrices> getUserProductPrice(@PathVariable Long id){
        UserProductPrices userProductPrices = userProductPricesService.getUserProductPrices(id);
        return ResponseEntity.ok(userProductPrices);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserProductPrices>> getAllUserProductPrices(){
        List<UserProductPrices> userProductPrices = userProductPricesService.getUserProductsPrices();
        return ResponseEntity.ok(userProductPrices);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteUserProductPrice(@PathVariable Long id){
        Boolean isProductPriceDeleted = userProductPricesService.deleteUserProductPrices(id);
        if (isProductPriceDeleted){
            return ResponseEntity.ok(new CustomResponse(true, "User Product Price deleted successfully"));
        }
        return ResponseEntity.ok(new CustomResponse(false, "User Product Price not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateUserProductPrice(@PathVariable Long id, @Valid @RequestBody UserProductPrices userProductPrices){
        Boolean isProductPriceUpdated = userProductPricesService.updateUserProductPrices(userProductPrices, id);
        if (isProductPriceUpdated){
            return ResponseEntity.ok(new CustomResponse(true, "User Product Price updated successfully"));
        }
        return ResponseEntity.ok(new CustomResponse(false, "User Product Price not found"));
    }
}
