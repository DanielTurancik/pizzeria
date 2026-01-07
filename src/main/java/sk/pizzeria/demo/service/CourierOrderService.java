package sk.pizzeria.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.pizzeria.demo.entity.Order;
import sk.pizzeria.demo.entity.User;
import sk.pizzeria.demo.repository.OrderRepository;
import sk.pizzeria.demo.repository.UserRepository;

import java.util.List;

@Service
public class CourierOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CourierOrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> listOpenReady() {
        return orderRepository.findByStatusAndCourierIsNullOrderByIdAsc(Order.STATUS_READY);
    }

    @Transactional(readOnly = true)
    public List<Order> listMine(Integer courierUserId) {
        return orderRepository.findByCourier_IdOrderByIdDesc(courierUserId);
    }

    @Transactional
    public void claim(Integer courierUserId, Integer orderId) {
        Order o = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        if (!Order.STATUS_READY.equals(o.getStatus())) {
            throw new IllegalStateException("Only ready orders can be claimed.");
        }
        if (o.getCourier() != null) {
            throw new IllegalStateException("Order is already claimed.");
        }

        User courier = userRepository.findById(courierUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + courierUserId));

        o.setCourier(courier);
        orderRepository.save(o);
    }

    @Transactional
    public void setDelivering(Integer courierUserId, Integer orderId) {
        Order o = getMineOrThrow(courierUserId, orderId);

        if (!Order.STATUS_READY.equals(o.getStatus())) {
            throw new IllegalStateException("Transition allowed only from ready -> delivering.");
        }

        o.setStatus(Order.STATUS_DELIVERING);
        orderRepository.save(o);
    }

    @Transactional
    public void setDelivered(Integer courierUserId, Integer orderId) {
        Order o = getMineOrThrow(courierUserId, orderId);

        if (!Order.STATUS_DELIVERING.equals(o.getStatus())) {
            throw new IllegalStateException("Transition allowed only from delivering -> delivered.");
        }

        o.setStatus(Order.STATUS_DELIVERED);
        orderRepository.save(o);
    }

    private Order getMineOrThrow(Integer courierUserId, Integer orderId) {
        Order o = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        if (o.getCourier() == null || o.getCourier().getId() == null || !o.getCourier().getId().equals(courierUserId)) {
            throw new IllegalStateException("Order is not assigned to current courier.");
        }

        return o;
    }
}