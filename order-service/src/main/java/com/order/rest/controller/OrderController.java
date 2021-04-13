package com.order.rest.controller;

import com.order.common.TransactionRequest;
import com.order.common.TransactionResponse;
import com.order.data.entity.Order;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest transactionRequest){
        return orderService.save(transactionRequest);
    }
}
