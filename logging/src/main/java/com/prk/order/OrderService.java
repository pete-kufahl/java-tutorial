package com.prk.order;

import com.prk.user.User;
import com.prk.user.UserRepository;
import com.prk.user.UserStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderService {
    private OrderRepository repository = new OrderRepository();
    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());

    // get all
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    // get by user
    public List<Order> getAllOrdersByUser(User user) {
        if (UserRepository.getDummyDataList().contains(user)) {
            return repository.findByUser(user);
        } else {
            try {
                throw new Exception("the user does not exist");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }

    // add order
    public boolean addOrder(Order order) {
        if (order.getOrderDateTime().isAfter(LocalDateTime.now())) {
            LOGGER.log(Level.WARNING, "cannot place order in the future: " + order.toString());
            try {
                throw new Exception("cannot place an order in the future");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        if (order.getProducts().isEmpty()) {
            LOGGER.log(Level.WARNING, "trying to place an empty order: " + order.toString());
            try {
                throw new Exception("order must consist of at least one product");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        if (order.getUser().getUserStatus() == UserStatus.BLOCKED) {
            LOGGER.log(Level.WARNING, "trying to place an order for a blocked user: " + order.toString());
            try {
                throw new Exception("order cannot be placed by a blocked user");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (order.getUser().getUserStatus() == UserStatus.PENDING) {
            LOGGER.log(Level.WARNING, "trying to place an order by a pending user " + order.toString());
            try {
                throw new Exception("order cannot be placed by a pending user");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return repository.save(order);
    }

    // delete order
    public boolean deleteOrder(Order order) {
        if (order.getOrderStatus() == OrderStatus.COMPLETED) {
            try {
                throw new Exception("cannot cancel a completed order");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            try {
                throw new Exception("order was already cancelled");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return repository.remove(order);
    }
}
