package sk.pizzeria.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.pizzeria.demo.service.PizzaService;

@Controller
public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping({"/", "/pizze"})
    public String list(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("pizzas", pizzaService.listActive(q));
        model.addAttribute("q", q);
        return "pizzas/list";
    }
}