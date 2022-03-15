package ru.gb.gbthymeleafwinter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.gbthymeleafwinter.dao.CartDao;
import ru.gb.gbthymeleafwinter.entity.Product;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartDao cartDao;

    private final ProductService productService;

    public void save(Long productId) {
        cartDao.addProduct(productId);
    }

    public Set<Product> findCartProducts() {
        return productService.findAllByIdIn(cartDao.getProductIds());
    }

    public void deleteById(Long id) {
        cartDao.deleteById(id);
    }
}
