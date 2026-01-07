package sk.pizzeria.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.pizzeria.demo.entity.Order;
import sk.pizzeria.demo.entity.User;
import sk.pizzeria.demo.repository.OrderRepository;
import sk.pizzeria.demo.repository.UserRepository;

import java.util.List;

@Service
public class CookOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CookOrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> listOpenPending() {
        return orderRepository.findByStatusAndCookIsNullOrderByIdAsc(Order.STATUS_PENDING);
    }

    @Transactional(readOnly = true)
    public List<Order> listMine(Integer cookUserId) {
        return orderRepository.findByCook_IdOrderByIdDesc(cookUserId);
    }

    @Transactional
    public void claim(Integer cookUserId, Integer orderId) {
        Order o = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        if (!Order.STATUS_PENDING.equals(o.getStatus())) {
            throw new IllegalStateException("Only pending orders can be claimed.");
        }
        if (o.getCook() != null) {
            throw new IllegalStateException("Order is already claimed.");
        }

        User cook = userRepository.findById(cookUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + cookUserId));

        o.setCook(cook);
        orderRepository.save(o);
    }

    @Transactional
    public void setPreparing(Integer cookUserId, Integer orderId) {
        Order o = getMineOrThrow(cookUserId, orderId);

        if (!Order.STATUS_PENDING.equals(o.getStatus())) {
            throw new IllegalStateException("Transition allowed only from pending -> preparing.");
        }

        o.setStatus(Order.STATUS_PREPARING);
        orderRepository.save(o);
    }

    @Transactional
    public void setReady(Integer cookUserId, Integer orderId) {
        Order o = getMineOrThrow(cookUserId, orderId);

        if (!Order.STATUS_PREPARING.equals(o.getStatus())) {
            throw new IllegalStateException("Transition allowed only from preparing -> ready.");
        }

        o.setStatus(Order.STATUS_READY);
        orderRepository.save(o);
    }

    private Order getMineOrThrow(Integer cookUserId, Integer orderId) {
        Order o = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        if (o.getCook() == null || o.getCook().getId() == null || !o.getCook().getId().equals(cookUserId)) {
            throw new IllegalStateException("Order is not assigned to current cook.");
        }

        return o;
    }
}
