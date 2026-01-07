package sk.pizzeria.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sk.pizzeria.demo.repository.UserRepository;
import sk.pizzeria.demo.service.CheckoutService;

@Controller
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final UserRepository userRepository;

    public CheckoutController(CheckoutService checkoutService, UserRepository userRepository) {
        this.checkoutService = checkoutService;
        this.userRepository = userRepository;
    }

    private Integer currentUserId(UserDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + principal.getUsername()))
                .getId();
    }

    @PostMapping("/checkout")
    public String checkout(
            @AuthenticationPrincipal UserDetails principal,
            @RequestParam(required = false) String deliveryAddress,
            @RequestParam(required = false) String note
    ) {
        Integer userId = currentUserId(principal);
        Integer orderId = checkoutService.createOrderFromCart(userId, deliveryAddress, note);
        return "redirect:/orders/" + orderId;
    }
}
