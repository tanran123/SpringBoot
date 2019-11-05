package com.ginko.driver.api.moudle.payment;

import com.ginko.driver.api.webSocket.CustomerWebSoket;
import com.ginko.driver.api.webSocket.WebSocketReturnType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/payment")
@RestController
public class PaymentController {


    @RequestMapping(value = "/paymentStatus",method = RequestMethod.POST)
    public void getPayment(@RequestBody WebSocketReturnType webSocketReturnType){
        try {
            CustomerWebSoket.sendByOrderCode(webSocketReturnType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
