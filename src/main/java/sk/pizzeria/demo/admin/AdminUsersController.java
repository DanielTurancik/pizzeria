package sk.pizzeria.demo.admin;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sk.pizzeria.demo.dto.AdminUserEditForm;
import sk.pizzeria.demo.service.AdminUserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {

    private final AdminUserService adminUserService;

    public AdminUsersController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("q", q);
        model.addAttribute("users", adminUserService.searchUsers(q));
        return "admin/users/list";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("user", adminUserService.getUser(id));
        model.addAttribute("allRoles", adminUserService.getAllRoles());
        model.addAttribute("form", adminUserService.toForm(id));
        return "admin/users/edit";
    }

    @PostMapping("/{id}/edit")
    public String save(
            @PathVariable Integer id,
            @Valid @ModelAttribute("form") AdminUserEditForm form,
            BindingResult br,
            Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("user", adminUserService.getUser(id));
            model.addAttribute("allRoles", adminUserService.getAllRoles());
            return "admin/users/edit";
        }
        adminUserService.updateUser(id, form);
        return "redirect:/admin/users?updated";
    }

    @PostMapping("/{id}/toggle-enabled")
    public String toggleEnabled(@PathVariable Integer id) {
        adminUserService.toggleEnabled(id);
        return "redirect:/admin/users";
    }
}
