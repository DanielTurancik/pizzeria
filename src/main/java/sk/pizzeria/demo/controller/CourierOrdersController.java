package sk.pizzeria.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.pizzeria.demo.repository.UserRepository;
import sk.pizzeria.demo.service.CourierOrderService;

@Controller
@RequestMapping("/kurier/orders")
public class CourierOrdersController {

    private final CourierOrderService courierOrderService;
    private final UserRepository userRepository;

    public CourierOrdersController(CourierOrderService courierOrderService, UserRepository userRepository) {
        this.courierOrderService = courierOrderService;
        this.userRepository = userRepository;
    }

    private Integer currentUserId(UserDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + principal.getUsername()))
                .getId();
    }

    @GetMapping
    public String dashboard(@AuthenticationPrincipal UserDetails principal, Model model) {
        Integer courierId = currentUserId(principal);
        model.addAttribute("openReady", courierOrderService.listOpenReady());
        model.addAttribute("mine", courierOrderService.listMine(courierId));
        return "kurier/orders";
    }

    @PostMapping("/{id}/claim")
    public String claim(@AuthenticationPrincipal UserDetails principal, @PathVariable Integer id) {
        Integer courierId = currentUserId(principal);
        courierOrderService.claim(courierId, id);
        return "redirect:/kurier/orders";
    }

    @PostMapping("/{id}/delivering")
    public String setDelivering(@AuthenticationPrincipal UserDetails principal, @PathVariable Integer id) {
        Integer courierId = currentUserId(principal);
        courierOrderService.setDelivering(courierId, id);
        return "redirect:/kurier/orders";
    }

    @PostMapping("/{id}/delivered")
    public String setDelivered(@AuthenticationPrincipal UserDetails principal, @PathVariable Integer id) {
        Integer courierId = currentUserId(principal);
        courierOrderService.setDelivered(courierId, id);
        return "redirect:/kurier/orders";
    }
}