package com.alaiah.shopify.service;

import com.alaiah.shopify.dto.Purchase;
import com.alaiah.shopify.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
