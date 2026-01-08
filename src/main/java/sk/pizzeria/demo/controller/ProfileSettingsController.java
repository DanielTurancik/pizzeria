package sk.pizzeria.demo.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sk.pizzeria.demo.dto.ChangePasswordForm;
import sk.pizzeria.demo.dto.ProfileEditForm;
import sk.pizzeria.demo.entity.User;
import sk.pizzeria.demo.repository.UserRepository;
import sk.pizzeria.demo.service.AccountService;
import sk.pizzeria.demo.validation.PasswordPolicy;

@Controller
@RequestMapping("/profil")
public class ProfileSettingsController {

    private final UserRepository userRepository;
    private final AccountService accountService;

    public ProfileSettingsController(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    private User currentUser(UserDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + principal.getUsername()));
    }

    @GetMapping("/edit")
    public String edit(@AuthenticationPrincipal UserDetails principal, Model model) {
        User me = currentUser(principal);

        ProfileEditForm form = new ProfileEditForm();
        form.setFirstName(me.getFirstName());
        form.setLastName(me.getLastName());
        form.setPhoneNumber(me.getPhoneNumber());
        form.setAddress(me.getAddress());
        form.setProfileImageUrl(me.getProfileImageUrl());

        model.addAttribute("form", form);
        return "profile/edit";
    }

    @PostMapping("/edit")
    public String save(@AuthenticationPrincipal UserDetails principal,
                       @Valid @ModelAttribute("form") ProfileEditForm form,
                       BindingResult br,
                       @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
                       Model model) {
        if (br.hasErrors()) return "profile/edit";

        try {
            User me = currentUser(principal);
            accountService.updateProfile(me.getId(), form, avatarFile);
            return "redirect:/profil?updated";
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "profile/edit";
        }
    }

    @GetMapping("/password")
    public String password(Model model) {
        model.addAttribute("form", new ChangePasswordForm());
        model.addAttribute("passwordHint", PasswordPolicy.hint());
        return "profile/password";
    }

    @PostMapping("/password")
    public String changePassword(@AuthenticationPrincipal UserDetails principal,
                                 @Valid @ModelAttribute("form") ChangePasswordForm form,
                                 BindingResult br,
                                 Model model) {
        if (br.hasErrors()) {
            model.addAttribute("passwordHint", PasswordPolicy.hint());
            return "profile/password";
        }

        try {
            User me = currentUser(principal);
            accountService.changePassword(me.getId(), form);
            return "redirect:/profil?passwordChanged";
        } catch (RuntimeException ex) {
            model.addAttribute("passwordHint", PasswordPolicy.hint());
            model.addAttribute("errorMessage", ex.getMessage());
            return "profile/password";
        }
    }
}
