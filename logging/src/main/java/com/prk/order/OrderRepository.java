package com.prk.order;

import com.prk.product.Product;
import com.prk.product.ProductRepository;
import com.prk.user.User;
import com.prk.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderRepository {
    private List<Order> orderList = getDummyDataList();

    public List<Order> findAll() {
        return orderList;
    }

    public List<Order> findByUser(User user) {
        return orderList.stream()
                .filter(o -> o.getUser().equals(user))
                .toList();
    }

    public boolean save(Order order) {
        long lastId = orderList.get(orderList.size() - 1).getId();
        order.setId(lastId + 1);
        return orderList.add(order);
    }

    public boolean remove(Order order) {
        if (orderList.contains(order)) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            return true;
        } else {
            return false;
        }
    }

    public static List<Order> getDummyDataList() {
        UserRepository userRepository = new UserRepository();
        List<User> userList = userRepository.getUserList();
        List<Product> productList = ProductRepository.getDummyDataList();

        var now = LocalDateTime.now();
        Order o1 = new Order(1, userList.get(0), productList.subList(0, 1), now);
        Order o2 = new Order(2, userList.get(0), productList.subList(1, 1), now);
        Order o3 = new Order(3, userList.get(1), productList.subList(2, 2), now);
        Order o4 = new Order(4, userList.get(1), productList.subList(1, 2), now);
        Order o5 = new Order(5, userList.get(2), productList.subList(2, 2), now);
        Order o6 = new Order(6, userList.get(2), productList.subList(0, 2), now);

        Order[] orders = { o1, o2, o3, o4, o5, o6 };
        return new ArrayList<>(Arrays.asList(orders));
    }
}
