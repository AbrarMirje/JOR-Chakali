package com.jor.controller;

import com.jor.entity.Locations;
import com.jor.entity.Shop;
import com.jor.response.CustomResponse;
import com.jor.service.LocationsService;
import com.jor.service.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
@CrossOrigin("*")
@Tag(name = "Location APIs")
public class LocationController {

    private final LocationsService locationsService;

    @PostMapping("/add")
    public ResponseEntity<Locations> addLocation(@Valid @RequestBody Locations locations){
        return ResponseEntity.ok(locationsService.addLocation(locations));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Locations> getLocation(@PathVariable Long id){
        return ResponseEntity.ok(locationsService.getLocation(id));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Locations>> getLocations(){
        return ResponseEntity.ok(locationsService.getLocations());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteLocation(@PathVariable Long id){
        Boolean isLocationDeleted = locationsService.deleteLocation(id);
        if (isLocationDeleted){
            return ResponseEntity.ok(new CustomResponse(true, "Location deleted successfully"));
        }
        return ResponseEntity.ok(new CustomResponse(false, "Location not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateLocation(@PathVariable Long id,@Valid @RequestBody Locations locations){
        Boolean isLocationUpdated = locationsService.updateLocation(locations, id);
        if (isLocationUpdated){
            return ResponseEntity.ok(new CustomResponse(true, "Location updated successfully"));
        }
        return ResponseEntity.ok(new CustomResponse(false, "Location not found"));
    }

}
