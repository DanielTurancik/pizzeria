package sk.pizzeria.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.pizzeria.demo.entity.Order;
import sk.pizzeria.demo.entity.User;
import sk.pizzeria.demo.repository.OrderRepository;
import sk.pizzeria.demo.repository.UserRepository;

import java.util.List;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public ProfileService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public User getUserByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }

    @Transactional(readOnly = true)
    public List<Order> getMyOrders(Integer userId) {
        return orderRepository.findByCustomer_IdOrderByIdDesc(userId);
    }

    @Transactional
    public void cancelOrder(Integer userId, Integer orderId) {
        Order o = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        if (o.getCustomer() == null || o.getCustomer().getId() == null || !o.getCustomer().getId().equals(userId)) {
            throw new IllegalStateException("Not allowed.");
        }

        if (!Order.STATUS_PENDING.equals(o.getStatus())) {
            throw new IllegalStateException("Only pending orders can be cancelled.");
        }

        orderRepository.delete(o);
    }
}