package com.ginko.driver.api.moudle.payment;

import com.ginko.driver.api.webSocket.CustomerWebSoket;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/payment")
@RestController
public class PaymentController {


    @RequestMapping(value = "/paymentStatus",method = RequestMethod.POST)
    public void getPayment(@RequestParam("orderCode")String orderCode, @RequestParam("paymentStatus")Boolean paymentStatus){
        try {
            CustomerWebSoket.sendByOrderCode(orderCode,paymentStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
