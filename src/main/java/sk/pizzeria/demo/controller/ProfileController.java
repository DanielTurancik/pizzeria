package sk.pizzeria.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.pizzeria.demo.entity.User;
import sk.pizzeria.demo.service.ProfileService;

@Controller
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profil")
    public String profile(@AuthenticationPrincipal UserDetails principal, Model model) {
        User me = profileService.getUserByEmailOrThrow(principal.getUsername());
        model.addAttribute("me", me);
        model.addAttribute("orders", profileService.getMyOrders(me.getId()));
        return "profile/view";
    }

    @PostMapping("/profil/orders/{id}/cancel")
    public String cancel(@AuthenticationPrincipal UserDetails principal, @PathVariable Integer id) {
        User me = profileService.getUserByEmailOrThrow(principal.getUsername());
        profileService.cancelOrder(me.getId(), id);
        return "redirect:/profil";
    }
}