package com.jor.service;

import com.jor.entity.Bucket;

public interface BucketService {
    Bucket createBucket(Bucket bucket);
    Boolean deleteBucket(Long id);
}
