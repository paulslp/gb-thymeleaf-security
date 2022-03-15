package ru.gb.gbthymeleafwinter.dao;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
@Setter
@Getter
public class CartDao {

    private Set<Long> productIds = new HashSet<>();

    @PostConstruct
    void init() {
        productIds = new HashSet<>();
    }

    public boolean addProduct(Long productId) {
        return productIds.add(productId);
    }

    public void deleteById(Long id) {
        productIds.removeIf(it -> it.equals(id));
    }
}
