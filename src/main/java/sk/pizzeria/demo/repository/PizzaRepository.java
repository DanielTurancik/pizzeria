package sk.pizzeria.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import sk.pizzeria.demo.entity.Pizza;


import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    List<Pizza> findByActiveTrueOrderByNameAsc();
    @EntityGraph(attributePaths = "sizes")
    Optional<Pizza> findBySlug(String slug);
    List<Pizza> findByNameContainingIgnoreCaseAndActiveTrue(String q);
}
