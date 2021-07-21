package com.alaiah.shopify.service;

import com.alaiah.shopify.dao.CustomerRepository;
import com.alaiah.shopify.dto.Purchase;
import com.alaiah.shopify.dto.PurchaseResponse;
import com.alaiah.shopify.entity.Customer;
import com.alaiah.shopify.entity.Order;
import com.alaiah.shopify.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;


@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        Customer customer = customerRepository.findByEmail(purchase.getCustomer().getEmail());
        if (customer == null) {

            customer = purchase.getCustomer();
        }

        Order order = purchase.getOrder();

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.addOrderItem(item));

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        customer.addOrder(order);

        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();

    }
}
