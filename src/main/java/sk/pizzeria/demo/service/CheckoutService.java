package sk.pizzeria.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.pizzeria.demo.entity.CartItem;
import sk.pizzeria.demo.entity.Order;
import sk.pizzeria.demo.entity.User;
import sk.pizzeria.demo.repository.CartItemRepository;
import sk.pizzeria.demo.repository.OrderRepository;
import sk.pizzeria.demo.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckoutService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CheckoutService(
            CartItemRepository cartItemRepository,
            OrderRepository orderRepository,
            UserRepository userRepository
    ) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Integer createOrderFromCart(Integer userId, String deliveryAddress, String note) {
        User customer = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        List<CartItem> items = cartItemRepository.findByUser_IdOrderByIdAsc(userId);
        if (items.isEmpty()) {
            throw new IllegalStateException("Cart is empty.");
        }

        String addr = (deliveryAddress != null && !deliveryAddress.isBlank())
                ? deliveryAddress.trim()
                : (customer.getAddress() == null ? "" : customer.getAddress().trim());

        if (addr.isBlank()) {
            throw new IllegalStateException("Delivery address is required (user profile address is empty).");
        }

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem it : items) {
            BigDecimal price = it.getPizzaSize().getPrice();
            int qty = it.getQuantity() == null ? 0 : it.getQuantity();
            total = total.add(price.multiply(BigDecimal.valueOf(qty)));
        }

        Order o = new Order();
        o.setCustomer(customer);
        o.setStatus(Order.STATUS_PENDING);
        o.setDeliveryAddress(addr);
        o.setNote(note);
        o.setTotalPrice(total);

        Order saved = orderRepository.save(o);

        cartItemRepository.deleteAll(items);

        return saved.getId();
    }
}