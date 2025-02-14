package com.jor.controller;


import com.jor.entity.Bucket;
import com.jor.response.CustomResponse;
import com.jor.service.BucketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bucket")
@Tag(name = "Bucket APIs")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    @PostMapping("/add")
    public ResponseEntity<Bucket> addBucket(@Valid @RequestBody Bucket bucket){
        return ResponseEntity.ok(bucketService.createBucket(bucket));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteBucket(@PathVariable Long id){
        Boolean isBucketDeleted = bucketService.deleteBucket(id);
        if (isBucketDeleted){
            return ResponseEntity.ok(new CustomResponse(true, "Bucket deleted successfully"));
        }
        return ResponseEntity.ok(new CustomResponse(false, "Bucket not found"));
    }
}
