package sk.pizzeria.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.pizzeria.demo.repository.UserRepository;
import sk.pizzeria.demo.service.CartService;

@Controller
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    private Integer currentUserId(UserDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + principal.getUsername()))
                .getId();
    }

    @GetMapping("/cart")
    public String view(@AuthenticationPrincipal UserDetails principal, Model model) {
        Integer userId = currentUserId(principal);
        model.addAttribute("items", cartService.getCart(userId));
        return "cart/view";
    }

    @PostMapping("/cart/add")
    public String add(
            @AuthenticationPrincipal UserDetails principal,
            @RequestParam Integer pizzaSizeId,
            @RequestParam(defaultValue = "1") Integer quantity,
            @RequestParam(required = false) String returnTo
    ) {
        Integer userId = currentUserId(principal);
        cartService.add(userId, pizzaSizeId, quantity == null ? 1 : quantity);

        if (returnTo != null && !returnTo.isBlank()) {
            return "redirect:" + returnTo;
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/item/{id}/inc")
    public String inc(@AuthenticationPrincipal UserDetails principal, @PathVariable Integer id) {
        Integer userId = currentUserId(principal);
        cartService.increment(userId, id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/item/{id}/dec")
    public String dec(@AuthenticationPrincipal UserDetails principal, @PathVariable Integer id) {
        Integer userId = currentUserId(principal);
        cartService.decrement(userId, id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/item/{id}/remove")
    public String remove(@AuthenticationPrincipal UserDetails principal, @PathVariable Integer id) {
        Integer userId = currentUserId(principal);
        cartService.remove(userId, id);
        return "redirect:/cart";
    }
}
