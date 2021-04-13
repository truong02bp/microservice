package com.order.service;

import com.order.common.Payment;
import com.order.common.TransactionRequest;
import com.order.common.TransactionResponse;
import com.order.data.entity.Order;
import com.order.data.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public TransactionResponse save(TransactionRequest request){
        String response = "";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        Payment paymentResponse = restTemplate.postForObject("http://PAYMENT-SERVICE/payment",payment,Payment.class);
        if (paymentResponse == null)
            paymentResponse = new Payment();
        System.out.println(paymentResponse.toString());
        if (paymentResponse.getPaymentStatus().equals("success"))
            response = "payment processing successful and order placed";
        else
            response = "there is a failure in payment api, order added to cart";
        return new TransactionResponse(orderRepository.save(order),paymentResponse.getAmount()
                ,paymentResponse.getTransactionId(),response);
    }
}
