package sk.pizzeria.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import sk.pizzeria.demo.entity.Pizza;
import sk.pizzeria.demo.entity.PizzaCategory;
import sk.pizzeria.demo.service.PizzaService;

@Controller
public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping({"/", "/pizze"})
    public String list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) PizzaCategory category,
            @RequestParam(required = false) String tag,
            Model model
    ) {
        model.addAttribute("pizzas", pizzaService.listActiveFiltered(q, category, tag));
        model.addAttribute("q", q);
        model.addAttribute("category", category);
        model.addAttribute("tag", tag);
        model.addAttribute("categories", PizzaCategory.values());
        return "pizzas/list";
    }

    @GetMapping("/pizza/{slug}")
    public String detail(@PathVariable String slug, Model model) {
        Pizza pizza = pizzaService.getBySlugOrThrow(slug);
        model.addAttribute("pizza", pizza);
        return "pizzas/detail";
    }
}
