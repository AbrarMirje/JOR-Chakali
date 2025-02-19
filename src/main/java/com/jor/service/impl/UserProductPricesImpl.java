package com.jor.service.impl;

import com.jor.entity.UserProductPrices;
import com.jor.repository.UserProductPricesRepository;
import com.jor.service.UserProductPricesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProductPricesImpl implements UserProductPricesService {

    private final UserProductPricesRepository userProductPricesRepository;


    @Override
    public UserProductPrices addUserProductPrices(UserProductPrices userProductPrices) {

        return userProductPricesRepository.save(userProductPrices);
    }

    @Override
    public List<UserProductPrices> getUserProductsPrices() {
        return userProductPricesRepository.findAll();
    }

    @Override
    public UserProductPrices getUserProductPrices(Long id) {
        return userProductPricesRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User Product Price not found with id " + id)
        );

    }

    @Override
    public Boolean deleteUserProductPrices(Long id) {
        userProductPricesRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User Product Price not found with id " + id)
        );
        userProductPricesRepository.deleteById(id);
        return true;
    }

    @Override
    public Boolean updateUserProductPrices(UserProductPrices userProductPrices, Long id) {
        UserProductPrices oldPrice = userProductPricesRepository.findById(id).get();

        if (userProductPrices.getProduct() != null) oldPrice.setProduct(userProductPrices.getProduct());
        if (userProductPrices.getShop() != null) oldPrice.setShop(userProductPrices.getShop());
        if (userProductPrices.getCustomPrice() != null) oldPrice.setCustomPrice(userProductPrices.getCustomPrice());

        userProductPricesRepository.save(userProductPrices);
        return true;
    }
}
