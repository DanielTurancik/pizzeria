package sk.pizzeria.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.pizzeria.demo.repository.UserRepository;
import sk.pizzeria.demo.service.CookOrderService;

@Controller
@RequestMapping("/kuchar/orders")
public class CookOrdersController {

    private final CookOrderService cookOrderService;
    private final UserRepository userRepository;

    public CookOrdersController(CookOrderService cookOrderService, UserRepository userRepository) {
        this.cookOrderService = cookOrderService;
        this.userRepository = userRepository;
    }

    private Integer currentUserId(UserDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + principal.getUsername()))
                .getId();
    }

    @GetMapping
    public String dashboard(@AuthenticationPrincipal UserDetails principal, Model model) {
        Integer cookId = currentUserId(principal);
        model.addAttribute("openPending", cookOrderService.listOpenPending());
        model.addAttribute("mine", cookOrderService.listMine(cookId));
        return "kuchar/orders";
    }

    @PostMapping("/{id}/claim")
    public String claim(@AuthenticationPrincipal UserDetails principal, @PathVariable Integer id) {
        Integer cookId = currentUserId(principal);
        cookOrderService.claim(cookId, id);
        return "redirect:/kuchar/orders";
    }

    @PostMapping("/{id}/preparing")
    public String setPreparing(@AuthenticationPrincipal UserDetails principal, @PathVariable Integer id) {
        Integer cookId = currentUserId(principal);
        cookOrderService.setPreparing(cookId, id);
        return "redirect:/kuchar/orders";
    }

    @PostMapping("/{id}/ready")
    public String setReady(@AuthenticationPrincipal UserDetails principal, @PathVariable Integer id) {
        Integer cookId = currentUserId(principal);
        cookOrderService.setReady(cookId, id);
        return "redirect:/kuchar/orders";
    }
}
