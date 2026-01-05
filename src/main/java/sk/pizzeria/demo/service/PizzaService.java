package sk.pizzeria.demo.service;

import org.springframework.stereotype.Service;

import sk.pizzeria.demo.entity.Pizza;
import sk.pizzeria.demo.repository.PizzaRepository;

import java.util.List;

@Service
public class PizzaService {
    private final PizzaRepository pizzaRepository;

    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public List<Pizza> listActive(String q) {
        if (q == null || q.isBlank()) {
            return pizzaRepository.findByActiveTrueOrderByNameAsc();
        }
        return pizzaRepository.findByNameContainingIgnoreCaseAndActiveTrue(q.trim());
    }
    public Pizza getBySlugOrThrow(String slug) {
        return pizzaRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Pizza not found: " + slug));
    }
}