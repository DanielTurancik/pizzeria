package sk.pizzeria.demo.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import sk.pizzeria.demo.dto.ChangePasswordForm;
import sk.pizzeria.demo.service.AccountService;
import sk.pizzeria.demo.validation.PasswordPolicy;

@Controller
@RequestMapping("/profil")
public class ProfilePasswordController {

    private final AccountService accountService;

    public ProfilePasswordController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/password")
    public String show(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new ChangePasswordForm());
        }
        model.addAttribute("passwordHint", PasswordPolicy.hint());
        return "profile/password";
    }

    @PostMapping("/password")
    public String submit(
            @SessionAttribute("userId") Integer userId,
            @Valid @ModelAttribute("form") ChangePasswordForm form,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("passwordHint", PasswordPolicy.hint());

        if (bindingResult.hasErrors()) {
            return "profile/password";
        }

        try {
            accountService.changePassword(userId, form);
            return "redirect:/profil?passwordChanged";
        } catch (IllegalStateException ex) {
            String msg = ex.getMessage() == null ? "" : ex.getMessage();

            if (msg.contains("Current password")) {
                bindingResult.rejectValue("currentPassword", "password.current.invalid", msg);
            } else if (msg.contains("confirmation")) {
                bindingResult.rejectValue("newPasswordConfirm", "password.confirm.mismatch", msg);
            } else if (msg.contains("Heslo musí") || msg.contains("Heslo")) {
                bindingResult.rejectValue("newPassword", "password.weak", msg);
            } else if (msg.contains("different")) {
                bindingResult.rejectValue("newPassword", "password.same", msg);
            } else {
                bindingResult.reject("password.change.failed", msg);
            }

            return "profile/password";
        }
    }
}
