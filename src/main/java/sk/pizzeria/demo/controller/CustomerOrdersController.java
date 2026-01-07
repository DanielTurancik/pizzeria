package sk.pizzeria.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.pizzeria.demo.entity.Order;
import sk.pizzeria.demo.repository.OrderRepository;
import sk.pizzeria.demo.repository.UserRepository;

@Controller
public class CustomerOrdersController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CustomerOrdersController(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    private Integer currentUserId(UserDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + principal.getUsername()))
                .getId();
    }

    @GetMapping("/orders")
    public String myOrders(@AuthenticationPrincipal UserDetails principal, Model model) {
        Integer userId = currentUserId(principal);
        model.addAttribute("orders", orderRepository.findByCustomer_IdOrderByIdDesc(userId));
        return "orders/list";
    }

    @GetMapping("/orders/{id}")
    public String detail(@AuthenticationPrincipal UserDetails principal, @PathVariable Integer id, Model model) {
        Integer userId = currentUserId(principal);

        Order o = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        if (o.getCustomer() == null || o.getCustomer().getId() == null || !o.getCustomer().getId().equals(userId)) {
            throw new IllegalStateException("Not allowed.");
        }

        model.addAttribute("order", o);
        return "orders/detail";
    }
}