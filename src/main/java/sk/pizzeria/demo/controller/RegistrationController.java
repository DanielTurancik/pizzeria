package sk.pizzeria.demo.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sk.pizzeria.demo.dto.RegisterForm;
import sk.pizzeria.demo.service.AccountService;
import sk.pizzeria.demo.validation.PasswordPolicy;

@Controller
public class RegistrationController {

    private final AccountService accountService;

    public RegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String form(Model model) {
        model.addAttribute("form", new RegisterForm());
        model.addAttribute("passwordHint", PasswordPolicy.hint());
        return "auth/register";
    }

    @PostMapping("/register")
    public String submit(@Valid @ModelAttribute("form") RegisterForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("passwordHint", PasswordPolicy.hint());
            return "auth/register";
        }

        try {
            accountService.register(form);
            return "redirect:/login?registered";
        } catch (RuntimeException ex) {
            model.addAttribute("passwordHint", PasswordPolicy.hint());
            model.addAttribute("errorMessage", ex.getMessage());
            return "auth/register";
        }
    }
}
