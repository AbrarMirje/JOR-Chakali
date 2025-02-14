package com.jor.service.impl;


import com.jor.entity.Bucket;
import com.jor.entity.Shop;
import com.jor.repository.BucketRepository;
import com.jor.repository.ShopRepository;
import com.jor.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final ShopRepository shopRepository;


    @Override
    public Bucket createBucket(Bucket bucket) {
        // Pehle Shop ko fetch kar aur ensure kar ki wo managed hai
        Shop shop = shopRepository.findById(bucket.getShop().getShopId()).orElseThrow(
                () -> new RuntimeException("Shop not found with id " + bucket.getShop().getShopId())
        );

        // Pehle Bucket ke andar managed Shop set karo
        bucket.setShop(shop);

        // Ab Bucket ko save karo
        Bucket savedBucket = bucketRepository.save(bucket);

        // Shop ka bucket update karo aur save karo
        shop.setBucket(savedBucket);
        shopRepository.save(shop);

        return savedBucket;
    }



    @Override
    public Boolean deleteBucket(Long id) {
        Optional<Bucket> bucketOptional = bucketRepository.findById(id);

        if (bucketOptional.isPresent()) {
            Bucket bucket = bucketOptional.get();


            if (bucket.getShop() != null){
                Shop shop = bucket.getShop();
                shop.setBucket(null);
                shopRepository.save(shop);
            }

            bucketRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
