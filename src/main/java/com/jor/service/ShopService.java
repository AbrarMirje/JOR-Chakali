package com.jor.service;

import com.jor.entity.Shop;

import java.util.List;

public interface ShopService {

    Shop addShop(Shop shop);

    Shop getShop(Long id);

    List<Shop> getShops();

    Boolean deleteShop(Long id);

    Boolean updateShop(Shop shop, Long id);

    List<Shop> getShopsByLocationId(Long locationId);
}
