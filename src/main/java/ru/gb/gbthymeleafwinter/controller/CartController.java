package ru.gb.gbthymeleafwinter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.gbthymeleafwinter.service.CartService;

import static org.springframework.util.CollectionUtils.isEmpty;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/all")
    public String getProductList(Model model) {
        if (isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
            return "anonymous-message";
        } else {
            model.addAttribute("products",
                    cartService.findCartProducts());
            return "cart-product-list";
        }
    }

    @GetMapping("/add/{productId}")
    public String addProduct(@PathVariable Long productId) {
        if (isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
            return "anonymous-message";
        } else {
            cartService.save(productId);
            return "redirect:/product/all";
        }
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam(name = "id") Long id) {
        cartService.deleteById(id);
        return "redirect:/cart/all";
    }

    private boolean isAnonymous(Authentication authentication) {
        return authentication == null || isEmpty(authentication.getAuthorities())
                || (authentication.getAuthorities().size() == 1
                && authentication.getAuthorities().stream()
                .findFirst().map(it -> it.getAuthority().matches("ROLE_ANONYMOUS"))
                .isPresent());
    }
}
