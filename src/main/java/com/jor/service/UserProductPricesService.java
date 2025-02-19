package com.jor.service;

import com.jor.entity.UserProductPrices;
import org.apache.catalina.User;

import java.util.List;

public interface UserProductPricesService {
    UserProductPrices addUserProductPrices(UserProductPrices userProductPrices);

    List<UserProductPrices> getUserProductsPrices();

    UserProductPrices getUserProductPrices(Long id);

    Boolean deleteUserProductPrices(Long id);

    Boolean updateUserProductPrices(UserProductPrices userProductPrices, Long id);

}
