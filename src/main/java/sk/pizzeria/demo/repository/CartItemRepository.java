package sk.pizzeria.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import sk.pizzeria.demo.entity.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @EntityGraph(attributePaths = {"pizzaSize", "pizzaSize.pizza"})
    List<CartItem> findByUser_IdOrderByIdAsc(Integer userId);

    Optional<CartItem> findByUser_IdAndPizzaSize_Id(Integer userId, Integer pizzaSizeId);

    Optional<CartItem> findByIdAndUser_Id(Integer id, Integer userId);

    void deleteByUser_IdAndPizzaSize_Id(Integer userId, Integer pizzaSizeId);
}
