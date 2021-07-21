package com.alaiah.shopify.dto;

import com.alaiah.shopify.entity.Address;
import com.alaiah.shopify.entity.Customer;
import com.alaiah.shopify.entity.Order;
import com.alaiah.shopify.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
