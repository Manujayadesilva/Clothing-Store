package com.nibm.ClothingStore.controller;

import com.nibm.ClothingStore.model.Order;
import com.nibm.ClothingStore.model.Customer;
import com.nibm.ClothingStore.repository.OrderRepository;
import com.nibm.ClothingStore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Customer customer = customerRepository.findById(order.getCustomer().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + order.getCustomer().getId()));

        order.setCustomer(customer);
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
        return ResponseEntity.ok(order);
    }

    @GetMapping("/customer/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));

        Customer customer = customerRepository.findById(orderDetails.getCustomer().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + orderDetails.getCustomer().getId()));

        order.setCustomer(customer);
        order.setOrderDate(orderDetails.getOrderDate());
        order.setStatus(orderDetails.getStatus());
        order.setQuantity(orderDetails.getQuantity());
        order.setSize(orderDetails.getSize());

        Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));

        orderRepository.delete(order);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
