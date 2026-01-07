package sk.pizzeria.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import sk.pizzeria.demo.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @EntityGraph(attributePaths = {"customer", "cook"})
    List<Order> findByStatusAndCookIsNullOrderByIdAsc(String status);

    @EntityGraph(attributePaths = {"customer", "cook"})
    List<Order> findByCook_IdOrderByIdDesc(Integer cookId);

    @EntityGraph(attributePaths = {"customer", "cook"})
    Optional<Order> findById(Integer id);
}
