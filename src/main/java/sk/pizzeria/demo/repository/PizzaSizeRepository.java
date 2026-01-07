package sk.pizzeria.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.pizzeria.demo.entity.PizzaSize;

public interface PizzaSizeRepository extends JpaRepository<PizzaSize, Integer> {
}