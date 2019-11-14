package com.ginko.driver.api.moudle.payment;

import com.ginko.driver.api.webSocket.CustomerWebSoket;
import com.ginko.driver.api.webSocket.WebSocketReturnType;
import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.framework.entity.Partner;
import com.ginko.driver.framework.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/payment")
@RestController
public class PaymentController {

    @Autowired
    private PartnerService partnerService;

    /**
     * 各种支付回调
     * @param webSocketReturnType
     */
    @RequestMapping(value = "/paymentStatus",method = RequestMethod.POST)
    public MsgConfig getPayment(@RequestBody WebSocketReturnType webSocketReturnType){
        //partner付款的回调逻辑
        if ("partner".equals(webSocketReturnType.getPaymentType())){
            partnerService.successPayMnet(webSocketReturnType.getOrderCode(),webSocketReturnType.isPaymentStatus());
        }
        try {
            CustomerWebSoket.sendByOrderCode(webSocketReturnType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MsgConfig("0","success",null);
    }
}
