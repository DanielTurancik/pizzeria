// FILE: src/main/java/sk/pizzeria/demo/service/CartService.java
package sk.pizzeria.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.pizzeria.demo.entity.CartItem;
import sk.pizzeria.demo.entity.PizzaSize;
import sk.pizzeria.demo.entity.User;
import sk.pizzeria.demo.repository.CartItemRepository;
import sk.pizzeria.demo.repository.PizzaSizeRepository;
import sk.pizzeria.demo.repository.UserRepository;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final PizzaSizeRepository pizzaSizeRepository;

    public CartService(
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            PizzaSizeRepository pizzaSizeRepository
    ) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.pizzaSizeRepository = pizzaSizeRepository;
    }

    @Transactional(readOnly = true)
    public List<CartItem> getCart(Integer userId) {
        return cartItemRepository.findByUser_IdOrderByIdAsc(userId);
    }

    @Transactional
    public void add(Integer userId, Integer pizzaSizeId, int quantity) {
        if (quantity <= 0) quantity = 1;

        CartItem item = cartItemRepository.findByUser_IdAndPizzaSize_Id(userId, pizzaSizeId)
                .orElseGet(() -> {
                    User u = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
                    PizzaSize ps = pizzaSizeRepository.findById(pizzaSizeId)
                            .orElseThrow(() -> new IllegalArgumentException("PizzaSize not found: " + pizzaSizeId));

                    CartItem ci = new CartItem();
                    ci.setUser(u);
                    ci.setPizzaSize(ps);
                    ci.setQuantity(0);
                    return ci;
                });

        item.setQuantity(item.getQuantity() + quantity);
        cartItemRepository.save(item);
    }

    @Transactional
    public void increment(Integer userId, Integer cartItemId) {
        CartItem item = cartItemRepository.findByIdAndUser_Id(cartItemId, userId)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found: " + cartItemId));
        item.setQuantity(item.getQuantity() + 1);
        cartItemRepository.save(item);
    }

    @Transactional
    public void decrement(Integer userId, Integer cartItemId) {
        CartItem item = cartItemRepository.findByIdAndUser_Id(cartItemId, userId)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found: " + cartItemId));

        int next = item.getQuantity() - 1;
        if (next <= 0) {
            cartItemRepository.delete(item);
            return;
        }
        item.setQuantity(next);
        cartItemRepository.save(item);
    }

    @Transactional
    public void remove(Integer userId, Integer cartItemId) {
        CartItem item = cartItemRepository.findByIdAndUser_Id(cartItemId, userId)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found: " + cartItemId));
        cartItemRepository.delete(item);
    }
}
